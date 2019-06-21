package tech.shunzi.demo.server;

import tech.shunzi.demo.constant.WebServerParamConstant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SimpleNIOWebServer {

    public static void main(String args[]) {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.socket().bind(new InetSocketAddress(WebServerParamConstant.HOST_ADDRESS, WebServerParamConstant.PORT));
            ByteBuffer readBuffer = ByteBuffer.allocate(128);
            SocketChannel socketChannel;
            while (true) {
                socketChannel = ssc.accept();
                if (null != socketChannel) {
                    socketChannel.read(readBuffer);
                    break;
                } else {
                    // Do some operation
                    Thread.sleep(5000L);
                    System.out.println("I am waiting!");
                }
            }


            // Convert write to read of byte buffer
            // channel.read() -> buffer.get()
            readBuffer.flip();
            while (readBuffer.hasRemaining()) {
                System.out.println((char) readBuffer.get());
            }
            socketChannel.close();
            ssc.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
