package client.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import client.model.Bean.FileInformation;

public class FileLister {

    public FileLister() {
    	
    }

    public FileInformation[] loadFileList(String currentDirectoryPath, DataInputStream dis, DataOutputStream dos) {
        try {
            dos.writeUTF(currentDirectoryPath); // Gửi đường dẫn hiện tại
            dos.flush();

            int fileCount = dis.readInt(); // Nhận số lượng file
            if (fileCount > 0) {
            	FileInformation[] fileList = new FileInformation[fileCount];
                for (int i = 0; i < fileCount; i++) {
                	FileInformation fileInformation = new FileInformation();
                	fileInformation.receiveFileInformation(dis);
                	fileList[i] = fileInformation;
                	//System.out.println(fileInformation.toString());
                	
                }
                return fileList;
            } else {
                System.out.println("Thư mục trống hoặc không thể đọc được.");
                return new FileInformation[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new FileInformation[0];
        }
    }
}
