package server.model.BO;

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
    public boolean verifyCredentials(String username, String password) {

            // Tạo đối tượng Account để tìm trong cơ sở dữ liệu
            Account account = new Account(username, password);

            // Kiểm tra thông tin tài khoản từ cơ sở dữ liệu
            Account retrievedAccount = accountDAO.selectById(account);

            // Nếu tài khoản tìm thấy và mật khẩu khớp
            boolean loginSuccess = retrievedAccount != null && retrievedAccount.getPassword().equals(password);


            // Ghi log kết quả
            if (loginSuccess) {
                System.out.println("Login successful for user: " + username);
            } else {
                System.out.println("Login failed for user: " + username);
            }   
            return loginSuccess;
    }
}
