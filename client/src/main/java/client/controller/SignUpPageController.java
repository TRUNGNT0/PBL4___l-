package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.model.BO.SignUpHandler;
import client.view.SignUpPage;

public class SignUpPageController implements ActionListener {
    private SignUpPage view;
    private NetworkController networkController;
    private SignUpHandler signUpHandler;

    public SignUpPageController(NetworkController networkController) {
        this.networkController = networkController;
        this.signUpHandler = new SignUpHandler();
        this.view = new SignUpPage(this);
        this.view.setVisible(true); // Hiển thị giao diện SignUpPage
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("Sign up".equals(command)) {
            btn_SignUp_Click();
        } else if ("Back".equals(command)) {
            btn_Back_Click();
        }
    }

    // Xử lý sự kiện nút "Đăng ký"
    private void btn_SignUp_Click() {
        String username = view.getUsername();
        String password = view.getPassword();
        String confirmPassword = view.getConfirmPassword();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            view.showError("Vui lòng nhập đầy đủ thông tin!");
			return;
        }

        if (!password.equals(confirmPassword)) {
            view.showError("Mật khẩu xác nhận không khớp!");
			return;
        }

        // Kết nối và gửi yêu cầu đăng ký
        networkController.connect();
        boolean success = signUpHandler.signUp(username, password, networkController.getInputStream(), networkController.getOutputStream());
        networkController.disconnect();

        if (success) {
            view.dispose(); // Đóng SignUpPage
            new LoginPageController(); // Chuyển về trang đăng nhập
        } else {
            view.showError("Đăng ký thất bại, vui lòng thử lại!");
        }
    }

    // Xử lý sự kiện nút "Quay lại"
    private void btn_Back_Click() {
        view.dispose(); // Đóng SignUpPage
        new LoginPageController(); // Chuyển về trang đăng nhập
    }
}
