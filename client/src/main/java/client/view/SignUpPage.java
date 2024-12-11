package client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.controller.SignUpPageController;

public class SignUpPage extends JFrame {
    private static final long serialVersionUID = 1L;

    // Components
    private JLabel lb_UserName;
    private JLabel lb_Password;
    private JLabel lb_ConfirmPassword;
    private JTextField tf_UserName;
    private JPasswordField tf_Password;
    private JPasswordField tf_ConfirmPassword;
    private JButton btn_SignUp;
    private JButton btn_Back;

    // Controller
    private SignUpPageController controller;

    // Constructor
    public SignUpPage(SignUpPageController controller) {
        this.controller = controller;
        init();
    }

    // Initialize components and layout
    private void init() {
        // JFrame configuration
        this.setTitle("Đăng ký tài khoản");
        this.setSize(853, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.decode("#EDEDED"));

        // Center panel for form layout
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components

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

        tf_Password = new JPasswordField();
        tf_Password.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(tf_Password, gbc);

        // Confirm password label and text field
        lb_ConfirmPassword = new JLabel("Confirm Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(lb_ConfirmPassword, gbc);

        tf_ConfirmPassword = new JPasswordField();
        tf_ConfirmPassword.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(tf_ConfirmPassword, gbc);

        // South panel for buttons
        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);

        btn_SignUp = new JButton("Sign up");
        btn_SignUp.setPreferredSize(new Dimension(150, 40));
        btn_SignUp.addActionListener(controller); // Attach controller
        southPanel.add(btn_SignUp);

        btn_Back = new JButton("Back");
        btn_Back.setPreferredSize(new Dimension(150, 40));
        
        btn_Back.addActionListener(controller); // Gửi về controller để xử lý
        southPanel.add(btn_Back);

        // Add panels to frame
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    public void showError(String text) {
        JOptionPane.showMessageDialog(this, text, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String text) {
        JOptionPane.showMessageDialog(this, text, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public String getUsername() {
        return tf_UserName.getText().trim();
    }

    public String getPassword() {
        return new String(tf_Password.getPassword());
    }

    public String getConfirmPassword() {
        return new String(tf_ConfirmPassword.getPassword());
    }
}
