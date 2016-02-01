/** 
 * File: MulticastSender.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Description: Author: Sachiel Date: 2016-1-28
 */
public class MulticastSender {
    public static void main(String[] args) throws Exception {
        InetAddress group = InetAddress.getByName("224.0.0.4"); // 组播地址  
        int port = 4006; // 端口  
        MulticastSocket mss = null;
        try {
            mss = new MulticastSocket(port); // 1.创建一个用于发送和接收的MulticastSocket组播套接字对象  
            mss.joinGroup(group); // 3.使用组播套接字joinGroup(),将其加入到一个组播  
            while (true) {
                String message = "Hello" + new java.util.Date();
                // 2.创建一个指定缓冲区大小及组播地址和端口的DatagramPacket组播数据包对象  
                byte[] buffer2 = message.getBytes();

                DatagramPacket dp = new DatagramPacket(buffer2, buffer2.length, group, port);
                // msr.receive(dp); //接收组播数据包  
                mss.send(dp); // 4.使用组播套接字的send()方法，将组播数据包对象放入其中，发送组播数据包  
                // String s = new String(dp.getData(), 0, dp.getLength()); //5.解码组播数据包提取信息，并依据得到的信息作出响应  
                System.out.println("发送数据包给" + group + ":" + port);
                Thread.sleep(1000);
            }
        } catch(IOException e) {
            e.printStackTrace();

        } finally {
            if (mss != null) {
                try {
                    mss.leaveGroup(group); // 7.使用组播套接字的leaveGroup()方法，离开组播组  
                    mss.close(); // 关闭组播套接字  
                } catch(IOException e) {
                }
            }
        }
    }
}
