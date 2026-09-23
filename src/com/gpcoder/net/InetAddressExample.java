package com.gpcoder.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Minh họa sử dụng java.net.InetAddress để tra cứu IP và tên miền.
 * Nguồn: GP Coder (https://gpcoder.com/3664-lap-trinh-mang-voi-java/)
 */
public class InetAddressExample {

    public static void main(String[] args) throws UnknownHostException {
        // 1. Lay thong tin Host hien tai (Local Host)
        InetAddress localIp = InetAddress.getLocalHost();
        System.out.println("=== LOCAL HOST ===");
        System.out.println("Host Name  : " + localIp.getHostName());
        System.out.println("IP Address : " + localIp.getHostAddress());

        // 2. Tra cuu IP qua ten mien (DNS lookup)
        String domain = "google.com";
        InetAddress ip = InetAddress.getByName(domain);
        System.out.println("\n=== DNS LOOKUP (" + domain + ") ===");
        System.out.println("Host Name  : " + ip.getHostName());
        System.out.println("IP Address : " + ip.getHostAddress());

        // 3. Tra cuu tat ca cac dia chi IP duoc gan cho ten mien
        System.out.println("\n=== ALL IP ADDRESSES FOR (" + domain + ") ===");
        InetAddress[] allIps = InetAddress.getAllByName(domain);
        for (InetAddress item : allIps) {
            System.out.println("- " + item);
        }
    }
}
