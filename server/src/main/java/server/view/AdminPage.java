package server.view;

import javax.swing.JFrame;

import server.controller.AdminPageController;

public class AdminPage extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	AdminPageController controller;
	
	public AdminPage(AdminPageController controller) {
		this.controller = controller;
	}
	

}
