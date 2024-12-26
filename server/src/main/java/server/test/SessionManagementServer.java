package server.test;
import java.io.*;
import java.net.*;
import java.util.*;

public class SessionManagementServer {
    // Lưu thông tin đăng nhập (giả sử username:password)
    private static final Map<String, String> USER_CREDENTIALS = Map.of(
        "user1", "password1",
        "user2", "password2"
    );

    // Lưu trữ session token và liên kết với client
    private static final Map<String, String> SESSIONS = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server is running...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {

            String clientMessage;
            while (true) {
                clientMessage = in.readUTF(); // Đọc thông điệp từ client
                System.out.println("Received: " + clientMessage);

                if (clientMessage.startsWith("LOGIN")) {
                    // Xử lý đăng nhập
                    String[] parts = clientMessage.split(" ");
                    if (parts.length == 3) {
                        String username = parts[1];
                        String password = parts[2];
                        if (USER_CREDENTIALS.containsKey(username) && USER_CREDENTIALS.get(username).equals(password)) {
                            // Tạo token và gửi về client
                            String token = UUID.randomUUID().toString();
                            SESSIONS.put(token, username);
                            out.writeUTF("LOGIN_SUCCESS " + token);
                        } else {
                            out.writeUTF("LOGIN_FAILED");
                        }
                    } else {
                        out.writeUTF("INVALID_COMMAND");
                    }
                } else if (clientMessage.startsWith("REQUEST")) {
                    // Xử lý yêu cầu
                    String[] parts = clientMessage.split(" ");
                    if (parts.length >= 2) {
                        String token = parts[1];
                        if (SESSIONS.containsKey(token)) {
                            String username = SESSIONS.get(token);
                            out.writeUTF("REQUEST_SUCCESS for user: " + username);
                        } else {
                            out.writeUTF("INVALID_TOKEN");
                        }
                    } else {
                        out.writeUTF("INVALID_COMMAND");
                    }
                } else {
                    out.writeUTF("UNKNOWN_COMMAND");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
