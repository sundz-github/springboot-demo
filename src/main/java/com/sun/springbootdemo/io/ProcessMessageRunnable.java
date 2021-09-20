package com.sun.springbootdemo.io;

import com.sun.springbootdemo.io.bio.BioServer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/9/12 20:45
 */
@AllArgsConstructor
@Log4j2
public class ProcessMessageRunnable implements Runnable {

    private Socket socket;


    @Override
    public void run() {
        try {
            log.info("<-------------------开始接受消息------------------->");
            Socket client = null;
            String userName = null;
            while (true) {
                DataInputStream is = new DataInputStream(socket.getInputStream());
                int len;
                int flag = is.readInt();
                log.info("子线程flag:{}", flag);
                if (flag == 2) {
                    String content = is.readUTF();
                    if (StringUtils.isNotBlank(content) && content.contains("@")) {
                        userName = content.substring(content.indexOf("@") + 1);
                    }
                    log.info("获取消息:{}, userName:{}", content, userName);
                    if (StringUtils.isNotBlank(userName)) {
                        // 获取转发客户端  发送消息
                        client = BioServer.getSockets().get(userName);
                        OutputStream out;
                        DataOutputStream os = new DataOutputStream(client.getOutputStream());
                        os.writeInt(flag);
                        if (content.contains("@")) {
                            content = content.substring(0, content.indexOf("@"));
                        }
                        os.writeUTF(content);
                        os.flush();
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static byte[] trimByteArr(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            bytes = new byte[1024];
        }
        byte[] copy = null;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == 0) {
                copy = Arrays.copyOf(bytes, i);
                return copy;
            }
        }
        return copy;
    }
}
