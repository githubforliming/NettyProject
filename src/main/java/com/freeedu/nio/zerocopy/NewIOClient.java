package com.freeedu.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author LM
 * @create 2020-02-19 21:51
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(new InetSocketAddress("127.0.0.1",7001));

        String fileName = "F:\\BaiduNetdiskDownload\\BADOUXUEYUAN\\【04】工具包\\镜像包\\CentOS_6.5_最原始的环境.rar";

        FileChannel channel = new FileInputStream(fileName).getChannel();

        // 准备发送
        long start = System.currentTimeMillis();

        // 在linux下 一个transferTo 函数 就可以完成传输
        // 在windows 下一次调用transferTo 只能发送8M
        // 就需要分段传输文件 ，而且要注意传输时的位置

       long tansferCount =  channel.transferTo(0,channel.size(),socketChannel);

        System.out.println("传递："+tansferCount);

        System.out.println("用时："+(System.currentTimeMillis()-start));


    }
}
