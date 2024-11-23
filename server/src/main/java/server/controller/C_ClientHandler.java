package server.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import server.model.M_FileDeleteHandler;
import server.model.M_FileDownloadHandler;
import server.model.M_FileUploadHandler;
import server.model.M_DirectoryHandler;
import server.model.LoginHandler;

public class C_ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    // Các handler được khởi tạo trong lớp
    private M_FileUploadHandler uploadHandler;
    private M_FileDownloadHandler downloadHandler;
    private M_FileDeleteHandler deleteHandler;
    private M_DirectoryHandler directoryHandler;
	private LoginHandler login;

    public C_ClientHandler(Socket socket, String homeDirectoryPath) {
        this.socket = socket;

        // Khởi tạo các handler
        this.uploadHandler = new M_FileUploadHandler(homeDirectoryPath);
        this.downloadHandler = new M_FileDownloadHandler(homeDirectoryPath);
        this.deleteHandler = new M_FileDeleteHandler(homeDirectoryPath);
        this.directoryHandler = new M_DirectoryHandler(homeDirectoryPath);
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
        try {
            while (true) {
                String request = dis.readUTF(); // Nhận yêu cầu từ client
                switch (request) {
					case "LOGIN":
                        handleLogin();
                        break;
                    case "UP_LOAD":
                        handleUpload();
                        break;

                    case "LOAD":
                        handleLoad();
                        break;

                    case "DOWN_LOAD":
                        handleDownload();
                        break;

                    case "DELETE":
                        handleDelete();
                        break;

                    default:
                        System.out.println("Unknown request: " + request);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + e.getMessage());
        } finally {
            closeConnections();
        }
    }

    // Xử lý từng loại yêu cầu
    private void handleUpload() {
        uploadHandler.upLoadHandler(dis);
    }

    private void handleLoad() {
        directoryHandler.loadHandler(dis, dos);
    }

    private void handleDownload() {
        downloadHandler.downLoadHandler(dis, dos);
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
