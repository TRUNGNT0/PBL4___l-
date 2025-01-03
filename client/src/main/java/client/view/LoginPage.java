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
import javax.swing.UIManager;

import client.controller.LoginPageController;

public class LoginPage extends JFrame {
    private static final long serialVersionUID = 1L;

    // Components
    private JLabel lb_UserName;
    private JLabel lb_Password;
    private JTextField tf_UserName;
    private JPasswordField tf_Password;
    private JButton btn_Login;
    private JButton btn_SignUp; // New button for Sign Up

    // Controller
    private LoginPageController controller;

    // Constructor
    public LoginPage(LoginPageController controller) {
        this.controller = controller;
        init();
    }

    // Initialize components and layout
    private void init() {
    	try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // JFrame configuration
        this.setTitle("Login");
        this.setSize(853, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.decode("#F2DDDC"));

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

        // South panel for login and sign up buttons
        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);

        btn_Login = new JButton("Login");
        btn_Login.setPreferredSize(new Dimension(150, 40)); // Optional, for consistent button size
        btn_Login.addActionListener(controller);
        this.getRootPane().setDefaultButton(btn_Login);
        southPanel.add(btn_Login);

        btn_SignUp = new JButton("Sign up");
        btn_SignUp.setPreferredSize(new Dimension(150, 40));
        btn_SignUp.addActionListener(controller); // Add action listener for Sign Up
        southPanel.add(btn_SignUp);

        // Add panels to frame
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }
    public void showError(String text) {
        JOptionPane.showMessageDialog(this, text, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public String getUsername() {
        return tf_UserName.getText().trim();
    }

    public String getPassword() {
        return new String(tf_Password.getPassword());
    }
}
