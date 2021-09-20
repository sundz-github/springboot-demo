package com.sun.springbootdemo.io.bio;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.io.DataInputStream;
import java.net.Socket;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/9/19 17:35
 */
@Log4j2
@AllArgsConstructor
public class BioClientReader implements Runnable {

    private Socket socket;

    private String userName;

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            DataInputStream is = new DataInputStream(socket.getInputStream());
            int flag = is.readInt();
            if (flag == 2) {
                System.out.println("接受者【" + userName + "】:" + is.readUTF());
            }
        }
    }
}
