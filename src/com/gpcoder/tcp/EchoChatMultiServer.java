package com.gpcoder.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server TCP song song (Multi-threaded) - sử dụng ExecutorService (ThreadPool) để phục vụ đồng thời nhiều client.
 * Nguồn: GP Coder (https://gpcoder.com/3679-xay-dung-ung-dung-client-server-voi-socket-trong-java/)
 */
public class EchoChatMultiServer {

    public static final int NUM_OF_THREAD = 4;
    public static final int DEFAULT_SERVER_PORT = 5000;

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_SERVER_PORT;
        ExecutorService executor = Executors.newFixedThreadPool(NUM_OF_THREAD);
        ServerSocket serverSocket = null;
        try {
            System.out.println("[MultiServer] Binding to port " + port + ", please wait...");
            serverSocket = new ServerSocket(port);
            System.out.println("[MultiServer] Server started with ThreadPool size " + NUM_OF_THREAD + ": " + serverSocket);
            System.out.println("[MultiServer] Waiting for clients (song song)...");

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("[MultiServer] Client accepted: " + socket);

                    WorkerThread handler = new WorkerThread(socket);
                    executor.execute(handler);
                } catch (IOException e) {
                    System.err.println("[MultiServer] Connection Error: " + e);
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
            executor.shutdown();
        }
    }
}
