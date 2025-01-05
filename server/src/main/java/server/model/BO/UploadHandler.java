package server.model.BO;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;

import server.controller.MyServer;
import server.model.Bean.FileInformation;

public class UploadHandler {
    
//    public void upLoadHandler(DataInputStream dis) {
//    	try {
//			String currentDirectoryPath = dis.readUTF();
//			FileInformation fileInformation = new FileInformation();
//			fileInformation.receiveFileInformation(dis);
//			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
//	                MyServer.getHomeDirectoryPath() + currentDirectoryPath + "\\" + fileInformation.getName()));
//			byte[] buffer = new byte[4096];
//            int bytesRead;
//            long totalRead = 0;
//            while (totalRead < fileInformation.getSize() && (bytesRead = dis.read(buffer)) != -1) {
//                bos.write(buffer, 0, bytesRead);
//                totalRead += bytesRead;
//            }
//            bos.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
    
	public void upLoadHandler(DataInputStream dis, SecretKey secretKey) {
	    try {
	        // Đọc đường dẫn và thông tin file
	        String currentDirectoryPath = dis.readUTF();
	        FileInformation fileInformation = new FileInformation();
	        fileInformation.receiveFileInformation(dis);

	        // Giải mã dữ liệu file
	        Cipher cipher = Cipher.getInstance("AES");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey);

	        File outputFile = new File(MyServer.getHomeDirectoryPath() + currentDirectoryPath, fileInformation.getName());
	        try (CipherInputStream cis = new CipherInputStream(dis, cipher);
	             FileOutputStream fos = new FileOutputStream(outputFile)) {

	            byte[] buffer = new byte[4096];
	            int bytesRead;
	            while ((bytesRead = cis.read(buffer)) != -1) {
	                fos.write(buffer, 0, bytesRead);
	            }
	        }

	        System.out.println("Đã nhận file (đã giải mã): " + fileInformation.getName());
	    } catch (IOException | GeneralSecurityException e) {
	        e.printStackTrace();
	    }
	}
}
