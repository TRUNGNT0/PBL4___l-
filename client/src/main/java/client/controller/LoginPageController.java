package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import client.model.BO.M_Login;
import client.view.LoginPage;

public class LoginPageController implements ActionListener{
	private LoginPage view;
	private NetworkController networkController;
	private M_Login loginHandler;
	
	
	
	public LoginPageController(NetworkController networkController) {
		view = new LoginPage(this);
		this.networkController = networkController;
		this.loginHandler = new M_Login();
		
		view.setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
	    case "Login":
	        btn_Login_Click();
	        break;
	        
	    case "Sign up":
	        btn_SignUp_Click();
	        break;
	        
	    default:
	        view.showError("Lựa chọn không hợp lệ");
	        break;
	}

	}
	
	private void btn_Login_Click() {
		String username = view.getUsername();
		String password = view.getPassword();
		if (username.isEmpty() || password.isEmpty()) {
			view.showError("Vui lòng nhập đầy đủ thông tin");
			return;
		} 

		networkController.connect();
		boolean success = false;
		try {
			success = loginHandler.login(username, password, networkController.getInputStream(), networkController.getOutputStream());
			if(success) {
				networkController.receiveSessionId();
			}
		} catch (IOException e) {
			view.showError("Có lỗi trong quá trình đăng nhập, kiểm tra kết nối");
			e.printStackTrace();
		}
		networkController.disconnect();
		
		if(success) {
			new HomePageController(username, networkController);
            view.setVisible(false); 
            view.dispose();
		} else {
			view.showError("Tên đăng nhập hoặc mật khẩu không chính xác");
			return;
		}
    }
	
	private void btn_SignUp_Click() {
		new SignUpPageController(networkController); // Tạo controller cho SignUpPage
	}
}
