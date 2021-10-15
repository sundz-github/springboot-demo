package com.sun.springbootdemo.io.bio;

import lombok.extern.log4j.Log4j2;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> bio通信服务端  -->  阻塞通信 </p>
 *
 * @author Sundz
 * @date 2021/9/12 11:24
 */
@Log4j2
public class BioServer {

    private static final Map<String, Socket> SOCKETMAPS = new HashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9090);
            log.info("<----------------服务端启动了---------------->");
            while (true) {
                //获取客户端
                Socket client = serverSocket.accept();
                InputStream in;
                DataInputStream is = new DataInputStream(client.getInputStream());
                // 1:登录 2:私信 3:群聊
                int flag = is.readInt();
                log.info("服务端flag:{}", flag);
                if (flag == 1) {
                    String loginName = is.readUTF();
                    log.info("<-------------------【{}】登录成功------------------->", loginName);
                    // 将客户端注入列表里
                    SOCKETMAPS.put(loginName, client);
                }
                new Thread(new ProcessMessageRunnable(client)).start();

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    public static Map<String, Socket> getSockets() {
        return SOCKETMAPS;
    }
}
