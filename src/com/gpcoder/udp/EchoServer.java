package com.gpcoder.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Server UDP không hướng kết nối (Connectionless) - Echo dữ liệu nhận được.
 * Nguồn: GP Coder (https://gpcoder.com/3679-xay-dung-ung-dung-client-server-voi-socket-trong-java/)
 */
public class EchoServer {

    public static final int DEFAULT_SERVER_PORT = 5001;
    public static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_SERVER_PORT;
        DatagramSocket ds = null;
        try {
            System.out.println("[UDPServer] Binding to port " + port + ", please wait...");
            ds = new DatagramSocket(port);
            System.out.println("[UDPServer] UDP Server da khoi dong tren cong " + port);
            System.out.println("[UDPServer] Cho tin nhan tu Client...");

            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                ds.receive(incoming); // Chờ nhận gói tin đến

                String message = new String(incoming.getData(), 0, incoming.getLength(), "UTF-8");
                System.out.println("[UDPServer] Nhan tu " + incoming.getSocketAddress() + ": " + message);

                // Nếu client gửi "QUIT" thì server có thể thoát (tiện cho testing tự động)
                boolean shouldStop = message.equalsIgnoreCase("QUIT");

                // Echo gói tin gửi ngược lại cho client
                byte[] responseBytes = ("ECHO: " + message).getBytes("UTF-8");
                DatagramPacket outsending = new DatagramPacket(
                        responseBytes, responseBytes.length,
                        incoming.getAddress(), incoming.getPort()
                );
                ds.send(outsending);

                if (shouldStop) {
                    System.out.println("[UDPServer] Nhan lenh QUIT. Dang dung UDP Server...");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ds != null && !ds.isClosed()) {
                ds.close();
            }
        }
    }
}
