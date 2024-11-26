package server.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import server.model.Bean.Account;
import server.model.DAO.AccountDAO;

public class LoginHandler {

    private AccountDAO accountDAO;

    // Constructor
    public LoginHandler() {
        // Khởi tạo AccountDAO để truy vấn dữ liệu tài khoản
        this.accountDAO = new AccountDAO();
    }

    // Phương thức kiểm tra thông tin đăng nhập
    public void verifyCredentials(DataInputStream dis, DataOutputStream dos) {
        try {
            // Nhận username và password từ client
            String username = dis.readUTF();
            String password = dis.readUTF();

            // Tạo đối tượng Account để tìm trong cơ sở dữ liệu
            Account account = new Account(username, password);

            // Kiểm tra thông tin tài khoản từ cơ sở dữ liệu
            Account retrievedAccount = accountDAO.selectById(account);

            // Nếu tài khoản tìm thấy và mật khẩu khớp
            boolean loginSuccess = retrievedAccount != null && retrievedAccount.getPassword().equals(password);

            // Gửi kết quả đăng nhập và username (nếu thành công) lại cho client
            dos.writeBoolean(loginSuccess);  // Kết quả đăng nhập
            if (loginSuccess) {
                dos.writeUTF(retrievedAccount.getUserName()); // Gửi lại username
            } else {
                dos.writeUTF(""); // Nếu đăng nhập thất bại, gửi chuỗi rỗng
            }
            dos.flush();

            // Ghi log kết quả
            if (loginSuccess) {
                System.out.println("Login successful for user: " + username);
            } else {
                System.out.println("Login failed for user: " + username);
            }

        } catch (IOException e) {
            System.out.println("Error during login verification: " + e.getMessage());
        }
    }
}
