package com.sun.springbootdemo.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * <p> 客户端 </p>
 *
 * @author Sundz
 * @date 2021/11/20 18:53
 */
public class NIOClient {

    public static void main(String[] args) throws Exception {
        SocketChannel client = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        client.configureBlocking(false);
        //client.bind(new InetSocketAddress("127.0.0.1", 8080));
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入聊天内容:");
            String content = scanner.nextLine();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(content.getBytes());
            byteBuffer.flip();
            client.write(byteBuffer);
            byteBuffer.clear();
        }
    }
}
