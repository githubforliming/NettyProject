package com.freeedu.nio;

import java.nio.ByteBuffer;

/**
 * @author LM
 * @create 2020-02-18 21:42
 */
public class ReadOnluBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        // 放入一个char
        buffer.putChar('我');
        // 设置为只读
        ByteBuffer bufferReadOnley = buffer.asReadOnlyBuffer();
        System.out.println(bufferReadOnley.getClass());
        // 读取
        System.out.println(bufferReadOnley.getChar());
        // 放入char
        bufferReadOnley.putChar('你');
    }
}
