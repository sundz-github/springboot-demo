package com.sun.springbootdemo.io.nio;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/10/10 21:00
 */
@Log4j2
public class NioServer {

    public static void main(String[] args) {
        try {
            // 1、开启服务端
            ServerSocketChannel sChannel = ServerSocketChannel.open();
            // 2、绑定端口
            sChannel.bind(new InetSocketAddress(8080));
            // 3、配置非阻塞
            sChannel.configureBlocking(false);
            // 4、开启轮询器
            Selector selector = Selector.open();
            // 5、服务端注册到轮询器
            sChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (selector.select() > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                if (CollectionUtils.isEmpty(selectionKeys)) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                // 6、获取事件
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (Objects.isNull(selectionKey)) {
                        continue;
                    }
                    // 客户端接入请求 注册客户端
                    if (selectionKey.isAcceptable()) {
                        SocketChannel cChannel = sChannel.accept();
                        cChannel.configureBlocking(false);
                        cChannel.register(selector, SelectionKey.OP_READ);
                    }
                    // 获取客户端消息
                    if (selectionKey.isReadable()) {
                        SocketChannel cChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int count = cChannel.read(buffer);
                        if (count > 0) {
                            buffer.flip();
                            System.out.println("服务端接受客户端消息:" + new String(buffer.array(), 0, buffer.remaining(), StandardCharsets.UTF_8));
                        }
                    }
                    // 6、移除事件
                    iterator.remove();
                }
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
