package server.model.BO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import server.controller.MyServer;
import server.model.Bean.FileInformation;

public class DownloadHandler {
    
//    public void downLoadHandler(DataInputStream dis, DataOutputStream dos, SecretKey secretKey) {
//        try { 
//            String directoryPath = MyServer.getHomeDirectoryPath() + dis.readUTF();
//            FileInformation fileInformation = new FileInformation();
//            fileInformation.receiveFileInformation(dis);
//            File file = new File(directoryPath, fileInformation.getName());
//            fileInformation = new FileInformation(file.getName(), file.lastModified(), file.length(), file.isFile());
//            fileInformation.sendFileInformation(dos);
//            if (file.exists() && file.isFile()) {
//                try (FileInputStream fis = new FileInputStream(file)) {
//                    byte[] buffer = new byte[4096];
//                    int bytesRead;
//                    while ((bytesRead = fis.read(buffer)) != -1) {
//                        dos.write(buffer, 0, bytesRead);
//                    }
//                }
//            } else {
//                dos.writeLong(0);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
	
	public void downLoadHandler(DataInputStream dis, DataOutputStream dos, SecretKey secretKey) {
	    try { 
	        String directoryPath = MyServer.getHomeDirectoryPath() + dis.readUTF();
	        FileInformation fileInformation = new FileInformation();
	        fileInformation.receiveFileInformation(dis);

	        File file = new File(directoryPath, fileInformation.getName());
	        fileInformation = new FileInformation(file.getName(), file.lastModified(), file.length(), file.isFile());
	        fileInformation.sendFileInformation(dos);

	        if (file.exists() && file.isFile()) {
	            // Tạo Cipher cho AES ở chế độ CBC
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	            byte[] iv = new byte[16]; // Vector IV ngẫu nhiên (có thể thay thế bằng giá trị ngẫu nhiên thực tế)
	            IvParameterSpec ivSpec = new IvParameterSpec(iv);

	            // Khởi tạo Cipher
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

	            // Gửi IV đến client
	            dos.write(iv);

	            // Đọc và mã hóa dữ liệu từ file
	            try (FileInputStream fis = new FileInputStream(file)) {
	                byte[] buffer = new byte[4096];
	                byte[] encryptedBuffer;
	                int bytesRead;
	                while ((bytesRead = fis.read(buffer)) != -1) {
	                    encryptedBuffer = cipher.update(buffer, 0, bytesRead); // Mã hóa dữ liệu
	                    if (encryptedBuffer != null) {
	                        dos.write(encryptedBuffer);
	                    }
	                }
	                // Kết thúc mã hóa
	                encryptedBuffer = cipher.doFinal();
	                if (encryptedBuffer != null) {
	                    dos.write(encryptedBuffer);
	                }
	                dos.flush();
	            }
	        } else {
	            dos.writeLong(0); // Gửi thông báo file không tồn tại
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    
//    public void downLoadHandlerWithZip(DataInputStream dis, DataOutputStream dos) {
//		try {
//			String currentDirectoryPath = dis.readUTF();
//			int listSize = dis.readInt();
//			List<FileInformation> fileInformationList = new ArrayList<FileInformation>();
//			for(int i = 0; i < listSize; i++) {
//				FileInformation fileInfo = new FileInformation();
//				fileInfo.receiveFileInformation(dis);
//				fileInformationList.add(fileInfo);
//			}
//			ZipOutputStream zos = new ZipOutputStream(dos);
//	    	String zipDirectoryName = generateRandomString();
//	    	FileInformation fileInfomation = new FileInformation();
//	    	fileInfomation.setName(zipDirectoryName);
//	    	fileInfomation.sendFileInformation(dos);
//	    	
//	    	for(FileInformation fileInfo : fileInformationList){
//	    		File file = new File(MyServer.getHomeDirectoryPath() + "\\" + currentDirectoryPath,
//	    				fileInfo.getName());
//				zipFile(file, file.getName(),zos);
//	    	}
//			zos.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
	
	public void downLoadHandlerWithZip(DataInputStream dis, DataOutputStream dos, SecretKey secretKey) {
	    try {
	        // Nhận đường dẫn thư mục hiện tại từ client
	        String currentDirectoryPath = dis.readUTF();

	        // Nhận số lượng file trong danh sách
	        int listSize = dis.readInt();
	        List<FileInformation> fileInformationList = new ArrayList<>();
	        for (int i = 0; i < listSize; i++) {
	            FileInformation fileInfo = new FileInformation();
	            fileInfo.receiveFileInformation(dis);
	            fileInformationList.add(fileInfo);
	        }

	        // Tạo tên file ZIP ngẫu nhiên
	        String zipDirectoryName = generateRandomString();
	        FileInformation zipFileInfo = new FileInformation();
	        zipFileInfo.setName(zipDirectoryName);
	        zipFileInfo.sendFileInformation(dos);

	        // Tạo Cipher cho AES ở chế độ CBC
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        byte[] iv = new byte[16]; // Vector IV ngẫu nhiên
	        new Random().nextBytes(iv); // Tạo IV ngẫu nhiên
	        IvParameterSpec ivSpec = new IvParameterSpec(iv);

	        // Khởi tạo Cipher với chế độ ENCRYPT_MODE
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

	        // Gửi IV đến client
	        dos.write(iv);

	        // Nén và mã hóa dữ liệu
	        try (ZipOutputStream zos = new ZipOutputStream(new CipherOutputStream(dos, cipher))) {
	            for (FileInformation fileInfo : fileInformationList) {
	                File file = new File(MyServer.getHomeDirectoryPath() + "\\" + currentDirectoryPath, fileInfo.getName());
	                zipFile(file, file.getName(), zos);
	            }
	        }

	        System.out.println("Đã nén và mã hóa dữ liệu thành công.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    
    public void zipFile(File file, String fileName,ZipOutputStream zos) throws IOException {
    	if (file.isDirectory()) {
            if (fileName.endsWith("\\")) {
                zos.putNextEntry(new ZipEntry(fileName));
                zos.closeEntry();
            } else {
            	zos.putNextEntry(new ZipEntry(fileName + "\\"));
            	zos.closeEntry();
            }
            File[] children = file.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "\\" + childFile.getName(), zos);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(fileName);
		zos.putNextEntry(zipEntry);
		byte[] bytes = new byte[4096];
	    int length;
	    while ((length = fis.read(bytes)) >= 0) {
	       	zos.write(bytes, 0, length);
	    }
	    zos.closeEntry();
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
