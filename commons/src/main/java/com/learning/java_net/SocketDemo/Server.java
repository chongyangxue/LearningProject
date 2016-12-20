/** 
 * File: Server.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.java_net.SocketDemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 这个Client/Server程序只能实现Server和一个客户的对话。
 * 在实际应用 中，往往是在服务器上运行一个永久的程序，它可以接收来自其他多个客户端的请求，提供相应的服务。
 * 为了实现在服务器方给多个客户提供服务的功能，需要对上 面的程序进行改造，利用多线程实现多客户机制。
 * 服务器总是在指定的端口上监听是否有客户请求，一旦监听到客户请求，服务器就会启动一个专门的服务线程来响 应该客户的请求，
 * 而服务器本身在启动完线程之后马上又进入监听状态，等待下一个客户的到来。
 * 
 * 非阻塞的IO参见NIO部分的Selector
 * 
 * Description: 
 * Author: Sachiel 
 * Date: 2016-1-5
 */
public class Server {

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(4700);
            Socket socket = null;
            socket = server.accept();
            //使用accept()阻塞等待客户请求，有客户
            //请求到来则产生一个Socket对象，并继续执行

            
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Client:" + is.readLine());
            String line;
            while (!(line = sin.readLine()).equals("bye")) {
                os.println(line);
                os.flush();
                System.out.println("Server:" + line);
                System.out.println("Client:" + is.readLine());
                line = sin.readLine();
            }
            os.close();
            is.close();
            socket.close();
            server.close();
        } catch(Exception e) {
            System.out.println("Error." + e);
        }
    }
}
