package client.test;

import client.controller.LoginPageController;
import client.controller.NetworkController;

public class Program {
	public static void main(String[] args) {
		new LoginPageController(new NetworkController("localhost", 8888));
	}
}
