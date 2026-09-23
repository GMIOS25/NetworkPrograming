package vn.viettuts.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Server TCP minh họa từ VietTuts (https://viettuts.vn/lap-trinh-mang-voi-java).
 * Kế thừa Thread, quản lý timeout và trao đổi chuỗi có định dạng UTF.
 */
public class ServerExample extends Thread {
    private ServerSocket serverSocket;

    public ServerExample(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        // Timeout 20 giay cho viec cho doi ket noi
        serverSocket.setSoTimeout(20000);
    }

    @Override
    public void run() {
        System.out.println("[Server] Dang khoi dong va cho ket noi tren cong " 
                + serverSocket.getLocalPort() + "...");
        while (true) {
            try {
                Socket server = serverSocket.accept();
                System.out.println("[Server] Da chap nhan ket noi tu: " 
                        + server.getRemoteSocketAddress());

                DataInputStream in = new DataInputStream(server.getInputStream());
                String clientMsg = in.readUTF();
                System.out.println("[Server] Nhan tu client: " + clientMsg);

                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Cam on ban da ket noi toi " 
                        + server.getLocalSocketAddress() + "\nTam biet!");

                server.close();
                System.out.println("[Server] Da dong ket noi voi client.");
                break; // Sau khi phuc vu xong 1 client mau thi dung (de tien cho bai demo test)
            } catch (SocketTimeoutException s) {
                System.out.println("[Server] Het thoi gian cho (Socket timed out)!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 6060;
        try {
            Thread t = new ServerExample(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
