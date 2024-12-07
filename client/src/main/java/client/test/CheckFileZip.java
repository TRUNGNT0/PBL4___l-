package client.test;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CheckFileZip {
	public static void main(String[] args) {
		try (ZipFile zipFile = new ZipFile("C:\\Users\\Admin\\Downloads\\My DownLoad\\abc.zip")) {
		    Enumeration<? extends ZipEntry> entries = zipFile.entries();
		    while (entries.hasMoreElements()) {
		        ZipEntry entry = entries.nextElement();
		        System.out.println("File: " + entry.getName());
		    }
		} catch (IOException e) {
		    System.err.println("File ZIP bị lỗi: " + e.getMessage());
		}

	}
}
