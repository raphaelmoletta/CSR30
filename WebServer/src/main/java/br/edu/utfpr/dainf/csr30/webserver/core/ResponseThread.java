package br.edu.utfpr.dainf.csr30.webserver.core;

import br.edu.utfpr.dainf.csr30.webserver.MainFrame;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario_padrao
 */
public class ResponseThread implements Runnable {

    private Socket socket;

    public ResponseThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            output.write("HTTP/1.1 200 OK\r\n<HTML><BODY>COCO<\\BODY><\\HTML>\r\n".getBytes());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            String s = input.readLine();
            while (!s.isEmpty()) {
                MainFrame.putMessage(s);
                s = input.readLine();
            }
            MainFrame.putMessage(s);
            socket.close();
        } catch (IOException e) {
            MainFrame.putMessage(e.getMessage());
        }

    }
}
