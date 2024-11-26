package server.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class M_FileDeleteHandler extends M_ServerFileHandler {
    public M_FileDeleteHandler(String homeDirectoryPath) {
        super(homeDirectoryPath);
    }

    public void deleteHandler(DataInputStream dis, DataOutputStream dos) {
        try {
            String directoryPath = this.homeDirectoryPath + dis.readUTF();
            List<String> fileList = this.receiveFileNameListFromClient(dis);

            for (String fileName : fileList) {
                File file = new File(directoryPath, fileName);
                dos.writeBoolean(file.exists() && file.isFile() && file.delete());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
