/** 
 * File: Client.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.java_net.SocketDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Description: Author: Sachiel Date: 2016-1-5
 */
public class Client {
    public static void main(String args[]) {
        try {
            Socket socket = new Socket("127.0.0.1", 4700);
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            final BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String line;
                        while(!(line = is.readLine()).equals("bye")){
                            System.out.println("Server:" + line);
                        }
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                
            }).start();
            String readline; //从系统标准输入读入一字符串　　　　　　　　
            while (!(readline = sin.readLine()).equals("bye")) {
                os.println(readline);
                os.flush();
            }
            os.close();
            is.close();
            socket.close();
        } catch(Exception e) {
            System.out.println("Error" + e);
        }
    }
}
