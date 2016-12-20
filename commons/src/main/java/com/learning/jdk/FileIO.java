package com.learning.jdk;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileIO extends Thread {

	public void moveFile(String sourceFolder, String destFolder, int waitTime) {

		File sourceDir = new File(sourceFolder);
		List<String> files = Arrays.asList(sourceDir.list());
		try {
			for(String file : files) {
				File sourceFile = new File(sourceFolder + "/" + file);
				File destFile = new File(destFolder + "/" + file);
				sourceFile.renameTo(destFile);
				System.out.println(file + "   moved to " + destFolder);
				Thread.sleep(waitTime);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		FileIO io = new FileIO();
		long startTime = System.currentTimeMillis();
		io.moveFile("f:/destination", "f:/source", 500);
//		io.moveFile("f:/source", "f:/destination", 500);
		long endTime = System.currentTimeMillis();
		System.out.println("操作持续时间： " + (endTime - startTime));
	}
}
