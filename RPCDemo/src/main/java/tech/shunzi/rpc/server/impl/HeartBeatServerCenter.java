package tech.shunzi.rpc.server.impl;

import tech.shunzi.rpc.listener.HeartbeatListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class HeartBeatServerCenter {
    private ExecutorService executor = Executors.newFixedThreadPool(20);

    private final ConcurrentHashMap<String, Class> serviceRegistry = new ConcurrentHashMap<String, Class>();

    private AtomicBoolean isRunning = new AtomicBoolean(true);

    // 服务器监听端口
    private int port = 8089;

    // 心跳监听器
    HeartbeatListener listener;

    // 单例模式
    private static class SingleHolder {
        private static final HeartBeatServerCenter INSTANCE = new HeartBeatServerCenter();
    }

    private HeartBeatServerCenter() {
    }

    public static HeartBeatServerCenter getInstance() {
        return SingleHolder.INSTANCE;
    }

    public void register(Class serviceInterface, Class impl) {
        System.out.println("register service " + serviceInterface.getName());
        serviceRegistry.put(serviceInterface.getName(), impl);
    }

    public void start() throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        System.out.println("start server");
        // Compared with ordinary sever center, it starts a listener for heartbeat.
        listener = HeartbeatListener.getInstance();
        System.out.println("start to listen heart beat");
        try {
            while (true) {
                // 1.监听客户端的TCP连接，接到TCP连接后将其封装成task，由线程池执行
                executor.execute(new ServiceTask(server.accept()));
            }
        } finally {
            server.close();
        }
    }

    public void stop() {
        isRunning.set(false);
        executor.shutdown();
    }


    public boolean isRunning() {
        return isRunning.get();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ConcurrentHashMap<String, Class> getServiceRegistry() {
        return serviceRegistry;
    }

    private class ServiceTask implements Runnable {
        Socket client = null;

        public ServiceTask(Socket client) {
            this.client = client;
        }

        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;
            try {
                // 2.将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果
                input = new ObjectInputStream(client.getInputStream());
                String serviceName = input.readUTF();
                String methodName = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] arguments = (Object[]) input.readObject();
                Class serviceClass = serviceRegistry.get(serviceName);
                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName + " not found");
                }
                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), arguments);

                // 3.将执行结果反序列化，通过socket发送给客户端
                output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(result);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}