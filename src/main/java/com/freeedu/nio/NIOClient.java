package com.freeedu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author LM
 * @create 2020-02-19 10:05
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        // 得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        System.out.println("客户端通道："+socketChannel.hashCode());
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        
        // 提供服务器端ip  端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        // 连接服务器
        if(!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("客户端连接服务器需要时间，客户端不会阻塞，可以进行其他操作");
            }
        }

        // 如果连接成功需要发送数据
        String str = "您好 L先生";
        // 直接把字节数组放到buffer中
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        // 发送数据 buffer数据写入到channel
        socketChannel.write(buffer);

        // 卡住
        System.in.read();


    }
}
