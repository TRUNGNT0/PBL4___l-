package client.model.BO;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import client.model.Bean.FileInformation;

public class FileUploader {

	
	
    public FileUploader() {
        
    }
    
    
    public void uploadFile(String currentDirectoryPath, String filePath, DataOutputStream dos) throws IOException {
        File file = new File(filePath);
        FileInformation fileInformation = new FileInformation(file.getName(), file.lastModified(), file.length(), file.isFile());

        try (FileInputStream fis = new FileInputStream(file)) {
            // Gửi đường dẫn hiện tại và thông tin file
            dos.writeUTF(currentDirectoryPath);
            fileInformation.sendFileInformation(dos);

            // Gửi dữ liệu file
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, bytesRead);
            }
            dos.flush();

            System.out.println("Đã upload file: " + file.getName());
        }
    }

    
    
}
