package com.freeedu.nio.zerocopy;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author LM
 * @create 2020-02-19 21:38
 */
public class OldIOClient {
    public static void main(String[] args) throws  Exception {
        Socket socket = new Socket("127.0.0.1",7001);
        String fileName = "C:\\Users\\LM\\Desktop\\test.zip";

        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] byteArrays = new byte[2048];

        long readcount ;

        long total = 0 ;

        long startTime = System.currentTimeMillis();

        while ((readcount =  inputStream.read(byteArrays))>= 0  ) {
            total+=readcount;
            dataOutputStream.write(byteArrays);
        }

        System.out.println("字节数："+total);
        System.out.println("耗时："+(System.currentTimeMillis() - startTime));

        dataOutputStream.close();;
        socket.close();
        inputStream.close();

    }
}
