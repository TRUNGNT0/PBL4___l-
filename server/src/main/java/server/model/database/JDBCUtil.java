package server.model.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

    // Phương thức tạo kết nối
    public static Connection getConnection() {
        Connection connection = null;

        try {
            // URL kết nối đến cơ sở dữ liệu
            String url = "jdbc:mysql://localhost:3306/pbl4";
            String username = "root";
            String password = "";

            // Tạo kết nối
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    // Phương thức đóng kết nối
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức in thông tin cơ sở dữ liệu
    public static void printInfo(Connection connection) {
        if (connection != null) {
            try {
                DatabaseMetaData metaData = connection.getMetaData();
                System.out.println("Database Product Name: " + metaData.getDatabaseProductName());
                System.out.println("Database Product Version: " + metaData.getDatabaseProductVersion());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức main để kiểm tra kết nối
    public static void main(String[] args) {
        Connection connection = JDBCUtil.getConnection();
        if (connection != null) {
            System.out.println("Kết nối thành công: " + connection);
            JDBCUtil.printInfo(connection);
            JDBCUtil.closeConnection(connection);
        } else {
            System.out.println("Kết nối thất bại!");
        }
    }
}
	