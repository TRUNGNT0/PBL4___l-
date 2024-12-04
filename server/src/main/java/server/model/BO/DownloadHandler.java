package server.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import server.controller.MyServer;
import server.model.Bean.FileInformation;

public class DownloadHandler {
    
    public void downLoadHandler(DataInputStream dis, DataOutputStream dos) {
        try {
            String directoryPath = MyServer.getHomeDirectoryPath() + dis.readUTF();
            FileInformation fileInformation = new FileInformation();
            fileInformation.receiveFileInformation(dis);
            File file = new File(directoryPath, fileInformation.getName());
            fileInformation = new FileInformation(file.getName(), file.lastModified(), file.length(), file.isFile());
            fileInformation.sendFileInformation(dos);
            if (file.exists() && file.isFile()) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
