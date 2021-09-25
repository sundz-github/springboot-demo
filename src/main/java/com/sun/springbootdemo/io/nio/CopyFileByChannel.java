package com.sun.springbootdemo.io.nio;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <p> 文件复制 -->> 采用Channel </p>
 *
 * @author Sundz
 * @date 2021/9/20 12:14
 */
public class CopyFileByChannel {

    @SneakyThrows
    public static void main(String[] args) {
        try (FileInputStream is = new FileInputStream("C:\\Users\\Administrator\\Desktop\\图片\\动画.jpg");
             FileChannel isChannel = is.getChannel();
             FileOutputStream os = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\图片\\copy.jpg");
             FileChannel osChannel = os.getChannel()
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 10);
            isChannel.read(buffer);
            // 开启读模式
            buffer.flip();
            osChannel.write(buffer);
            System.out.println("文件复制完成!");
        }

    }

}
