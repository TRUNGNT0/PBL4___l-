package server.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import server.controller.MyServer;
import server.model.Bean.FileInformation;

public class DirectoryHandler {
	
	public void loadHandler(DataInputStream dis, DataOutputStream dos) {
        try {
            File directory = new File(MyServer.getHomeDirectoryPath() + dis.readUTF());
            File[] files = directory.isDirectory() ? directory.listFiles() : null;

            if (files != null) {
                dos.writeInt(files.length);
                for (File file : files) {
                	FileInformation fileInformation = new FileInformation(file.getName(), file.lastModified(), file.length(), file.isFile());
                	fileInformation.sendFileInformation(dos);
                }
            } else {
                dos.writeInt(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public boolean createFolderIfNotExists(String folderPath) {
        File folder = new File(MyServer.getHomeDirectoryPath() + folderPath);
        return folder.exists() || folder.mkdirs();
    }
}
