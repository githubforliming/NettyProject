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
public class NIOFileChannel03 {
    public static void main(String[] args) {
        {
            FileInputStream fileInputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                // 创建一个输入流->channel
                fileInputStream = new FileInputStream("e:\\test\\1.txt");
                // 通过FileInputStream获取对应的FileChannel(FileChannelImpl)
                FileChannel fileinputChannel = fileInputStream.getChannel();

                // 输出流  channel
                fileOutputStream = new FileOutputStream("e:\\test\\2.txt");
                FileChannel fileoutputChannel = fileOutputStream.getChannel();

                // 创建缓冲区ButeBuffer
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                // 循环读取
                while(true) {
                    // 将通道数据读入到buffer中
                    int count = fileinputChannel.read(byteBuffer);
                    if (count != -1) {
                        byteBuffer.flip();
                        fileoutputChannel.write(byteBuffer);
                        // 复位 position=limit 不能存数据了  下次read时候就会返回 0
                        byteBuffer.clear();
                    } else {
                        break;
                    }
                }

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
