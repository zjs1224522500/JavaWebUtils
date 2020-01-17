package tech.shunzi.rpc.server.impl;

import tech.shunzi.rpc.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceCenter implements Server {

    /**
     * Create thread pool for task processing.
     */
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Maintain the registry service list.
     */
    private static final HashMap<String, Class> serviceRegistry = new HashMap<>();

    /**
     * Running status
     */
    private static boolean isRunning = false;

    private static int port;

    public ServiceCenter(int port) {
        this.port = port;
    }

    public void stop() {
        isRunning = false;
        executor.shutdown();
    }

    public void start() throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        System.out.println("start server");
        try {
            while (true) {
                // 1.监听客户端的TCP连接，接到TCP连接后将其封装成task，由线程池执行
                // 1. Listen the TCP connection and wrap the connection to ServiceTask.
                System.out.println("Server received the connection and prepare to execute task.");
                executor.execute(new ServiceTask(server.accept()));
            }
        } finally {
            server.close();
        }
    }

    public void register(Class serviceInterface, Class impl) {
        // Use the class name of service as key and implementation of class as value.
        serviceRegistry.put(serviceInterface.getName(), impl);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getPort() {
        return port;
    }

    private static class ServiceTask implements Runnable {
        Socket client = null;

        public ServiceTask(Socket client) {
            this.client = client;
        }

        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;
            try {
                // 2.将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果
                // 2.Deserialize the byte stream to object sent by client.
                input = new ObjectInputStream(client.getInputStream());
                // Use UTF format to read data. server - readUTF/ client - writeUTF
                String serviceName = input.readUTF();
                String methodName = input.readUTF();

                // Read an object from the ObjectInputStream. server - readObject / client - writeObject
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] arguments = (Object[]) input.readObject();
                System.out.println("Server received data: serviceName - " + serviceName + ", methodName - " + methodName + ", arguments - " + Arrays.toString(arguments));

                // Get the implementation class of service
                Class serviceClass = serviceRegistry.get(serviceName);
                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName + " not found");
                }

                // Use reflect get the service method of class.
                Method method = serviceClass.getMethod(methodName, parameterTypes);
                // Get the return result of method invocation
                Object result = method.invoke(serviceClass.newInstance(), arguments);

                System.out.println("Server start to execute the remote method and prepare to return result.");

                // 3.将执行结果序列化，通过socket发送给客户端
                // 3.Serialize the result and send it to client with socket.
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
