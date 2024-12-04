package server.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import server.controller.MyServer;
import server.model.Bean.FileInformation;

public class DeleteHandler {
    public DeleteHandler() {
        
    }

    public void deleteHandler(DataInputStream dis, DataOutputStream dos) {
        try {
            String directoryPath = MyServer.getHomeDirectoryPath() + dis.readUTF();
            
            //Nhận danh sách các file muốn xóa
            int fileCount = dis.readInt();
            List<FileInformation> fileList = new ArrayList<FileInformation>();
            for(int i = 0; i < fileCount; i++) {
            	FileInformation fileInformation = new FileInformation();
            	fileInformation.receiveFileInformation(dis);
            	fileList.add(fileInformation);
            }
            for (FileInformation fileInformation : fileList) {
                File file = new File(directoryPath, fileInformation.getName());
                dos.writeBoolean(deleteFile(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private boolean deleteFile(File file) {
    	if(file.exists() && file.isFile()) {
    		return file.delete();
    	} else if(file.exists() && file.isDirectory()) {
    		File[] fileList = file.listFiles();
    		for(File f : fileList) {
    			deleteFile(f);
    		}
    		return file.delete();
    	}
    	return false;
    }
}
