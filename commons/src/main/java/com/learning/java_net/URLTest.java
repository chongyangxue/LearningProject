package com.learning.java_net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class URLTest {
	public static void main(String[] args){
		try{
			URL url = new URL("http://www.yahoo.com");
			InputStream is = url.openStream();
			Reader reader = new InputStreamReader(is);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String result = null;
			while((result = bufferedReader.readLine()) != null){
				System.out.println(result);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
