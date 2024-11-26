package client.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class FileDeleter {

    public FileDeleter() {
        
    }

    public void deleteFiles(String currentDirectoryPath, List<String> fileList, DataInputStream dis, DataOutputStream dos) {
        try {
            dos.writeUTF(currentDirectoryPath); // Gửi đường dẫn hiện tại
            sendFileNameListToServer(fileList, dos);

            // Xử lý phản hồi từ server
            for (String fileName : fileList) {
                boolean isDeleted = dis.readBoolean();
                if (isDeleted) {
                    System.out.println("Đã xóa file: " + fileName);
                } else {
                    System.out.println("Không thể xóa file: " + fileName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendFileNameListToServer(List<String> fileList, DataOutputStream dos) {
        try {
            dos.writeInt(fileList.size());
            for (String fileName : fileList) {
                dos.writeUTF(fileName);
            }
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
