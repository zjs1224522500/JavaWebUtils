package tech.shunzi.rpc.client.test;

import tech.shunzi.rpc.client.HeartbeatClient;
import tech.shunzi.rpc.handler.HeartbeatHandler;
import tech.shunzi.rpc.handler.impl.HeartbeatHandlerImpl;
import tech.shunzi.rpc.server.impl.HeartBeatServerCenter;

import java.io.IOException;

public class HeartbeatTest {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    HeartBeatServerCenter serviceServer = HeartBeatServerCenter.getInstance();
                    serviceServer.register(HeartbeatHandler.class, HeartbeatHandlerImpl.class);
                    serviceServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread client1 = new Thread(new HeartbeatClient());
        client1.start();
        Thread client2 = new Thread(new HeartbeatClient());
        client2.start();
    }
}
