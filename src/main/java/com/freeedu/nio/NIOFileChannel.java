package com.freeedu.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author LM
 * @create 2020-02-18 20:10
 */
public class NIOFileChannel {
    public static void main(String[] args) {
        String str = "输出，你好，L先生";
        FileOutputStream fileOutputStream = null;
        try {
            // 创建一个输出流->channel
            fileOutputStream = new FileOutputStream("e:\\test\\1.txt");
            // 通过fileOutputStream获取对应的FileChannel(FileChannelImpl)
            FileChannel fileChannel = fileOutputStream.getChannel();
            // 创建缓冲区ButeBuffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // 将String 放入到butebuffer中
            byteBuffer.put(str.getBytes());
            //byteBuffer 进行反转
            byteBuffer.flip();
            // 将 bytebuffer 数据写入到channel（fileChannel）
            fileChannel.write(byteBuffer);
            //
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
