package br.edu.utfpr.dainf.csr30.webserver.core;

import br.edu.utfpr.dainf.csr30.webserver.MainFrame;

/**
 *
 * @author usuario_padrao
 */
public class WebServer {

    public static boolean stop() {
        Configurations.getInstance().setRunning(false);
        return true;
    }

    public static boolean start(String serverPort) {
        try {
            Configurations.getInstance().setPort(Integer.parseInt(serverPort));
            new Thread(new ListnerThread()).start();
            Configurations.getInstance().setRunning(true);
            
        } catch (NumberFormatException e) {
            MainFrame.putMessage("Server port is not numeric.");
            return false;
        }
        
        return true;
    }
    
}
