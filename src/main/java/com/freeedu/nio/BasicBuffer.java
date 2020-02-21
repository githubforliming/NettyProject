package com.freeedu.nio;

import java.nio.IntBuffer;

/**
 * @author LM
 * @create 2020-02-18 16:06
 */
public class BasicBuffer {
    public static void main(String[] args) {
        // 创建buffer 大小为5 可以存放5个INT
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向buffer中存放数据
//        intBuffer.put(11);
//        intBuffer.put(12);
//        intBuffer.put(13);
//        intBuffer.put(14);
//        intBuffer.put(15);
        for (int i = 0; i < intBuffer.capacity() ; i++) {
            intBuffer.put(i*10);
        }

        // 将buffer转换 读写切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

        
    }
}
