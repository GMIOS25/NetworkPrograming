package com.gpcoder.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server TCP tuần tự (Single-threaded) - chỉ phục vụ 1 client tại 1 thời điểm.
 * Nguồn: GP Coder (https://gpcoder.com/3679-xay-dung-ung-dung-client-server-voi-socket-trong-java/)
 */
public class EchoChatSingleServer {

    public static final int DEFAULT_SERVER_PORT = 5000;

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_SERVER_PORT;
        ServerSocket serverSocket = null;
        try {
            System.out.println("[SingleServer] Binding to port " + port + ", please wait...");
            serverSocket = new ServerSocket(port);
            System.out.println("[SingleServer] Server started: " + serverSocket);
            System.out.println("[SingleServer] Waiting for clients (tuan tu)...");

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("[SingleServer] Client accepted: " + socket);

                    OutputStream os = socket.getOutputStream();
                    InputStream is = socket.getInputStream();
                    int ch;
                    while ((ch = is.read()) != -1) {
                        os.write(ch); // Echo du lieu nguoc ve client
                        os.flush();
                    }
                    socket.close();
                    System.out.println("[SingleServer] Client closed: " + socket);
                } catch (IOException e) {
                    System.err.println("[SingleServer] Connection Error: " + e);
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}
