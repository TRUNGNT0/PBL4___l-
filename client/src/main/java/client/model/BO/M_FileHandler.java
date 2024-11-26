package client.model.BO;

import java.io.*;
import java.util.List;

import client.model.Bean.FileInformation;

public class M_FileHandler {
	private String currentDirectoryPath;
	
    private FileUploader fileUploader;
    private FileDownloader fileDownloader;
    private FileLister fileLister;
    private FileDeleter fileDeleter;

    public M_FileHandler(String username) {
    	currentDirectoryPath = "\\" + username;
    	
        fileUploader = new FileUploader();
        fileDownloader = new FileDownloader();
        fileLister = new FileLister();
        fileDeleter = new FileDeleter();
    }
    
    public void goToDirectory(String directory) {
    	currentDirectoryPath += "\\" + directory;
    }
    
    public void goBackDirectory() {
        // Tìm vị trí dấu "\" cuối cùng
        int lastSeparatorIndex = currentDirectoryPath.lastIndexOf("\\");
        if (!(lastSeparatorIndex == -1 || lastSeparatorIndex == 0)) {
            // Không tìm thấy dấu "\", hoặc đang ở thư mục cha trả về gốc
        	currentDirectoryPath = currentDirectoryPath.substring(0, lastSeparatorIndex);
        }
    }

    public void upload(String filePath, DataOutputStream dos) {
        fileUploader.uploadFile(currentDirectoryPath, filePath, dos);
    }

    public boolean checkDownLoad() {
    	return fileDownloader.checkDownLoad();
    }
    
    public void setDownLoad(String path) {
    	fileDownloader.setDownLoad(path);
    }
    
    public void download(List<String> fileList, String downloadDirectory, DataInputStream dis, DataOutputStream dos) {
        fileDownloader.downloadFiles(currentDirectoryPath, fileList, downloadDirectory, dis, dos);
    }

    public FileInformation[] load(DataInputStream dis, DataOutputStream dos) {
        return fileLister.loadFileList(currentDirectoryPath, dis, dos);
    }

    public void delete(List<String> fileList, DataInputStream dis, DataOutputStream dos) {
        fileDeleter.deleteFiles(currentDirectoryPath, fileList, dis, dos);
    }
}
