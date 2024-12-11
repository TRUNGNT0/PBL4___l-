package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.model.BO.M_Login;
import client.view.HomePage;
import client.view.LoginPage;
import client.controller.SignUpPageController;;

public class LoginPageController implements ActionListener{
	private LoginPage view;
	private NetworkController networkController;
	private M_Login loginHandler;
	
	public LoginPageController() {
		view = new LoginPage(this);
		this.networkController = new NetworkController("116.105.208.83", 8888);
		this.loginHandler = new M_Login();
		
		view.setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if ("Login".equals(command)) {
			btn_Login_Click();
		} else if ("Sign up".equals(command)) {
			btn_SignUp_Click();
		}
	}
	
	private void btn_Login_Click() {
		String username = view.getUsername();
		String password = view.getPassword();
		
		if (username.isEmpty() || password.isEmpty()) {
			view.showError("Vui lòng nhập đầy đủ thông tin");
			return;
		} else {
			boolean success = login(username, password);
			if(success) {
				new HomePageController(username, networkController);
                view.setVisible(false); // Ẩn V_Login
                view.dispose(); // Giải phóng bộ nhớ của V_Login
			} else {
				view.showError("Tên đăng nhập hoặc mật khẩu không chính xác");
				return;
			}
		}
    }
	private void btn_SignUp_Click() {
		view.setVisible(false); // Ẩn LoginPage
		new SignUpPageController(networkController); // Tạo controller cho SignUpPage
	}
	
	private boolean login(String username, String password) {
		networkController.connect();
		boolean success = loginHandler.login(username, password, networkController.getInputStream(), networkController.getOutputStream());
		networkController.disconnect();
		return success;
	}
}
