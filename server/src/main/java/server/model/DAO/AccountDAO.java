package server.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.model.Bean.Account;
import server.model.database.JDBCUtil;

public class AccountDAO implements DAOInterface<Account> {

    @Override
    public int insert(Account t) {
        try {
            // Tạo kết nối đến cơ sở dữ liệu
            Connection con = JDBCUtil.getConnection();

            // Tạo câu lệnh SQL để thêm tài khoản mới
            String sql = "INSERT INTO account (user, password) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getUserName());  // Đặt giá trị cho trường user
            ps.setString(2, t.getPassword());  // Đặt giá trị cho trường password

            // Thực thi câu lệnh SQL
            int rowsAffected = ps.executeUpdate();

            // Đóng kết nối
            JDBCUtil.closeConnection(con);

            return rowsAffected;  // Trả về số dòng bị ảnh hưởng
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Account t) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(Account t) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ArrayList<Account> selectAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account selectById(Account t) {
        try {
            // Tạo kết nối đến cơ sở dữ liệu
            Connection con = JDBCUtil.getConnection();

            // Tạo câu lệnh SQL để truy vấn tài khoản theo user
            String sql = "SELECT * FROM account WHERE user = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getUserName());  // Đặt giá trị cho trường user

            // Thực thi câu lệnh SQL
            ResultSet rs = ps.executeQuery();

            // Xử lý kết quả trả về
            if (rs.next()) {
                String username = rs.getString("user");
                String password = rs.getString("password");

                // Đóng kết nối và trả về đối tượng Account
                JDBCUtil.closeConnection(con);
                return new Account(username, password);
            } else {
                // Không tìm thấy tài khoản
                System.out.println("No account found with username: " + t.getUserName());
                JDBCUtil.closeConnection(con);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Select by username
    public Account selectByUsername(String username) {
        try {
            // Tạo kết nối đến cơ sở dữ liệu
            Connection con = JDBCUtil.getConnection();

            // Tạo câu lệnh SQL để truy vấn tài khoản theo username
            String sql = "SELECT * FROM account WHERE user = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);  // Đặt giá trị cho trường username

            // Thực thi câu lệnh SQL
            ResultSet rs = ps.executeQuery();

            // Xử lý kết quả trả về
            if (rs.next()) {
                String user = rs.getString("user");
                String password = rs.getString("password");

                // Đóng kết nối và trả về đối tượng Account
                JDBCUtil.closeConnection(con);
                return new Account(user, password);
            } else {
                // Không tìm thấy tài khoản
                System.out.println("No account found with username: " + username);
                JDBCUtil.closeConnection(con);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Account> selectByCondition(String condition) {
        // TODO Auto-generated method stub
        return null;
    }
}
