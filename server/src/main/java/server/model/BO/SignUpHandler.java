package server.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import server.model.Bean.Account;
import server.model.DAO.AccountDAO;
import server.controller.MyServer;

public class SignUpHandler {

    private AccountDAO accountDAO;

    // Constructor
    public SignUpHandler() {
        this.accountDAO = new AccountDAO();
    }

    public void signUp(DataInputStream dis, DataOutputStream dos) {
        try {
            String username = dis.readUTF();
            String password = dis.readUTF();

            // Kiểm tra xem username có tồn tại trong cơ sở dữ liệu hay không
            if (accountDAO.selectByUsername(username) != null) {
                // Nếu username đã tồn tại
                dos.writeBoolean(false);
                dos.writeUTF("Username đã tồn tại, vui lòng chọn username khác.");
            } else {
                // Nếu username chưa tồn tại, thêm tài khoản mới
                Account newAccount = new Account(username, password);
                int result = accountDAO.insert(newAccount);

                // Kiểm tra kết quả thêm tài khoản
                if (result > 0) {
                    // Đăng ký thành công, tạo thư mục cho người dùng
                    String userDirectoryPath = MyServer.getHomeDirectoryPath() + "//" + username;
                    File userDirectory = new File(userDirectoryPath);

                    if (!userDirectory.exists()) {
                        // Tạo thư mục nếu chưa tồn tại
                        boolean dirCreated = userDirectory.mkdirs();
                        if (dirCreated) {
                            System.out.println("Thư mục cho người dùng " + username + " đã được tạo.");
                        } else {
                            System.out.println("Không thể tạo thư mục cho người dùng " + username);
                        }
                    }

                    dos.writeBoolean(true);  // Đăng ký thành công
                    dos.writeUTF("Đăng ký thành công!");
                } else {
                    dos.writeBoolean(false);  // Đăng ký thất bại
                    dos.writeUTF("Đã có lỗi xảy ra trong quá trình đăng ký.");
                }
            }

            dos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
