package server.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

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
		this.login= new LoginHandler();
        this.SignUp= new SignUpHandler();

        try {
            dis = new DataInputStream(this.socket.getInputStream());
            dos = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
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
                isLogin = handleAuthenticSessionId();
                if(isLogin) {
                	break;
                } else return;
			default:
                System.out.println("Unknown request: " + request1);
                break;
        	}
        	
        	String request = dis.readUTF(); // Nhận yêu cầu từ client
            switch (request) {
				case "LOGIN":
                    handleLogin();
                    break;
                    
                case "UP_LOAD":
                    handleUpload();
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
                    handleDownload();
                    break;
                    
                case "DOWN_LOAD_2":
                    handleDownload2();
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
    private void handleUpload() {
        uploadHandler.upLoadHandler(dis);
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

    private void handleDownload() {
        downloadHandler.downLoadHandler(dis, dos);
    }
    
    private void handleDownload2() {
    	downloadHandler.downLoadHandlerWithZip(dis, dos);
    }

    private void handleDelete() {
        deleteHandler.deleteHandler(dis, dos);
    }
    
    private boolean handleAuthenticSessionId() {
    	boolean success = false;
    	try {
			String username = dis.readUTF();
			String token = dis.readUTF();
			success = sessionManager.isValidSessionId(username, token);
			dos.writeBoolean(success);
			System.out.println("Xác thực SessionId thành công. Username "+ username + " - SessionId: " + token);
		} catch (IOException e) {
			e.printStackTrace();
			return success;
		}
    	return success;
    }

	private void handleLogin() {
		try {
			String username = dis.readUTF();
			String password = dis.readUTF();
	        if(login.verifyCredentials(username, password)) {
	        	String token = sessionManager.generateRandomSessionId();
	        	dos.writeBoolean(true);
	        	dos.writeUTF(username);
	        	dos.writeUTF(token);
	        	sessionManager.addSessionId(username, token);
	        	sessionManager.printAllSessionId();
	        } else {
	        	dos.writeBoolean(false);
	        }
	        dos.flush();
		} catch (IOException e) {

			e.printStackTrace();
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
