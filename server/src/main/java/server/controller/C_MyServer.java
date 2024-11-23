package server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class C_MyServer {
    private int port;
    private ServerSocket serverSocket;
    private static String homeDirectoryPath;

    // Getter để các lớp khác lấy thông tin đường dẫn
    public static String getHomeDirectoryPath() {
        return homeDirectoryPath;
    }

    public C_MyServer(int port) {
        this.port = port;
    }

    public void startServer(String path) {
        homeDirectoryPath = path;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server đang lắng nghe tại cổng " + port);

            while (true) {
                // Chấp nhận kết nối từ client
                Socket socket = serverSocket.accept();
                System.out.println("Kết nối từ: " + socket.getInetAddress());

                // Khởi tạo C_ClientHandler và chạy trên một thread mới
                C_ClientHandler clientHandler = new C_ClientHandler(socket, homeDirectoryPath);
                new Thread(clientHandler).start();
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
        }
    }
}
