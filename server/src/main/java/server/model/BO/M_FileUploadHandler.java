package server.model.BO;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class M_FileUploadHandler extends M_ServerFileHandler {
    public M_FileUploadHandler(String homeDirectoryPath) {
        super(homeDirectoryPath);
    }

    public void upLoadHandler(DataInputStream dis) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
                this.homeDirectoryPath + dis.readUTF() + "\\" + dis.readUTF()))) {
            long fileSize = dis.readLong();
            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalRead = 0;

            while (totalRead < fileSize && (bytesRead = dis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
                totalRead += bytesRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
