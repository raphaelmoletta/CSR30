package br.edu.utfpr.dainf.csr30.webserver.core;

/**
 *
 * @author usuario_padrao
 */
public class Configurations {
    private static Configurations instance = null;
    
    private int port  = 1234;
    private boolean running = false;
    
    private Configurations() {
        
    }

    
    public static synchronized Configurations getInstance() {
        if(instance == null)
            instance = new Configurations();
        return instance;
    }

    public synchronized int getPort() {
        return port;
    }

    public synchronized void setPort(int port) {
        this.port = port;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }
    
    
}
