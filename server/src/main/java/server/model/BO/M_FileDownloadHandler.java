package server.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class M_FileDownloadHandler extends M_ServerFileHandler {
    public M_FileDownloadHandler(String homeDirectoryPath) {
        super(homeDirectoryPath);
    }

    public void downLoadHandler(DataInputStream dis, DataOutputStream dos) {
        try {
            String directoryPath = this.homeDirectoryPath + dis.readUTF();
            List<String> fileList = this.receiveFileNameListFromClient(dis);

            for (String fileName : fileList) {
                File file = new File(directoryPath, fileName);
                if (file.exists() && file.isFile()) {
                    dos.writeLong(file.length());
                    try (FileInputStream fis = new FileInputStream(file)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = fis.read(buffer)) != -1) {
                            dos.write(buffer, 0, bytesRead);
                        }
                    }
                } else {
                    dos.writeLong(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
