package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import client.controller.controllerInterface.ControllerInterface;
import client.model.BO.DirectoryHandler;
import client.model.BO.FileDeleter;
import client.model.BO.FileDownloader;
import client.model.BO.FileUploader;
import client.model.Bean.FileInformation;
import client.view.HomePage;

public class HomePageController implements ActionListener, ControllerInterface{
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
                
            case "New Directory":
                btn_NewDirectory_Click();
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
    	File file = new File(path);
    	if(!path.isEmpty() && file.isFile()) {
    		connect();
    		if(networkController.authenticToken()) {
            	networkController.sendCommand("UP_LOAD");
            	try {
					fileUploader.uploadFile(directoryHandler.getCurrentDirectoryPath(), path,
							networkController.getOutputStream());
				} catch (FileNotFoundException e) {
					view.showError("Hãy đóng file trước khi gửi");
					//e.printStackTrace();
				} catch (IOException e) {
					view.showError("Lỗi mạng không xác định");
					//e.printStackTrace();
				}
    		} else {
    			view.showError("Phiên đăng nhập đã hết hạn hãy đăng nhập lại");
    		}
        	disconnect();	
    	} else if (!path.isEmpty() && file.isDirectory()) {
    		
    	}
    	btn_Load_Click();
    }
    
    private void btn_Load_Click() {
    	connect();
    	if(networkController.authenticToken()) {
    		networkController.sendCommand("LOAD");
    		view.updateFileList(directoryHandler.loadFileList(networkController.getInputStream(),
    				networkController.getOutputStream()));
    	} else {
			view.showError("Phiên đăng nhập đã hết hạn hãy đăng nhập lại");
		}
		disconnect();
	}
    
    private void btn_Download_Click() {
    	if(!fileDownloader.checkDownLoad()) {
    		fileDownloader.setDownLoad(view.chooseFolder());
    	} else {
    		List<FileInformation> listFile = view.getselectedFile();
    		if(listFile.size() == 1 && listFile.getFirst().isFile()) {
    			connect();
    			if(networkController.authenticToken()) {
    				networkController.sendCommand("DOWN_LOAD");
                	fileDownloader.downloadFile(directoryHandler.getCurrentDirectoryPath(), listFile.getFirst(), 
                			networkController.getInputStream(), networkController.getOutputStream());
    			} else {
    				view.showError("Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại");
    			}
            	disconnect();
    		} else if(listFile.size() >= 1) {
    			connect();
    			if(networkController.authenticToken()) {
    				networkController.sendCommand("DOWN_LOAD_2");
                	fileDownloader.downloadFileWithZip(directoryHandler.getCurrentDirectoryPath(), listFile,
                			networkController.getInputStream(), networkController.getOutputStream());
    			} else {
    				view.showError("Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại");
    			}
            	disconnect();
    		}
    	}
    }

	private void btn_Delete_Click() {
		List<FileInformation> fileInformationList = view.getselectedFile();
		if(!fileInformationList.isEmpty()) {
			connect();
			if(networkController.authenticToken()) {
				networkController.sendCommand("DELETE");
		        fileDeleter.deleteFiles(directoryHandler.getCurrentDirectoryPath(), fileInformationList, 
		        		networkController.getInputStream(), networkController.getOutputStream());
			} else {
				view.showError("Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại");
			}
	        disconnect();
	        
	        btn_Load_Click();
		} else {
			view.showError("Chưa chọn file để xóa");
		}
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
	
	public void btn_NewDirectory_Click() {
		String newDirectory = view.showInputDialog("Nhập vào tên Directory muốn tạo");
		if(!newDirectory.isEmpty()) {
			FileInformation fileInformation = new FileInformation();
			fileInformation.setName(newDirectory);
			connect();
			if(networkController.authenticToken()) {
				networkController.sendCommand("NEW_DIRECTORY");
		        boolean success = directoryHandler.createNewDirectory(fileInformation, 
		        		networkController.getInputStream(), networkController.getOutputStream());
		        if(success) {
		        	btn_Load_Click();
		        } else {
		        	view.showError("Tên đã tồn tại hoặc gặp lỗi khi tạo thư mục");
		        }
			} else {
				view.showError("Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại");
			}
	        disconnect();
		}
	}
	@Override
	public void connect() {
		try {
			networkController.connect();
		} catch (SocketTimeoutException e) {
			view.showError("Mất quá nhiều thời gian để Server phản hồi");
			//e.printStackTrace();
		} catch (IOException e) {
			view.showError("Lỗi mạng không xác định khi kết nối");
			//e.printStackTrace();
		}
	}
	@Override
	public void disconnect() {
		try {
			networkController.disconnect();
		} catch (IOException e) {
			view.showError("Lỗi mạng không xác định khi ngắt kết nối");
			//e.printStackTrace();
		}
		
	}
	
}
