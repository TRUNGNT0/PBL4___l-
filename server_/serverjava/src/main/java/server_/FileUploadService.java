package server_;

import org.java_websocket.WebSocket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class FileUploadService {

    private static final String UPLOAD_DIR = "D:\\PBL4\\fileseverPBL4"; // Thư mục lưu file upload

    public void handleFileUpload(WebSocket conn, String email, String data) {
        try {
            // Dữ liệu file được mã hóa Base64 (gửi từ client)
            String[] fileParts = data.split(",", 2); // Format: "fileName,base64Data"
            if (fileParts.length != 2) {
                conn.send("Invalid file format.");
                return;
            }

            String fileName = fileParts[0];
            String base64FileData = fileParts[1];

            // Giải mã Base64 thành byte
            byte[] fileData = Base64.getDecoder().decode(base64FileData);

            // Tạo thư mục theo email nếu chưa tồn tại
            String upload = UPLOAD_DIR + "\\" + email;
            System.err.println(email);
            File userDir = new File(upload);
            if (!userDir.exists()) {
                userDir.mkdirs();
            }

            // Lưu file vào thư mục theo tên email
            File file = new File(userDir+ "\\" +fileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(fileData);
            }

            conn.send("File uploaded successfully: " + fileName);
        } catch (IOException e) {
            conn.send("Error uploading file.");
            e.printStackTrace();
        }
    }
}

