package client.controller;

import client.model.BO.SignUpHandler;
import client.view.SignUpPage;

public class SignUpPageController {
	private NetworkController networkController;
	private SignUpHandler signUpHandler;
	private SignUpPage view;
	
	public SignUpPageController(NetworkController networkController) {
		this.networkController = networkController;
		this.signUpHandler = new SignUpHandler();
		this.view = new SignUpPage(this);
	}
	
	
}
