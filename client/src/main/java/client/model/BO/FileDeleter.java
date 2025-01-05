package client.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import client.model.Bean.FileInformation;

public class FileDeleter {

    public FileDeleter() {
        
    }
    
    public boolean deleteFiles(String currentDirectoryPath, List<FileInformation> fileInformationList, DataInputStream dis, DataOutputStream dos) {
    	boolean isDeleted = false;
    	try {
            dos.writeUTF(currentDirectoryPath); // Gửi đường dẫn hiện tại
            dos.writeInt(fileInformationList.size());
            
            for (FileInformation fileInformation : fileInformationList) {
            	fileInformation.sendFileInformation(dos);
            }
            for(FileInformation fileInformation : fileInformationList) {
                isDeleted = dis.readBoolean();
                if (isDeleted) {
                    System.out.println("Đã xóa file: " + fileInformation.getName());
                } else {
                    System.out.println("Không thể xóa file: " + fileInformation.getName());
                }
                
            }
            return isDeleted;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
