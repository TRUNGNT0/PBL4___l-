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

    public NetworkController(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void connect() {
        try {
        	socket = new Socket(serverAddress, serverPort);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
	        System.out.println("Kết nối đến server " + serverAddress + " qua cổng " + serverPort);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void disconnect() {
    	try {
    		if (dos != null) dos.close();
            if (dis != null) dis.close();
            if (socket != null) socket.close();
            System.out.println("Đã ngắt kết nối với server.");
		} catch (IOException e) {
			// TODO: handle exception
		}
        
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
