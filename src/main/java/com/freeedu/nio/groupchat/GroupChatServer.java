package com.freeedu.nio.groupchat;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.lang.annotation.Target;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author LM
 * @create 2020-02-19 14:49
 */
public class GroupChatServer {
    // 定义属性

    // 选择器
    private Selector selector;
    // 服务器端channel
    private ServerSocketChannel listenChannel;
    // 监听端口
    private static final int PORT = 6666;

    // 构造器
    public GroupChatServer(){
        // 初始化工作
       try{
           // 得到serversocketchannel
           listenChannel = ServerSocketChannel.open();
           // 得到选择器
           selector = Selector.open();
           // 绑定端口
           listenChannel.socket().bind(new InetSocketAddress(PORT));
           // 设置非阻塞模式
           listenChannel.configureBlocking(false);
           // serversocketchannel 注册到selector
           listenChannel.register(selector,SelectionKey.OP_ACCEPT);
       } catch (Exception e) {
           e.printStackTrace();
       }

    }

    // 监听
    public void listen(){
        try {
            // 循环处理
            while (true) {
                int count = selector.select();
                if(count > 0) {
                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                    while (keys.hasNext()) {
                        SelectionKey key = keys.next();
                        // OP_ACCEPT 事件
                        if (key.isAcceptable()) {
                            // 获取socketChannel
                            SocketChannel sc = listenChannel.accept();
                            // 设置非阻塞
                            sc.configureBlocking(false);
                            // 注册 设置感兴趣事件是读
                            sc.register(selector,SelectionKey.OP_READ);
                            // 提示
                            System.out.println(sc.getRemoteAddress()+"上线");
                        }
                        // OP_READ 事件
                        if(key.isReadable()) {
                            // 处理读事件
                            read(key);
                        }

                        // 删除当前key  防止重复处理
                        keys.remove();
                    }

                } else {
                    System.out.println("服务器端等待连接...");
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

        }
    }
    // 读取消息
    private void read(SelectionKey key){
        SocketChannel channel = null;
        try {
            // 获取channel
            channel = (SocketChannel) key.channel();
            // 创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 读取到内容
            int  len = channel.read(buffer);

            // 读取到数据
            if (len >0) {
                String str = new String(buffer.array());
                System.out.println(channel.getRemoteAddress()+"说："+str);

                // 转发给其他的客户端  去掉自己
                sendToOtherClients(str,channel);
            } else {
                // 未读取到数据
            }
        } catch (Exception e) {
            // 如果发生异常 说明离线了
            try {
                System.out.println(channel.getRemoteAddress()+"离线了..");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    // 转发消息给其他客户端
    private  void sendToOtherClients(String str, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");
        // 遍历所有注册到selector上的socketchannel 并排除self
        for (SelectionKey key : selector.keys()) {
            Channel tarchannel = key.channel();
            // 排除自己 并且是socketchannel (排除服务器serversocketchannel)
            if (tarchannel instanceof  SocketChannel && tarchannel != self){

                SocketChannel tarSocketChannel = (SocketChannel) tarchannel;
                // 将msg存储到Buffer
                ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
                // 将buffer数据写入到通道
                tarSocketChannel.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        // 创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();

    }
}
