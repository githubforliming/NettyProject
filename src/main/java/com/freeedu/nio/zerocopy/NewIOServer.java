package com.freeedu.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author LM
 * @create 2020-02-19 21:48
 * 服务器端
 */
public class NewIOServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(7001);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket serverSocket = serverSocketChannel.socket();

        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);

        while (true) {
            SocketChannel socketChannel =  serverSocketChannel.accept();

            int readCount = 0;
            while (-1 != readCount) {
                readCount = socketChannel.read(byteBuffer);
                byteBuffer.rewind(); // positon=0 mark复原
            }
        }


    }
}
