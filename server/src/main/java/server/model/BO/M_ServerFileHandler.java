package server.model.BO;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class M_ServerFileHandler {
    protected String homeDirectoryPath;

    public M_ServerFileHandler(String homeDirectoryPath) {
        this.homeDirectoryPath = homeDirectoryPath;
    }

    public List<String> receiveFileNameListFromClient(DataInputStream dis) {
        List<String> fileList = new ArrayList<>();
        try {
            int fileCount = dis.readInt();
            for (int i = 0; i < fileCount; i++) {
                fileList.add(dis.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public boolean createFolderIfNotExists(String folderPath) {
        File folder = new File(this.homeDirectoryPath + folderPath);
        return folder.exists() || folder.mkdirs();
    }
}
