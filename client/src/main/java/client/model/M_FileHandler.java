package client.model;

import java.io.*;
import java.util.List;

public class M_FileHandler {
    private FileUploader fileUploader;
    private FileDownloader fileDownloader;
    private FileLister fileLister;
    private FileDeleter fileDeleter;

    public M_FileHandler(String username) {
        fileUploader = new FileUploader(username);
        fileDownloader = new FileDownloader(username);
        fileLister = new FileLister(username);
        fileDeleter = new FileDeleter(username);
    }

    public void upload(String filePath, DataOutputStream dos) {
        fileUploader.uploadFile(filePath, dos);
    }

    public void download(List<String> fileList, String downloadDirectory, DataInputStream dis, DataOutputStream dos) {
        fileDownloader.downloadFiles(fileList, downloadDirectory, dis, dos);
    }

    public String[] load(DataInputStream dis, DataOutputStream dos) {
        return fileLister.loadFileList(dis, dos);
    }

    public void delete(List<String> fileList, DataInputStream dis, DataOutputStream dos) {
        fileDeleter.deleteFiles(fileList, dis, dos);
    }
}
