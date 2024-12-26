package client.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class M_Login {

    public boolean login(String username, String password, DataInputStream dis, DataOutputStream dos) throws IOException {
    	dos.writeUTF("LOGIN");  // Gửi yêu cầu "LOGIN" đến server
        dos.writeUTF(username);  // Gửi username
        dos.writeUTF(password);  // Gửi password
        dos.flush();

        // Nhận phản hồi từ server
        boolean loginSuccess = dis.readBoolean();

        if (loginSuccess) {
            System.out.println("Đăng nhập thành công! Xin chào, " + username);
        } else {
            System.out.println("Đăng nhập thất bại. Vui lòng kiểm tra tài khoản/mật khẩu.");
        }
        return loginSuccess;
    }
}
