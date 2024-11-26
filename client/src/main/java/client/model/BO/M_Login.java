package client.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class M_Login {
    private String username;  // Lưu username sau khi đăng nhập

    public boolean login(String username, String password, DataInputStream dis, DataOutputStream dos) {
        try {
            dos.writeUTF("LOGIN");  // Gửi yêu cầu "LOGIN" đến server
            dos.writeUTF(username);  // Gửi username
            dos.writeUTF(password);  // Gửi password
            dos.flush();

            // Nhận phản hồi từ server
            boolean loginSuccess = dis.readBoolean();

            if (loginSuccess) {
                // Nếu đăng nhập thành công, nhận username từ server
                this.username = dis.readUTF();  // Lưu username trả về từ server
                System.out.println("Đăng nhập thành công! Xin chào, " + this.username);
            } else {
                System.out.println("Đăng nhập thất bại. Vui lòng kiểm tra tài khoản/mật khẩu.");
            }

            return loginSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Getter cho username
    public String getUsername() {
        return username;
    }
}
