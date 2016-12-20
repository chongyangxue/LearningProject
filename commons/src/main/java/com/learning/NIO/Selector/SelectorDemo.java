/** 
 * File: SelectorDemo.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.NIO.Selector;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

/**
 * Description: Author: Sachiel Date: 2016-1-4
 */
public class SelectorDemo {

    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    @Test
    public void demo() throws Exception {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverChannel.socket();
        serverSocket.bind(new InetSocketAddress(4700));
        serverChannel.configureBlocking(false);

        Selector selector = Selector.open();

        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) {
                continue;
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();
                    registerChannel(selector, channel, SelectionKey.OP_READ);
                    sayHello(channel);
                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.
                } else if (key.isReadable()) {
                    readDataFromSocket(key);
                } else if (key.isWritable()) {
                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }
    }

    protected void registerChannel(Selector selector, SelectableChannel channel, int ops) throws Exception {
        if (channel == null) {
            return; // could happen  
        }
        channel.configureBlocking(false);
        channel.register(selector, ops);
    }

    protected void readDataFromSocket(SelectionKey key) throws Exception {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;
        buffer.clear(); // Empty buffer  
        // Loop while data is available; channel is nonblocking  
        while ((count = socketChannel.read(buffer)) > 0) {
            buffer.flip(); // Make buffer readable  
            // Send the data; don't assume it goes all at once  
            while (buffer.hasRemaining()) {
                String msg = byteBufferToString(buffer);
                if (msg.equals("quit")) {
                    socketChannel.close();
                } else {
                    socketChannel.write(buffer);
                }
            }
            // WARNING: the above loop is evil. Because  
            // it's writing back to the same nonblocking  
            // channel it read the data from, this code can  
            // potentially spin in a busy loop. In real life  
            // you'd do something more useful than this.  
            buffer.clear(); // Empty buffer  

        }
        if (count < 0) {
            // Close channel on EOF, invalidates the key  
            socketChannel.close();
        }
    }

    private void sayHello(SocketChannel channel) throws Exception {
        buffer.clear();
        buffer.put("Hi there!\r\n".getBytes());
        buffer.flip();
        channel.write(buffer);
    }

    private String byteBufferToString(ByteBuffer buffer) {
        CharBuffer charBuffer = null;
        try {
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            //flip方法会将缓冲区的当前位置”重置”到缓冲区的开始处.这样可以保证传入的对像被其他方法使用时读取的数据完整性.
            buffer.flip(); 
            return charBuffer.toString().trim();
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
