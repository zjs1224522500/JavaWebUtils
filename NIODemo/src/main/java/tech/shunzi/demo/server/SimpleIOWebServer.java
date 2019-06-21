package tech.shunzi.demo.server;

import tech.shunzi.demo.constant.WebServerParamConstant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SimpleIOWebServer {

    public static void main(String args[]) {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(WebServerParamConstant.HOST_ADDRESS, WebServerParamConstant.PORT));
            SocketChannel socketChannel = ssc.accept();

            ByteBuffer readBuffer = ByteBuffer.allocate(128);
            socketChannel.read(readBuffer);

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
        }
    }
}
