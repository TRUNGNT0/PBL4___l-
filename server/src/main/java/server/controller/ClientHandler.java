package server.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import server.model.BO.LoginHandler;
import server.model.BO.DirectoryHandler;
import server.model.BO.DeleteHandler;
import server.model.BO.DownloadHandler;
import server.model.BO.UploadHandler;

public class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    // Các handler được khởi tạo trong lớp
    private UploadHandler uploadHandler;
    private DownloadHandler downloadHandler;
    private DeleteHandler deleteHandler;
    private DirectoryHandler directoryHandler;
	private LoginHandler login;

    public ClientHandler(Socket socket) {
        this.socket = socket;

        // Khởi tạo các handler
        this.uploadHandler = new UploadHandler();
        this.downloadHandler = new DownloadHandler();
        this.deleteHandler = new DeleteHandler();
        this.directoryHandler = new DirectoryHandler();
		this.login= new LoginHandler();

        try {
            dis = new DataInputStream(this.socket.getInputStream());
            dos = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
    	while (true) {
            try {
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

                    default:
                        System.out.println("Unknown request: " + request);
                        break;
                }
            } catch (IOException e) {
                System.out.println("Client disconnected: " + e.getMessage());
                closeConnections();
                break;
            } finally {
                closeConnections();
            }
    	}
    }

    // Xử lý từng loại yêu cầu
    private void handleUpload() {
        uploadHandler.upLoadHandler(dis);
    }
    
    private void handleUpload2() {
        
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

	private void handleLogin() {
        login.verifyCredentials(dis, dos);
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
