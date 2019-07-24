/**
 * File: MulticastReceiver.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Description: Author: Sachiel Date: 2016-1-28
 */
public class MulticastReceiver {
    public static void main(String[] args) throws Exception {
        InetAddress group = InetAddress.getByName("224.0.0.4"); // 组播地址  
        int port = 4006; // 端口  
        MulticastSocket msr = null;
        try {
            // 1.创建一个用于发送和接收的MulticastSocket组播套接字对象
            msr = new MulticastSocket(port);
            // 2.使用组播套接字joinGroup(),将其加入到一个组播
            msr.joinGroup(group);
            byte[] buffer = new byte[8192];
            System.out.println("接收数据包启动！（启动时间：）" + new java.util.Date() + ")");
            while (true) {
                // 3.创建一个指定缓冲区大小及组播地址和端口的DatagramPacket组播数据包对象  
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                // 4.使用组播套接字的receive（）方法，将组播数据包对象放入其中，接收组播数据包 
                msr.receive(dp);
                // 5.解码组播数据包提取信息，并依据得到的信息作出响应  
                String s = new String(dp.getData(), 0, dp.getLength());
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (msr != null) {
                try {
                    // 7.使用组播套接字的leaveGroup()方法，离开组播组
                    msr.leaveGroup(group);
                    msr.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
