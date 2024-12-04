package server.model.BO;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import server.controller.MyServer;
import server.model.Bean.FileInformation;

public class UploadHandler {
    
    public void upLoadHandler(DataInputStream dis) {
    	try {
			String currentDirectoryPath = dis.readUTF();
			FileInformation fileInformation = new FileInformation();
			fileInformation.receiveFileInformation(dis);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
	                MyServer.getHomeDirectoryPath() + currentDirectoryPath + "\\" + fileInformation.getName()));
			byte[] buffer = new byte[4096];
            int bytesRead;
            long totalRead = 0;
            while (totalRead < fileInformation.getSize() && (bytesRead = dis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
                totalRead += bytesRead;
            }
            bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
