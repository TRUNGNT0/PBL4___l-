package client.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import client.model.Bean.FileInformation;

public class DirectoryHandler {
	private String currentDirectoryPath;

	public DirectoryHandler(String username) {
    	currentDirectoryPath = "\\" + username;
    }
    
	public String getCurrentDirectoryPath() {
		return currentDirectoryPath;
	}
	
    public void goToDirectory(String newDirectory) {
    	currentDirectoryPath += "\\" + newDirectory;
    }
    
    public void goBackDirectory() {
        // Tìm vị trí dấu "\" cuối cùng
        int lastSeparatorIndex = currentDirectoryPath.lastIndexOf("\\");
        if (!(lastSeparatorIndex == -1 || lastSeparatorIndex == 0)) {
            // Không tìm thấy dấu "\", hoặc đang ở thư mục cha trả về gốc
        	currentDirectoryPath = currentDirectoryPath.substring(0, lastSeparatorIndex);
        }
    }

    public List<FileInformation> loadFileList(DataInputStream dis, DataOutputStream dos) {
    	List<FileInformation> fileInformationList = new ArrayList<FileInformation>();
        try {
            dos.writeUTF(currentDirectoryPath); // Gửi đường dẫn hiện tại
            dos.flush();

            int fileCount = dis.readInt(); // Nhận số lượng file
            if (fileCount > 0) {
                for (int i = 0; i < fileCount; i++) {
                	FileInformation fileInformation = new FileInformation();
                	fileInformation.receiveFileInformation(dis);
                	fileInformationList.add(fileInformation);
                	//System.out.println(fileInformation.toString());
                }
                return fileInformationList;
            } else {
                System.out.println("Thư mục trống hoặc không thể đọc được.");
                return fileInformationList;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return fileInformationList;
        }
    }
}
