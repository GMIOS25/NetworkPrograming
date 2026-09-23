package com.gpcoder.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Multicast Sender - Gửi gói tin UDP tới địa chỉ IP Multicast (Class D).
 * Tất cả các máy/tiến trình cùng join vào địa chỉ Group này sẽ nhận được bản tin.
 * Nguồn: GP Coder (https://gpcoder.com/3679-xay-dung-ung-dung-client-server-voi-socket-trong-java/)
 */
public class MulticastSender {

    public static final String GROUP_ADDRESS = "224.0.0.1";
    public static final int PORT = 8888;

    public static void main(String[] args) throws InterruptedException {
        int maxMessages = args.length > 0 ? Integer.parseInt(args[0]) : 5; // Mặc định gửi 5 gói rồi dừng hoặc lặp vô tận nếu âm
        DatagramSocket socket = null;
        try {
            InetAddress address = InetAddress.getByName(GROUP_ADDRESS);
            socket = new DatagramSocket();

            System.out.println("[MulticastSender] Bat dau phat tin toi group: " 
                    + GROUP_ADDRESS + ":" + PORT);

            long counter = 1;
            while (true) {
                String msg = "Hello Multicast Group! Ban tin so: " + counter;
                byte[] data = msg.getBytes("UTF-8");
                DatagramPacket outPacket = new DatagramPacket(data, data.length, address, PORT);
                socket.send(outPacket);
                System.out.println("[MulticastSender] Da phat: " + msg);

                if (maxMessages > 0 && counter >= maxMessages) {
                    System.out.println("[MulticastSender] Da gui du " + maxMessages + " ban tin. Ket thuc.");
                    break;
                }

                counter++;
                Thread.sleep(1000); // Nghi 1 giay giua cac lan gui
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
