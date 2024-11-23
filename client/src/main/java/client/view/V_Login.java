package client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.controller.C_MyClient;

public class V_Login extends JFrame {
    private static final long serialVersionUID = 1L;

    // Components
    private JLabel lb_UserName;
    private JLabel lb_Password;
    private JTextField tf_UserName;
    private JTextField tf_Password;
    private JButton btn_Login;

    // Controller
    private C_MyClient controller;

    // Constructor
    public V_Login() {
        init();
        controller = C_MyClient.getInstance("localhost", 8888); // Giả sử controller đã được khởi tạo
    }

    // Initialize components and layout
    private void init() {
        // JFrame configuration
        this.setTitle("Đăng nhập");
        this.setSize(853, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.decode("#CFE1B9"));

        // Center panel for form layout
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.decode("#CFE1B9"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets.set(10, 10, 10, 10); // Add padding between components

        // Username label and text field
        lb_UserName = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(lb_UserName, gbc);

        tf_UserName = new JTextField();
        tf_UserName.setPreferredSize(new Dimension(200, 30)); // Set size of the text field
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(tf_UserName, gbc);

        // Password label and text field
        lb_Password = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(lb_Password, gbc);

        tf_Password = new JTextField();
        tf_Password.setPreferredSize(new Dimension(200, 30)); // Set size of the text field
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(tf_Password, gbc);

        // South panel for the login button
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.decode("#CFE1B9"));

        btn_Login = new JButton("Đăng nhập");
        btn_Login.setPreferredSize(new Dimension(150, 40)); // Optional, for consistent button size
        btn_Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin(); // Handle login when button is clicked
            }
        });
        southPanel.add(btn_Login);

        // Add panels to frame
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    // Handle login process
    private void handleLogin() {
        String username = tf_UserName.getText().trim();
        String password = tf_Password.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            // Nếu thiếu trường nào thì hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            // Nếu tất cả trường đều có dữ liệu, gửi dữ liệu cho controller để xử lý
            boolean success = controller.login(username, password);
            if (success) {
                // Đăng nhập thành công, hiển thị V_TrangChu
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                V_TrangChu trangChu = new V_TrangChu(); // Tạo đối tượng V_TrangChu
                trangChu.setVisible(true); // Hiển thị V_TrangChu
                this.setVisible(false); // Ẩn V_Login
                this.dispose(); // Giải phóng bộ nhớ của V_Login
            } else {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không chính xác!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        V_Login loginFrame = new V_Login();
        loginFrame.setVisible(true);
    }
}
