package client.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkController {
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    private String username;
    private String sessionId;
    
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

    public DataInputStream getInputStream() {
        return dis;
    }

    public DataOutputStream getOutputStream() {
        return dos;
    }
}
