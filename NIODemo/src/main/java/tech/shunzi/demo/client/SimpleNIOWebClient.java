package tech.shunzi.demo.client;

import tech.shunzi.demo.constant.WebServerParamConstant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SimpleNIOWebClient {

    public static void main(String[] args) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(WebServerParamConstant.HOST_ADDRESS, WebServerParamConstant.PORT));
            socketChannel.configureBlocking(false);

            ByteBuffer writeBuffer = ByteBuffer.allocate(128);
            writeBuffer.put("hello world".getBytes());

            // Convert write to read of byte buffer
            // buffer.put() -> channel.write()
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
