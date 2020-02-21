package com.freeedu.nio.groupchat;




import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author LM
 * @create 2020-02-19 15:29
 */
public class GroupChatClient {
    //定义属性
    private final String HOST = "127.0.0.1";
    private final int PORT = 6666;
    private Selector selector;
    private SocketChannel socketChannel;

    private String UserName;

    public GroupChatClient() throws IOException {
        // 初始化工作
        selector = Selector.open();
        // 连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
        socketChannel.configureBlocking(false);
        //socketChannel.connect(new InetSocketAddress(HOST,PORT));

        // 将channel 注册到selector
        socketChannel.register(selector,SelectionKey.OP_READ);

        // 得到UserName
        UserName = socketChannel.getLocalAddress().toString().substring(1);

        System.out.println(UserName+"初始化完成..");
    }

    // 向服务器发送消息
    public void sendInfo(String info){
        info = UserName+" 说："+info;
        try {
            socketChannel.write(java.nio.ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo(){
        try {
            int len = selector.select();
            // 有可用通道
            if (len >0) {
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    // OP_READ
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = java.nio.ByteBuffer.allocate(1024);
                        // 读取
                        channel.read(buffer);

                        // 输出消息
                        System.out.println(new String(buffer.array()));
                    }
                    keys.remove();
                }

            } else {
                System.out.println("客户端 没有可用的通道 ...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // 启动客户端
        GroupChatClient client = new GroupChatClient();

        // 启动线程
        new Thread(()->{
            while (true) {
                // 不停读取
                client.readInfo();
                //
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            client.sendInfo(s);
        }
    }
}
