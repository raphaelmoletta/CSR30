package br.edu.utfpr.dainf.csr30.webserver.core;

import br.edu.utfpr.dainf.csr30.webserver.MainFrame;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author usuario_padrao
 */
public class ListnerThread implements Runnable {

    @Override
    public void run() {
        try (ServerSocket welcomeSocket = new ServerSocket(Configurations.getInstance().getPort())) {
            String request;
            while (Configurations.getInstance().isRunning()) {
                Socket connectionSocket = welcomeSocket.accept();
                MainFrame.putMessage(connectionSocket.getRemoteSocketAddress().toString());
                new Thread(new ResponseThread(connectionSocket)).start();
            }
            welcomeSocket.close();
        } catch (IOException e) {
            MainFrame.putMessage(e.getMessage());
        }
    }

}
