package client.model;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUploader {
    private String currentDirectoryPath;

    public FileUploader(String userName) {
        this.currentDirectoryPath = "\\" + userName;
    }

    public void uploadFile(String filePath, DataOutputStream dos) {
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            // Gửi đường dẫn hiện tại, tên file và kích thước file
            dos.writeUTF(currentDirectoryPath);
            dos.writeUTF(file.getName());
            dos.writeLong(file.length());

            // Gửi dữ liệu file
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, bytesRead);
            }
            dos.flush();

            System.out.println("Đã upload file: " + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
