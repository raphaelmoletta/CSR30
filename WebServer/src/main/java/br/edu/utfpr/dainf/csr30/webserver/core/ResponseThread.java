package br.edu.utfpr.dainf.csr30.webserver.core;

import br.edu.utfpr.dainf.csr30.webserver.MainFrame;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

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
            DataInputStream input = new DataInputStream(socket.getInputStream());
            String s = input.readLine();
            String commands[] = s.split(" ");
            
            try {
                if("/".equals(commands[1])) {
                    commands[1] = "/index.html";
                    MainFrame.putMessage("# Replacing: / to /index.html");
                }
                MainFrame.putMessage("File: " + Configurations.getInstance().getHome() + commands[1]);
                File file = new File(Configurations.getInstance().getHome() + commands[1]);
                if(!file.exists()) {
                    output.write("HTTP/1.1 404 Not Found\r\nContent-Type: text/html \r\n\r\n<HTML><HEAD><TITLE>WebServer Error</TITLE></HEAD><BODY><H1>ERROR 404 - Page Not Found</H1><BR/>".getBytes());
                    output.write(commands[1].getBytes());
                    output.write("</BODY></HTML>\r\n\r\n".getBytes());
                } else {
                    output.write("HTTP/1.1 200 OK\r\nContent-Type: text/html \r\n\r\n".getBytes());
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String text = null;

                    while ((text = reader.readLine()) != null) {
                        MainFrame.putMessage("Texto: " + text);
                        output.write(text.getBytes());
                    }
                    
                    output.write("\r\n\r\n".getBytes());
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            
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
