package client.model;

import java.io.*;
import java.util.List;

public class FileDownloader {
    private String currentDirectoryPath;

    public FileDownloader(String userName) {
        this.currentDirectoryPath = "\\" + userName;
    }

    public void downloadFiles(List<String> fileList, String downloadDirectory, DataInputStream dis, DataOutputStream dos) {
        try {
            dos.writeUTF(currentDirectoryPath); // Gửi đường dẫn hiện tại
            sendFileNameListToServer(fileList, dos);

            for (String fileName : fileList) {
                long fileSize = dis.readLong(); // Nhận kích thước file
                File file = new File(downloadDirectory, fileName);

                // Tạo thư mục nếu chưa tồn tại
                file.getParentFile().mkdirs();

                // Ghi dữ liệu file vào ổ đĩa
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
                    byte[] buffer = new byte[4096];
                    long totalRead = 0;
                    int bytesRead;

                    while (totalRead < fileSize && (bytesRead = dis.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                        totalRead += bytesRead;
                    }
                    bos.flush();
                }

                System.out.println("Đã tải file: " + fileName + " về " + downloadDirectory);
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
