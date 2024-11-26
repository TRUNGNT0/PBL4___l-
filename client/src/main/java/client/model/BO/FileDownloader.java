package client.model.BO;

import java.io.*;
import java.util.List;

public class FileDownloader {
	private String downLoadDirectoryPath;
	
    public FileDownloader() {
        String path = this.getDownLoadDirectoryPathFromFile();
        downLoadDirectoryPath = path;
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
    }

    public void downloadFiles(String currentDirectoryPath, List<String> fileList, String downloadDirectory, DataInputStream dis, DataOutputStream dos) {
    	if(downloadDirectory.equals("OKE")) {
    		downloadDirectory = downLoadDirectoryPath;
    	}
        try {
            dos.writeUTF(currentDirectoryPath); // Gửi đường dẫn hiện tại
            sendFileNameListToServer(fileList, dos);

            for (String fileName : fileList) {
                long fileSize = dis.readLong(); // Nhận kích thước file
                File file = new File(downloadDirectory, fileName);

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
                    try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                System.out.println("Đã tải file: " + fileName + " về " + downloadDirectory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendFileNameListToServer(List<String> fileList, DataOutputStream dos) {
        try {
            dos.writeInt(fileList.size());
            for (String fileName : fileList) {
                dos.writeUTF(fileName);
            }
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
