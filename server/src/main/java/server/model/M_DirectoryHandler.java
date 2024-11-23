package server.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

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
                    dos.writeUTF(file.getName());
                }
            } else {
                dos.writeInt(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
