package client.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class NetworkController {
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    private String username;
    private String sessionId;
    private SecretKey AESKey;
    

	public NetworkController(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        
        this.username = "";
        this.sessionId = "";
    }
    
    public void receiveSessionId() throws IOException {
		username = dis.readUTF();
		sessionId = dis.readUTF();
    }
    
    public SecretKey getAESKey() {
		return AESKey;
	}

	public void setAESKey(SecretKey aESKey) {
		AESKey = aESKey;
	}

	public void connect() throws IOException {
		socket = new Socket(serverAddress, serverPort);
		socket.setSoTimeout(5000);
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());
        System.out.println("Kết nối đến server " + serverAddress + " qua cổng " + serverPort);
    }
    
    public boolean authenticToken() {
    	try {
    		dos.writeUTF("AUTHENTIC_TOKEN");
			dos.writeUTF(username);
			dos.writeUTF(sessionId);
	    	boolean success = dis.readBoolean();
	    	return success;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }

    public void disconnect() throws IOException {
    	if (dos != null) dos.close();
        if (dis != null) dis.close();
        if (socket != null) socket.close();
        System.out.println("Đã ngắt kết nối với server.");
    }

    public void sendCommand(String command) {
        try {
            dos.writeUTF(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveMessage() {
        try {
            return dis.readUTF(); // Đọc tin nhắn từ server
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        }
    }
    
    public void sendMessage(String message) {
        try {
            if (AESKey == null) {
                throw new IllegalStateException("AESKey chưa được thiết lập. Không thể gửi tin nhắn.");
            }

            // Tạo Cipher cho AES với chế độ CBC
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = new byte[16]; // Khởi tạo vector IV (16 byte mặc định)
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, AESKey, ivSpec);

            // Mã hóa tin nhắn
            byte[] encryptedMessage = cipher.doFinal(message.getBytes("UTF-8"));

            // Mã hóa base64 để đảm bảo dữ liệu không bị lỗi trong quá trình truyền
            String base64Message = Base64.getEncoder().encodeToString(encryptedMessage);

            // Gửi tin nhắn đã mã hóa
            dos.writeUTF(base64Message);

            System.out.println("Đã gửi tin nhắn (đã mã hóa): " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataInputStream getInputStream() {
        return dis;
    }

    public DataOutputStream getOutputStream() {
        return dos;
    }
}
