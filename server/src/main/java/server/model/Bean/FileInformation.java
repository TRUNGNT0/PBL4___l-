package server.model.Bean;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileInformation {
	private String name;
	private long lastModified;	//millisecond
	private long size;		//Byte
	private boolean isFile;
	
	public FileInformation() {
		this.name = "";
		this.lastModified = 0;
		this.size = 0;
		this.isFile = false;
	}
	
	public FileInformation(String name, long lastModified, long size, boolean isFile) {
		this.name = name;
		this.lastModified = lastModified;
		this.size = size;
		this.isFile = isFile;
	}
	
	public void sendFileInformation(DataOutputStream dos) {
		try {
			dos.writeUTF(name);
			dos.writeLong(lastModified);
			dos.writeLong(size);
			dos.writeBoolean(isFile);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void receiveFileInformation(DataInputStream dis) {
		try {
			this.name = dis.readUTF();
			this.lastModified = dis.readLong();
			this.size = dis.readLong();
			this.isFile = dis.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "FileInformation [name=" + name + ", lastModified=" + lastModified + ", size=" + size + ", isFile="
				+ isFile + "]";
	}

	public boolean isFile() {
		return isFile;
	}
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getLastModified() {
		return lastModified;
	}
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
}
