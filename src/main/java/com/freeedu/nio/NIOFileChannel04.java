package com.freeedu.nio;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author LM
 * @create 2020-02-18 20:34
 */
public class NIOFileChannel04 {
    public static void main(String[] args) {
        {
            FileInputStream fileInputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                // 创建一个输入流FileInputStream->channel
                fileInputStream = new FileInputStream("e:\\test\\1.png");
                // 通过FileInputStream获取对应的FileChannel(FileChannelImpl)
                FileChannel fileinputChannel = fileInputStream.getChannel();

                // 输出流  channel
                fileOutputStream = new FileOutputStream("e:\\test\\2.png");
                FileChannel fileoutputChannel = fileOutputStream.getChannel();

                // transferFrom 实现拷贝
                fileoutputChannel.transferFrom(fileinputChannel,0,fileinputChannel.size());


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
}
