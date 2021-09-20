package com.sun.springbootdemo.io.bio;

import lombok.extern.log4j.Log4j2;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * <p> bio通信客户端  -->  阻塞通信 </p>
 *
 * @author Sundz
 * @date 2021/9/12 11:25
 */
@Log4j2
public class BioClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 9090);
            File file;
            byte[] bytes = new byte[1024];
            OutputStream out;
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            int flag = 1;
            os.writeInt(flag);
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入登录用户名:");
            String userName = scanner.nextLine();
            os.writeUTF(userName);
            // 启动客户端接受消息线程
            new Thread(new BioClientReader(socket, userName)).start();
            while (true) {
                // 获取键盘输入
                System.out.print(userName + "说:");
                flag = 2;
                os.writeInt(flag);
                String content = scanner.nextLine();
                os.writeUTF(content);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
