package com.freeedu.bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author LM
 * @create 2020-02-18 8:55
 */
public class BIOServer {
    public static void main(String[] args) {
        //1. 创建一个线程池
        //2. 如果有客户端连接，就创建一个线程，与之通讯（单独写一个方法）

        // 创建线程池
        ExecutorService pool = Executors.newCachedThreadPool();

        // 创建ServerSocket
        ServerSocket serverSocket= null;
        try {
            serverSocket = new ServerSocket(6666);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("服务器启动了！");

        while (true) {
            // 监听，等待客户端连接
            try {
                System.out.println("等待客户端连接...");
                final Socket socket = serverSocket.accept();
                System.out.println("连接到一个客户端");
                // 创建一个线程 与之通讯
                pool.execute(()->{
                    // 与客户端通信
                    handler(socket);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 编写一个Hnadler 方法 与客户端通信
    public static void handler(Socket socket){
        try {
            //System.out.println("线程信息 id="+Thread.currentThread().getId());
            byte[] bytes = new byte[1024];
            // 获取输入流
            InputStream inputStream = socket.getInputStream();
            // 循环读取客户端数据
            while(true) {
                //System.out.println("线程信息 id="+Thread.currentThread().getId());
                System.out.println("等待客户端发送消息...");
                int  read = inputStream.read(bytes);
                if (read != -1) {
                    // 输出客户端发送的数据
                    System.out.println(new String(bytes,0,read));
                } else {
                    // 退出
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
