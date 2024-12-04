package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.model.BO.FileDownloader;
import client.view.SettingPage;

public class SettingPageController implements ActionListener{
	SettingPage view;
	FileDownloader model;
	
	public SettingPageController(FileDownloader model) {
		this.model = model;
		view = new SettingPage(this);
		view.txtDownloadDirectorySetText(model.getDownLoadDirectoryPath());
		view.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		switch (s) {
		case "Choose Download Directory":
			btn_ChooseDownloadDirectory_Click();
			break;

		default:
			view.showError("Lựa chọn không hợp lệ!");
			break;
		}
	}
	
	private void btn_ChooseDownloadDirectory_Click() {
		String newDownloadDirectory = view.selectFolder();
		if(!newDownloadDirectory.isEmpty()) {
			view.txtDownloadDirectorySetText(newDownloadDirectory);
			model.setDownLoad(newDownloadDirectory);
		}
	}
}
