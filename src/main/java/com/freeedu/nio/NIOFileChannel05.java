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
public class NIOFileChannel05 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        // 放入int
        buffer.putInt(10);
        buffer.putLong(12L);
        buffer.putChar('我');
        // 读取
        buffer.flip();
        // 下边三个输出换位置 其实不会报错（因为字节总数不变 只是。。 输出结果可能比较糟糕 ）
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        // 抛出异常 BufferUnderflowException 其实就是没有数据了 你还去取  尴尬不
        System.out.println(buffer.getChar());

    }
}
