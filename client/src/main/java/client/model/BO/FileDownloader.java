package client.model.BO;

import java.io.*;
import java.util.List;

import client.model.Bean.FileInformation;

public class FileDownloader {
	private String downLoadDirectoryPath;
	
    public FileDownloader() {
        String path = this.getDownLoadDirectoryPathFromFile();
        downLoadDirectoryPath = path;
    }
    
    public String getDownLoadDirectoryPath() {
		return downLoadDirectoryPath;
	}

	private String getDownLoadDirectoryPathFromFile() {
    	String path = null;
    	File f = new File("text.txt");
    	if (!f.exists()) {
    		try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	try {
			BufferedReader br = new BufferedReader(new FileReader(f));
//			BufferedReader br = Files.newBufferedReader(f.toPath(), StandardCharsets.UTF_8);
			path = br.readLine();
			br.close();
			return path;
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			return path;
		} catch (IOException e) {
			e.printStackTrace();
			return path = null;
		}
    }
    
    private boolean setDownLoadDirectoryPathToFile() {
    	try {
			File file = new File("text.txt");
			PrintWriter pw = new PrintWriter(file, "UTF-8");
			pw.println(downLoadDirectoryPath);
			pw.flush();
			pw.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public boolean checkDownLoad() {
    	if(downLoadDirectoryPath == null || downLoadDirectoryPath.isEmpty()) return false;
    	else return true;
    }
    
    public void setDownLoad(String path) {
    	downLoadDirectoryPath = path;
    	setDownLoadDirectoryPathToFile();
    }
    
    public void downloadFile(String currentDirectoryPath, FileInformation fileInformation, DataInputStream dis, DataOutputStream dos) {
        try {
            dos.writeUTF(currentDirectoryPath); // Gửi đường dẫn hiện tại
            fileInformation.sendFileInformation(dos);
            fileInformation.receiveFileInformation(dis);
            long fileSize = fileInformation.getSize(); // Nhận kích thước file
            File file = new File(downLoadDirectoryPath, fileInformation.getName());

            // Tạo thư mục nếu chưa tồn tại
            file.getParentFile().mkdirs(); 

            // Ghi dữ liệu file vào ổ đĩa
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
                byte[] buffer = new byte[4096];
                long totalRead = 0;
                int bytesRead;

                while (totalRead < fileSize && (bytesRead = dis.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                    totalRead += bytesRead;
                }
                bos.flush();
            }
                System.out.println("Đã tải file: " + fileInformation.getName() + " về " + downLoadDirectoryPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void downloadFileWithZip(String currentDirectoryPath, List<FileInformation> fileInformationList, DataInputStream dis, DataOutputStream dos) {
        try {
        	dos.writeUTF(currentDirectoryPath);
        	dos.writeInt(fileInformationList.size());
        	for(FileInformation fileInfor : fileInformationList) {
        		fileInfor.sendFileInformation(dos);
        	}
        	
        	
            // Nhận tên file ZIP từ server
            FileInformation zipFileInformation = new FileInformation();
            zipFileInformation.receiveFileInformation(dis);

            // Tạo file ZIP đích
            File zipFile = new File(downLoadDirectoryPath, zipFileInformation.getName() + ".zip");

            // Tạo thư mục lưu trữ nếu chưa tồn tại
            zipFile.getParentFile().mkdirs();

            // Ghi dữ liệu ZIP vào file
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(zipFile))) {
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = dis.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }

                bos.flush();
            }

            System.out.println("Đã tải file ZIP về: " + zipFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Lỗi khi tải file ZIP: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
