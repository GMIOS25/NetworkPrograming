package com.gpcoder.net;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Minh họa sử dụng java.net.URL để phân tích cấu trúc một địa chỉ URL.
 * Nguồn: GP Coder (https://gpcoder.com/3664-lap-trinh-mang-voi-java/)
 */
public class UrlExample {

    public static void main(String[] args) {
        try {
            // Trong Java 20+, khuyến nghị sử dụng URI.create(...).toURL() thay vì new URL(...)
            String spec = "https://www.gpcoder.com:80/java/index.html?page=1&order=desc#java-core";
            URL url = URI.create(spec).toURL();

            System.out.println("URL          : " + url.toString());
            System.out.println("Protocol     : " + url.getProtocol());
            System.out.println("Authority    : " + url.getAuthority());
            System.out.println("File name    : " + url.getFile());
            System.out.println("Host         : " + url.getHost());
            System.out.println("Path         : " + url.getPath());
            System.out.println("Port         : " + url.getPort());
            System.out.println("Default port : " + url.getDefaultPort());
            System.out.println("Query        : " + url.getQuery());
            System.out.println("Ref (anchor) : " + url.getRef());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
