package com.freeedu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author LM
 * @create 2020-02-19 9:18
 */
public class NIOServer {
    public static void main(String[] args) {
        // 创建serversocketchannel ->相当于IO的serversocket
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            // 绑定端口
            serverSocketChannel.socket().bind(new InetSocketAddress(6666));
            // 设置为非阻塞
            serverSocketChannel.configureBlocking(false);

            // 得到一个Selector
            Selector selector = Selector.open();

            // ServerSocketChannel 注册到Selector  关心的事件是OP_ACCEPT 客户端连接事件
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

            // 等待事件发生
            while (true) {

                // 没有事件发生
                if(selector.select(1000) == 0) {
                    System.out.println("等待1秒 无连接");
                    continue;
                }

                // 有事件发生 获取发生事件的SelectedKey集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();


                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();

                   // 事件类型为OP_ACCEPT  有客户端发起连接  需要创建一个SocketChannel
                    if(next.isAcceptable()) {
                        // 已经有连接等待了 所以accept会马上返回
                        SocketChannel accept = serverSocketChannel.accept();
                        // 设置为非阻塞
                        accept.configureBlocking(false);
                        System.out.println("服务端获取的SocketChannel:"+accept.hashCode());
                        // 将socketchannel 注册到selector 关注事件为OP_READ
                        // 关联一个buffer 这个是服务器端用的buffer
                        accept.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                    }

                    // 读事件
                    if (next.isReadable()) {
                        // 返回channle
                        SocketChannel channel = (SocketChannel)next.channel();

                        // 获取到该channel关联的buffer
                        ByteBuffer buffer = (ByteBuffer)next.attachment();

                        // 进行读取
                        channel.read(buffer);

                        System.out.println("收到客户端消息："+new String(buffer.array()));

                    }

                    // 手动从集合中移除当前的selectionkey 防止重复操作
                    iterator.remove();// iterator 直接删除元素


                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
