package com.freeedu.nio.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author LM
 * @create 2020-02-19 21:35
 */
public class OldIOServer {
    public static void main(String[] args) throws  Exception {
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] byteArray = new byte[2048];
            while (true) {
                int read = dataInputStream.read(byteArray,0,byteArray.length);
                if (read == -1) {
                    break;
                }
            }
        }
    }
}
