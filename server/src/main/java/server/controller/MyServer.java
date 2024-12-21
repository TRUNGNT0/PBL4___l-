package server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
	private static final int SERVER_PORT = 8888;
	private static final int threadPoolSize = 100;
	
    private int port;
    private ServerSocket serverSocket;
    private static String homeDirectoryPath;
    
    

    // Nhóm luồng cố định
    private ExecutorService executorService;

    // Getter để các lớp khác lấy thông tin đường dẫn
    public static String getHomeDirectoryPath() {
        return homeDirectoryPath;
    }

    public MyServer() {
        this.port = SERVER_PORT;
        this.executorService = Executors.newFixedThreadPool(threadPoolSize); // Khởi tạo nhóm luồng cố định
    }

    public void startServer(String path) {
        homeDirectoryPath = path;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server đang lắng nghe tại cổng " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Kết nối từ: " + socket.getInetAddress());
                
                // Nộp ClientHandler vào ExecutorService
                executorService.submit(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng server khi xảy ra lỗi
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            executorService.shutdown();
        }
    }
}
