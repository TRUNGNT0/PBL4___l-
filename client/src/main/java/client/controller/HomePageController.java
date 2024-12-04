package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import client.model.BO.DirectoryHandler;
import client.model.BO.FileDeleter;
import client.model.BO.FileDownloader;
import client.model.BO.FileUploader;
import client.model.Bean.FileInformation;
import client.view.HomePage;

public class HomePageController implements ActionListener{
    private NetworkController networkController;
    private HomePage view;
    
    private DirectoryHandler directoryHandler;
    private FileDeleter fileDeleter;
    private FileDownloader fileDownloader;
    private FileUploader fileUploader;

    public HomePageController(String user, NetworkController networkController) {
        this.networkController = networkController;
        view = new HomePage(this);
        
        directoryHandler = new DirectoryHandler(user);
        fileDeleter = new FileDeleter();
        fileDownloader = new FileDownloader();
        fileUploader = new FileUploader();
        
        btn_Load_Click();
        view.setVisible(true);
    }
    @Override
	public void actionPerformed(ActionEvent e) {
    	String action = e.getActionCommand();
        switch (action) {
            case "UpLoad":
                btn_Upload_Click();
                break;
                
            case "Load":
                btn_Load_Click();;
                break;
                
            case "DownLoad":
                btn_Download_Click();
                break;

            case "Delete":
            	btn_Delete_Click();
                break;
                
            case "Back":
            	btn_Back_Click();
            	break;
            	
            case "Setting":
            	btn_Setting_Click();
            	break;
            	
            default:
                view.showError("Lựa chọn không hợp lệ!");
                break;
        }
		
	}
    
    private void btn_Upload_Click() {
    	String path = view.chooseFile();
    	upLoadFile(path);
    	btn_Load_Click();
    }
    
    private void upLoadFile(String path) {
    	networkController.connect();
    	networkController.sendCommand("UP_LOAD");
    	fileUploader.uploadFile(directoryHandler.getCurrentDirectoryPath(), path,
    			networkController.getOutputStream());
    	networkController.disconnect();
    }
    
    private void btn_Load_Click() {
    	networkController.connect();
    	networkController.sendCommand("LOAD");
		view.updateFileList(directoryHandler.loadFileList(networkController.getInputStream(),
				networkController.getOutputStream()));
		networkController.disconnect();
	}
    
    private void btn_Download_Click() {
    	if(!fileDownloader.checkDownLoad()) {
    		fileDownloader.setDownLoad(view.chooseFolder());
    	} else {
    		List<FileInformation> listFile = view.getselectedFile();
    		if(listFile.size() == 1) {
    			networkController.connect();
            	networkController.sendCommand("DOWN_LOAD");
            	fileDownloader.downloadFile(directoryHandler.getCurrentDirectoryPath(), listFile.getFirst(), 
            			networkController.getInputStream(), networkController.getOutputStream());
            	networkController.disconnect();
    		}
    	}
    }

	private void btn_Delete_Click() {
		List<FileInformation> fileInformationList = view.getselectedFile();
		networkController.connect();
        networkController.sendCommand("DELETE");
        fileDeleter.deleteFiles(directoryHandler.getCurrentDirectoryPath(), fileInformationList, 
        		networkController.getInputStream(), networkController.getOutputStream());
        networkController.disconnect();
        
        btn_Load_Click();
    }
	
	private void btn_Back_Click() {
		directoryHandler.goBackDirectory();
		btn_Load_Click();
    }
	
	private void btn_Setting_Click() {
		new SettingPageController(this.fileDownloader);
	}
	
	public void componentFile_doubleClick(FileInformation fileInformation) {
		if(!fileInformation.isFile()) {
			directoryHandler.goToDirectory(fileInformation.getName());
			btn_Load_Click();
		}
	}
	
	
}
