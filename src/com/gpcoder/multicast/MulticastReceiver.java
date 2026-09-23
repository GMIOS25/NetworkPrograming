package com.gpcoder.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;

/**
 * Multicast Receiver - Gia nhập nhóm Multicast và lắng nghe các gói tin phát chung.
 * Nguồn: GP Coder (https://gpcoder.com/3679-xay-dung-ung-dung-client-server-voi-socket-trong-java/)
 */
public class MulticastReceiver {

    public static final byte[] BUFFER = new byte[4096];

    public static void main(String[] args) {
        int maxReceive = args.length > 0 ? Integer.parseInt(args[0]) : 3; // Nhan 3 goi roi thoat (hoac nhap am de lap vo tan)
        MulticastSocket socket = null;
        try {
            InetAddress groupAddress = InetAddress.getByName(MulticastSender.GROUP_ADDRESS);

            // Khoi tao MulticastSocket lang nghe tai PORT
            socket = new MulticastSocket(MulticastSender.PORT);

            // Gia nhap Multicast group
            // Tu Java 14+: socket.joinGroup(SocketAddress, NetworkInterface) duoc khuyen nghi thay the socket.joinGroup(InetAddress)
            try {
                SocketAddress mcastaddr = new InetSocketAddress(groupAddress, MulticastSender.PORT);
                NetworkInterface netIf = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
                socket.joinGroup(mcastaddr, netIf);
            } catch (Exception fallback) {
                // Fallback cho phuong thuc truyen thong
                @SuppressWarnings("deprecation")
                InetAddress addr = groupAddress;
                socket.joinGroup(addr);
            }

            System.out.println("[MulticastReceiver] Da gia nhap group " 
                    + MulticastSender.GROUP_ADDRESS + ":" + MulticastSender.PORT);
            System.out.println("[MulticastReceiver] Dang cho nhan du lieu...");

            int count = 0;
            while (true) {
                DatagramPacket inPacket = new DatagramPacket(BUFFER, BUFFER.length);
                socket.receive(inPacket);
                String msg = new String(inPacket.getData(), 0, inPacket.getLength(), "UTF-8");
                System.out.println("[MulticastReceiver] Tu " + inPacket.getSocketAddress() + " -> " + msg);

                count++;
                if (maxReceive > 0 && count >= maxReceive) {
                    System.out.println("[MulticastReceiver] Da nhan du " + maxReceive + " ban tin. Ket thuc.");
                    break;
                }
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
