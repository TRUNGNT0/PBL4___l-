package server.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import server.view.AdminPage;

public class AdminPageController implements ActionListener{

	AdminPage view;
	public AdminPageController() {
		this.view = new AdminPage(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

}
