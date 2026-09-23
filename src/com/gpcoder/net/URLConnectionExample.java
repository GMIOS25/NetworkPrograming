package com.gpcoder.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * Minh họa kết nối và đọc nội dung văn bản từ một trang web qua java.net.URLConnection.
 * Nguồn: GP Coder (https://gpcoder.com/3664-lap-trinh-mang-voi-java/)
 */
public class URLConnectionExample {

    public static void main(String[] args) {
        try {
            URL url = URI.create("https://httpbin.org/get").toURL();
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Java NetworkProgramming Demo)");

            System.out.println("Dang ket noi toi: " + url);
            System.out.println("Content-Type: " + urlConnection.getContentType());
            System.out.println("Content-Length: " + urlConnection.getContentLength());

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
                count++;
                if (count > 30) {
                    sb.append("... [da cat bot noi dung] ...");
                    break;
                }
            }
            br.close();

            System.out.println("--- Du lieu doc duoc ---");
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
