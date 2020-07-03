package com.learning.Logs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tailer {
	public static void main(String[] args){
		String path = "/opt/logs/admin/service.log";
		new Tailer().printLogs(path, "");
	}
	
	public void printLogs(final String path, final String grep){
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(new Runnable(){

			@Override
			public void run() {
				tail_f(path, grep);
			}}
		);
	}
	
	/**
	 * RandomAccessFile提供对文件的随机读写功能，与普通的输入输出流不一样的是RamdomAccessFile可以任意的访问文件的任何地方。
	 * 这就是Random的意义所在。
	 * @param path
	 * @param grep
	 */
	private void tail_f(String path, String grep){
		if(grep == null)
			grep = "";
		try {
			RandomAccessFile raf = new RandomAccessFile(new File(path), "r");
			while(true) {
				Long fileLength = raf.length();
				Long pointer = raf.getFilePointer();
				if (fileLength < pointer) {
                    raf = new RandomAccessFile(path, "r");  
                    pointer = (long)0;
                }
				raf.seek(pointer);
				String line;
				while ((line = raf.readLine()) != null) {
					if (!line.contains(grep) || line.length() == 0) {
						continue;
					}
					System.out.println(line);
				}
				Thread.sleep(1000);
			}
			//raf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void tail_f2(String path, String grep){
		if(grep == null)
			grep = "";
		try {
			File file = new File(path);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while(true) {
				String line;
				while ((line = reader.readLine()) != null) {
					if (!line.contains(grep) || line.length() == 0) {
						continue;
					}
					System.out.println(line);
				}
				Thread.sleep(1000);
			}
			//raf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
