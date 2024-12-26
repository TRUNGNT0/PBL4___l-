package client.test;

import java.io.*;
import java.net.*;

public class SessionManagementClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            String token = null; // Token sẽ lưu trữ sau khi đăng nhập thành công

            while (true) {
                System.out.println("Enter command (LOGIN username password | REQUEST token | EXIT): ");
                String command = console.readLine();
                if (command.equalsIgnoreCase("EXIT")) {
                    break;
                }

                out.writeUTF(command); // Gửi thông điệp đến server
                String response = in.readUTF(); // Nhận phản hồi từ server
                System.out.println("Server response: " + response);

                // Lưu token nếu đăng nhập thành công
                if (response.startsWith("LOGIN_SUCCESS")) {
                    token = response.split(" ")[1];
                    System.out.println("Your session token: " + token);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
