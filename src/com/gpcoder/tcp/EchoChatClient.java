package com.gpcoder.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Client TCP gửi tuần tự các ký tự và nhận dữ liệu phản hồi từ Echo Server.
 * Nguồn: GP Coder (https://gpcoder.com/3679-xay-dung-ung-dung-client-server-voi-socket-trong-java/)
 */
public class EchoChatClient {

    public static final String DEFAULT_SERVER_IP = "127.0.0.1";
    public static final int DEFAULT_SERVER_PORT = 5000;

    public static void main(String[] args) throws IOException, InterruptedException {
        String serverIp = args.length > 0 ? args[0] : DEFAULT_SERVER_IP;
        int serverPort = args.length > 1 ? Integer.parseInt(args[1]) : DEFAULT_SERVER_PORT;

        Socket socket = null;
        try {
            System.out.println("[Client] Dang ket noi toi " + serverIp + ":" + serverPort + "...");
            socket = new Socket(serverIp, serverPort);
            System.out.println("[Client] Ket noi thanh cong: " + socket);

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            System.out.print("[Client] Gui du lieu ('0'..'9'): ");
            for (int i = '0'; i <= '9'; i++) {
                os.write(i); // Gui byte sang server
                os.flush();
                int ch = is.read(); // Cho nhan ket qua phan hoi (echo)
                System.out.print((char) ch + " ");
                Thread.sleep(100);
            }
            System.out.println("\n[Client] Hoan tat truyen nhan.");
        } catch (IOException ie) {
            System.err.println("[Client] Khong the ket noi toi server: " + ie.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
