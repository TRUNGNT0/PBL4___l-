package server.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import server.controller.MyServer;
import server.model.Bean.FileInformation;

public class ZipFileHandler {

    public void sendFileWithZip(String currentDirectoryPath, List<FileInformation> fileInformationList, DataInputStream dis, DataOutputStream dos) {
    	ZipOutputStream zos = new ZipOutputStream(dos);
    	String zipDirectoryName = generateRandomString();
    	try {
			zos.putNextEntry(new ZipEntry(zipDirectoryName));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	for(FileInformation fileInfo : fileInformationList){
    		File file = new File(MyServer.getHomeDirectoryPath() + "\\" + currentDirectoryPath,
    				fileInfo.getName());
    		try {
				zipFile(file, zipDirectoryName + "\\" + file.getName(),zos);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	try {
			zos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void zipFile(File file, String fileName,ZipOutputStream zos) throws IOException {
		if(file.isDirectory()) {
			zos.putNextEntry(new ZipEntry(fileName));
			zos.closeEntry();
			File[] children = file.listFiles();
            for (File childFile : children) {
            	zipFile(childFile, fileName + "\\" + childFile.getName(), zos);
            }
		}
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(fileName);
		zos.putNextEntry(zipEntry);
		byte[] bytes = new byte[4096];
	    int length;
	    while ((length = fis.read(bytes)) >= 0) {
	       	zos.write(bytes, 0, length);
	    }
	    fis.close();
    }
    
    public String generateRandomString() {
        String prefix = "RemoveFolder";

        Random random = new Random();
        int randomNumber = random.nextInt(1000); // Số ngẫu nhiên từ 0 đến 999
        // Lấy thời gian hiện tại
        long currentTimeMillis = System.currentTimeMillis();

        return prefix + randomNumber + currentTimeMillis;
    }
}
