package tech.shunzi.demo.server;

import tech.shunzi.demo.constant.WebServerParamConstant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SimpleScatterIOServer {
    public static void main(String args[]) {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(WebServerParamConstant.HOST_ADDRESS, WebServerParamConstant.PORT));
            SocketChannel socketChannel = ssc.accept();

            ByteBuffer readBufferOne = ByteBuffer.allocate(128);
            ByteBuffer readBufferTwo = ByteBuffer.allocateDirect(16);
            ByteBuffer[] readByteBufferArray = {readBufferOne, readBufferTwo};
            socketChannel.read(readByteBufferArray);

            // Convert write to read of byte buffer
            // channel.read() -> buffer.get()
            readBufferOne.flip();
            readBufferTwo.flip();
            while (readBufferOne.hasRemaining()) {
                System.out.println((char) readBufferOne.get());
            }
            while (readBufferTwo.hasRemaining()) {
                System.out.println((char) readBufferTwo.get());
            }

            socketChannel.close();
            ssc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
