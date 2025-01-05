package server.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import server.model.BO.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    
    private SessionManager sessionManager;

    // Các handler được khởi tạo trong lớp
    private UploadHandler uploadHandler;
    private DownloadHandler downloadHandler;
    private DeleteHandler deleteHandler;
    private DirectoryHandler directoryHandler;
	private LoginHandler login;
    private SignUpHandler SignUp;
    private Encryption encryption;


    public ClientHandler(Socket socket, SessionManager sessionManager) {
        this.socket = socket;
        this.sessionManager = sessionManager;
        try {
			this.socket.setSoTimeout(5000);
		} catch (SocketException e) {
			closeConnections();
			e.printStackTrace();
		}
        // Khởi tạo các handler
        this.uploadHandler = new UploadHandler();
        this.downloadHandler = new DownloadHandler();
        this.deleteHandler = new DeleteHandler();
        this.directoryHandler = new DirectoryHandler();
		this.login = new LoginHandler();
        this.SignUp = new SignUpHandler();
        this.encryption = new Encryption();

        try {
            dis = new DataInputStream(this.socket.getInputStream());
            dos = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
    	String username = "";
    	boolean isLogin = false;
    	try {
        	String request1 = dis.readUTF();
        	switch (request1) {
			case "LOGIN":
                handleLogin();
                return;
                
			case "SIGNUP":
                handleSignUp();
                return;
                
			case "AUTHENTIC_TOKEN":
				username = dis.readUTF();
				String sessionId = dis.readUTF();
                isLogin = handleAuthenticSessionId(username, sessionId);
                if(isLogin) {
                	break;
                } else return;
			default:
                System.out.println("Unknown request: " + request1);
                break;
        	}
        	
        	String request = dis.readUTF(); // Nhận yêu cầu từ client
            switch (request) {
//				case "LOGIN":
//                    handleLogin(sessionManager.getSecretKeyByUsername(username));
//                    break;
                    
                case "UP_LOAD":
                    handleUpload(sessionManager.getSecretKeyByUsername(username));
                    break;
                    
                case "UP_LOAD_2":
                    handleUpload2();
                    break;
                    
                case "NEW_DIRECTORY":
                	handleNewDirectory();
                    break;

                case "LOAD":
                    handleLoad();
                    break;

                case "DOWN_LOAD":
                    handleDownload(sessionManager.getSecretKeyByUsername(username));
                    break;
                    
                case "DOWN_LOAD_2":
                    handleDownload2(sessionManager.getSecretKeyByUsername(username));
                    break;

                case "DELETE":
                    handleDelete();
                    break;
                    
                case "SIGNUP":
                    handleSignUp();
                    break;
                default:
                    System.out.println("Unknown request: " + request);
                    break;
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + e.getMessage());
            closeConnections();
        } finally {
            closeConnections();
        }
       
    }

    // Xử lý từng loại yêu cầu
    private void handleUpload(SecretKey secretKey) {
        uploadHandler.upLoadHandler(dis, secretKey);
    }
    
    private void handleUpload2() {
        
    }
    
    
    
    private void handleSignUp() {
        SignUp.signUp(dis, dos);
    }

    private void handleNewDirectory() {
    	directoryHandler.createFolderIfNotExists(dis, dos);
    }

    private void handleLoad() {
        directoryHandler.loadHandler(dis, dos);
    }

    private void handleDownload(SecretKey secretKey) {
        downloadHandler.downLoadHandler(dis, dos, secretKey);
    }
    
    private void handleDownload2(SecretKey secretKey) {
    	downloadHandler.downLoadHandlerWithZip(dis, dos, secretKey);
    }

    private void handleDelete() {
        deleteHandler.deleteHandler(dis, dos);
    }
    
    private boolean handleAuthenticSessionId(String username, String sessionId) {
    	boolean success = false;
    	try {
			success = sessionManager.isValidSessionId(username, sessionId);
			dos.writeBoolean(success);
			System.out.println("Xác thực SessionId thành công. Username "+ username + " - SessionId: " + sessionId);
		} catch (IOException e) {
			e.printStackTrace();
			return success;
		}
    	return success;
    }

	private void handleLogin() {
		try {
			SecretKey secretKey = this.encryption.createAESKey();
			encryption.sendAESKeyToClient(secretKey, dis, dos);
			String username = this.receiveMessage(secretKey);
			String password = this.receiveMessage(secretKey);
	        if(login.verifyCredentials(username, password)) {
	        	String sessionId = sessionManager.generateRandomSessionId();
	        	dos.writeBoolean(true);
	        	dos.writeUTF(username);
	        	dos.writeUTF(sessionId);
	        	sessionManager.addSessionId(username, sessionId, secretKey);
	        } else {
	        	dos.writeBoolean(false);
	        }
	        dos.flush();
		} catch (IOException e) {

			e.printStackTrace();
		}
    }
	
	public String receiveMessage(SecretKey secretKey) {
	    try {
	        if (secretKey == null) {
	            throw new IllegalStateException("AESKey chưa được thiết lập. Không thể nhận tin nhắn.");
	        }

	        // Nhận tin nhắn mã hóa (chuỗi Base64)
	        String base64Message = dis.readUTF();

	        // Tạo Cipher cho AES với chế độ CBC
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        byte[] iv = new byte[16]; // Khởi tạo vector IV (16 byte mặc định)
	        IvParameterSpec ivSpec = new IvParameterSpec(iv);

	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

	        // Giải mã Base64 thành mảng byte
	        byte[] encryptedMessage = Base64.getDecoder().decode(base64Message);

	        // Giải mã tin nhắn
	        byte[] decryptedMessage = cipher.doFinal(encryptedMessage);

	        // Chuyển đổi mảng byte về chuỗi
	        String message = new String(decryptedMessage, "UTF-8");

	        System.out.println("Đã nhận tin nhắn (giải mã): " + message);
	        return message;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null; // Trả về null nếu có lỗi
	    }
	}
    // Đóng kết nối và giải phóng tài nguyên
    private void closeConnections() {
        try {
            if (dis != null) dis.close();
            if (dos != null) dos.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
