package com.freeedu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author LM
 * @create 2020-02-18 22:08
 *
 */
public class NIOScattingAndGathering {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        // 绑定端口到socket 并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建buffer数组
        ByteBuffer[] byteBuffer = new ByteBuffer[2];
        byteBuffer[0] = ByteBuffer.allocate(5);
        byteBuffer[1] = ByteBuffer.allocate(3);

        // 等待客户端连接 telnet ->  ctrl+] -> send xxx
        SocketChannel socketChannel = serverSocketChannel.accept();

        //假定最多接收8个 为了测试 5+3
        int len = 8;
        // 循环读取
        while (true) {
            int read = 0;
            //
            while (read < len ) {
                long count = socketChannel.read(byteBuffer);
                read +=count;
                System.out.println("read=" +read);
                // 使用流打印 看当前buffer position和 limit
                Arrays.asList(byteBuffer).
                        stream().
                        map(buffer ->"position="+buffer.position()+",limit="+buffer.limit())
                        .forEach(System.out::println);

                // 将所有buffer 进行flip
                Arrays.asList(byteBuffer).forEach(buffer->buffer.flip());

                // 将数据显示到客户端
                long byteW = 0;
                while (byteW < len) {
                    long  countw = socketChannel.write(byteBuffer);
                    byteW += countw;
                }

                // 将所有buffer 进行复位
                Arrays.asList(byteBuffer).forEach(buffer->buffer.clear());

                System.out.println("回写完了");
            }
        }

    }
}
