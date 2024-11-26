package client.controller;

import java.io.IOException;
import java.util.List;

import client.model.BO.M_Login;
import client.model.BO.M_FileHandler;
import client.model.Bean.FileInformation;

public class C_MyClient {
    private static C_MyClient instance;  // Đối tượng duy nhất của C_MyClient
    private NetworkHandler networkHandler;
    private M_FileHandler fileHandler;
    private M_Login authHandler;

    private String username;  // Biến lưu username

    // Constructor riêng tư để ngăn chặn việc tạo đối tượng từ ngoài
    private C_MyClient(String serverAddress, int serverPort) {
        this.networkHandler = new NetworkHandler(serverAddress, serverPort);
        this.authHandler = new M_Login();
    }

    // Phương thức tĩnh để lấy ra đối tượng duy nhất của C_MyClient
    public static C_MyClient getInstance(String serverAddress, int serverPort) {
        if (instance == null) {
            instance = new C_MyClient(serverAddress, serverPort);
        }
        return instance;
    }

    public void connect() {
        try {
            networkHandler.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            networkHandler.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean login(String username, String password) {
        connect();
        boolean loginSuccess = authHandler.login(username, password, networkHandler.getInputStream(), networkHandler.getOutputStream());
        if (loginSuccess) {
            this.username = authHandler.getUsername();  // Lấy username từ M_AuthHandler sau khi đăng nhập thành công
            this.fileHandler = new M_FileHandler(this.username);  // Sử dụng username cho M_FileHandler
        }
        disconnect();
        return loginSuccess;
    }
    
    public void goToDirectory(String derectoryName) {
    	fileHandler.goToDirectory(derectoryName);
    }
    
    public void goBackDirectory() {
    	fileHandler.goBackDirectory();
    }

    public void upLoad(String filePath) {
        connect();
        networkHandler.sendCommand("UP_LOAD");
        fileHandler.upload(filePath, networkHandler.getOutputStream());
        disconnect();
    }

    public FileInformation[] load() {
        connect();
        networkHandler.sendCommand("LOAD");
        FileInformation a[] = fileHandler.load(networkHandler.getInputStream(), networkHandler.getOutputStream());
        disconnect();
        return a;
    }
    
    public boolean checkDownLoad() {
    	return fileHandler.checkDownLoad();
    }
    
    public void setDownLoad(String path) {
    	fileHandler.setDownLoad(path);
    }

    public void downLoadFile(List<String> fileList, String downloadDirectory) {
        connect();
        networkHandler.sendCommand("DOWN_LOAD");
        fileHandler.download(fileList, downloadDirectory, networkHandler.getInputStream(), networkHandler.getOutputStream());
        disconnect();
    }

    public void deleteFile(List<String> fileList) {
        connect();
        networkHandler.sendCommand("DELETE");
        fileHandler.delete(fileList, networkHandler.getInputStream(), networkHandler.getOutputStream());
        disconnect();
    }
}
