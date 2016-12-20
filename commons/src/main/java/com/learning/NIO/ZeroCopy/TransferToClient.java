/** 
 * File: TransferToClient.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.NIO.ZeroCopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * 1.transferTo()方法使得文件内容被copy到了kernel buffer，这一动作由DMA engine完成。
 * 2. 没有data被copy到socket buffer。取而代之的是socket buffer被追加了一些descriptor的信息，
 * 包括data的位置和长度。然后DMA engine直接把data从kernel buffer传输到protocol engine，
 * 这样就消除了唯一的一次需要占用CPU的拷贝操作。
 * 
 */
public class TransferToClient {

    public void testSendfile() throws IOException {
        String host = "localhost";
        int port = 9026;
        SocketAddress sad = new InetSocketAddress(host, port);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(sad);
        socketChannel.configureBlocking(true);

        String fname = "src/main/java/zerocopy/test.data";
        FileChannel fileChannel = new FileInputStream(fname).getChannel();
        long start = System.nanoTime();
        long nsent = 0, curnset = 0;
        curnset = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总字节数:" + curnset + " 耗时(ns):" + (System.nanoTime() - start));
        try {
            socketChannel.close();
            fileChannel.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    
    public static void main(String[] args) throws IOException {
        TransferToClient sfc = new TransferToClient();
        sfc.testSendfile();
    }
}
