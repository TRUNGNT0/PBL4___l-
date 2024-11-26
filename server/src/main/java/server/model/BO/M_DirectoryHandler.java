package server.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import server.model.Bean.FileInformation;

public class M_DirectoryHandler extends M_ServerFileHandler {
    public M_DirectoryHandler(String homeDirectoryPath) {
        super(homeDirectoryPath);
    }

    public void loadHandler(DataInputStream dis, DataOutputStream dos) {
        try {
            File directory = new File(this.homeDirectoryPath + dis.readUTF());
            File[] files = directory.isDirectory() ? directory.listFiles() : null;

            if (files != null) {
                dos.writeInt(files.length);
                for (File file : files) {
                	FileInformation fileInformation = new FileInformation(file.getName(), file.lastModified(), file.length(), file.isFile());
                	fileInformation.sendFileInformation(dos);
                    //dos.writeUTF(file.getName());
                }
            } else {
                dos.writeInt(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
