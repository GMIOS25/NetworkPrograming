package com.gpcoder.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Luồng xử lý cho từng Client kết nối tới Server trong mô hình MultiServer.
 * Nguồn: GP Coder (https://gpcoder.com/3679-xay-dung-ung-dung-client-server-voi-socket-trong-java/)
 */
public class WorkerThread extends Thread {
    private Socket socket;

    public WorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("[WorkerThread] Bat dau xu ly cho: " + socket);
        try {
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            while (true) {
                int ch = is.read(); // Nhan tung byte tu client
                if (ch == -1) {
                    break;
                }
                os.write(ch); // Echo nguoc lai cho client
                os.flush();
            }
            socket.close();
        } catch (IOException e) {
            System.err.println("[WorkerThread] Loi xu ly yeu cau: " + e);
        }
        System.out.println("[WorkerThread] Hoan thanh xu ly cho: " + socket);
    }
}
