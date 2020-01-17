package tech.shunzi.rpc.client.test;

import tech.shunzi.rpc.client.RPCClient;
import tech.shunzi.rpc.server.Server;
import tech.shunzi.rpc.server.impl.ServiceCenter;
import tech.shunzi.rpc.service.HelloService;
import tech.shunzi.rpc.service.impl.HelloServiceImpl;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RPCTest {
    public static void main(String[] args) throws IOException {
        // Run server.
        new Thread(new Runnable() {
            public void run() {
                try {
                    // Create the server center with port.
                    Server serviceServer = new ServiceCenter(8088);
                    // Register specific service to center.
                    serviceServer.register(HelloService.class, HelloServiceImpl.class);
                    // Run server.
                    serviceServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Get remote service and method.
        HelloService service = RPCClient.getRemoteProxyObj(HelloService.class, new InetSocketAddress("localhost", 8088));
        // Print the result.
        System.out.println(service.sayHi("test"));
    }
}
