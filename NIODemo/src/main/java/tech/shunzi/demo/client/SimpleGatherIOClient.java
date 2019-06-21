package tech.shunzi.demo.client;

import tech.shunzi.demo.constant.WebServerParamConstant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SimpleGatherIOClient {

    public static void main(String[] args) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(WebServerParamConstant.HOST_ADDRESS, WebServerParamConstant.PORT));

            ByteBuffer writeBufferOne = ByteBuffer.allocate(128);
            ByteBuffer writeBufferTwo = ByteBuffer.allocate(16);
            writeBufferOne.put("hello ".getBytes());
            writeBufferTwo.put("world".getBytes());

            writeBufferOne.flip();
            writeBufferTwo.flip();
            ByteBuffer[] writeBufferArray = {writeBufferOne, writeBufferTwo};
            socketChannel.write(writeBufferArray);
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
