package client.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileLister {
    private String currentDirectoryPath;

    public FileLister(String userName) {
        this.currentDirectoryPath = "\\" + userName;
    }

    public String[] loadFileList(DataInputStream dis, DataOutputStream dos) {
        try {
            dos.writeUTF(currentDirectoryPath); // Gửi đường dẫn hiện tại
            dos.flush();

            int fileCount = dis.readInt(); // Nhận số lượng file
            if (fileCount > 0) {
                String[] fileList = new String[fileCount];
                for (int i = 0; i < fileCount; i++) {
                    fileList[i] = dis.readUTF(); // Nhận từng tên file
                }
                return fileList;
            } else {
                System.out.println("Thư mục trống hoặc không thể đọc được.");
                return new String[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}
