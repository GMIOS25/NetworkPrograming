package com.gpcoder.udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Client UDP gửi gói tin DatagramPacket và nhận phản hồi từ Echo Server.
 * Nguồn: GP Coder (https://gpcoder.com/3679-xay-dung-ung-dung-client-server-voi-socket-trong-java/)
 */
public class EchoClient {

    public static final String DEFAULT_SERVER_IP = "127.0.0.1";
    public static final int DEFAULT_SERVER_PORT = 5001;
    public static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) {
        String serverIp = args.length > 0 ? args[0] : DEFAULT_SERVER_IP;
        int serverPort = args.length > 1 ? Integer.parseInt(args[1]) : DEFAULT_SERVER_PORT;
        String messageToSend = args.length > 2 ? args[2] : null;

        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            ds.setSoTimeout(5000); // 5s timeout khi cho goi tin ve
            System.out.println("[UDPClient] Client started...");
            InetAddress server = InetAddress.getByName(serverIp);

            // Neu co truyen message qua tham so dong lenh
            if (messageToSend != null) {
                sendMessage(ds, server, serverPort, messageToSend);
            } else {
                // Nhap tu ban phim
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    System.out.print("[UDPClient] Nhap tin nhan (go 'exit' de thoat): ");
                    String line = br.readLine();
                    if (line == null || line.equalsIgnoreCase("exit")) {
                        break;
                    }
                    sendMessage(ds, server, serverPort, line);
                }
            }
        } catch (IOException e) {
            System.err.println("[UDPClient] Loi: " + e.getMessage());
        } finally {
            if (ds != null && !ds.isClosed()) {
                ds.close();
            }
        }
    }

    private static void sendMessage(DatagramSocket ds, InetAddress server, int port, String msg) throws IOException {
        byte[] data = msg.getBytes("UTF-8");
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, server, port);
        ds.send(sendPacket);
        System.out.println("[UDPClient] Da gui toi " + server.getHostAddress() + ":" + port + " -> " + msg);

        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        ds.receive(receivePacket);
        String reply = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
        System.out.println("[UDPClient] Nhan phan hoi tu server -> " + reply);
    }
}
