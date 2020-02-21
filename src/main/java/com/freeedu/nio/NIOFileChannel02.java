package com.freeedu.nio;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author LM
 * @create 2020-02-18 20:34
 */
public class NIOFileChannel02 {
    public static void main(String[] args) {
        {
            FileInputStream fileInputStream = null;
            try {
                // 创建一个输入流->channel
                fileInputStream = new FileInputStream("e:\\test\\1.txt");
                // 通过FileInputStream获取对应的FileChannel(FileChannelImpl)
                FileChannel fileChannel = fileInputStream.getChannel();
                System.out.println(fileChannel.size());
                // 创建缓冲区ButeBuffer
                ByteBuffer byteBuffer = ByteBuffer.allocate((int)fileChannel.size());
                // 将通道数据读入到buffer中
                fileChannel.read(byteBuffer);
                //byteBuffer 进行反转
                byteBuffer.flip();
                System.out.println(new java.lang.String(byteBuffer.array()));
                //
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 关闭流
                if(fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }
}
