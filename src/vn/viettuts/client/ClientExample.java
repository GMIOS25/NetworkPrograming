package vn.viettuts.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Client TCP minh họa từ VietTuts (https://viettuts.vn/lap-trinh-mang-voi-java).
 * Kết nối tới Server TCP và trao đổi chuỗi UTF.
 */
public class ClientExample {

    public static void main(String[] args) {
        String serverName = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 6060;

        try {
            System.out.println("[Client] Dang ket noi toi " + serverName + " tren cong " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("[Client] Ket noi thanh cong toi " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            String greeting = "Xin chao tu " + client.getLocalSocketAddress();
            out.writeUTF(greeting);
            System.out.println("[Client] Da gui: " + greeting);

            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("[Client] Server phan hoi: \n" + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
