package com.sun.springbootdemo;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/9/11 20:30
 */
public class JdkTest extends BaseJnuit5Test {

    @Test
    @SneakyThrows
    public void inpuStreamTest() {
        String s = "1234@小明";
        System.out.println(s.substring(s.indexOf("@") + 1));

    }

    public void testDataOutputStream() {
        try {
            File file = new File("C:\\Users\\Administrator\\Desktop\\file.txt");
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            out.writeBoolean(true);
            out.writeByte((byte) 0x41);
            out.writeChar((char) 0x4243);
            out.writeShort((short) 0x4445);
            out.writeInt(0x12345678);
            out.writeLong(0x0FEDCBA987654321L);
            out.writeUTF("abcdefghijklmnopqrstuvwxyz严12");
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testDataInputStream() {

        try {
            File file = new File("C:\\Users\\Administrator\\Desktop\\file.txt");
            DataInputStream in =
                    new DataInputStream(
                            new FileInputStream(file));
            System.out.printf("readBoolean():%s\n", in.readBoolean());
            System.out.printf("readByte():%s\n", in.readByte());
            System.out.printf("readChar():%s\n", in.readChar());
            System.out.printf("readShort():%s\n", in.readShort());
            System.out.printf("readInt():%s\n", in.readInt());
            System.out.printf("readLong():%s\n", in.readLong());
            System.out.printf("readUTF():%s\n", in.readUTF());
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bufferTest() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("--------------------------");
        buffer.put("abcd".getBytes());
        buffer.flip();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
    }

}
