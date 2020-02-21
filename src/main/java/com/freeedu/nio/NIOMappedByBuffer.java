package com.freeedu.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author LM
 * @create 2020-02-18 21:49
 * MappedByteBuffer 可以让文件在内存(堆外内存)中修改 操作系统不需要拷贝一次
 */
public class NIOMappedByBuffer {
    public static void main(String[] args) {
        RandomAccessFile randomAccessFile = null;
        try{
            randomAccessFile = new RandomAccessFile("e:\\test\\1.txt","rw");
            FileChannel channel = randomAccessFile.getChannel();
            /**
             * 参数1：读写模式，
             * 参数2：可以直接修改的起始位置 ，
             * 参数3：映射到内存的大小 既将1.txt的多少个字节映射到内存 可以修改的范围就是0-5（不包含5）
             * DirectByteBuffer
             */
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

            map.put(0,(byte)'H');
            map.put(3,(byte)'A');

            System.out.println("修改成功！");

            // 原始 123456789987654321
            // 修改 H23A56789987654321


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
