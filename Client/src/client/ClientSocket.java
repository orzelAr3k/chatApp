package client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ClientSocket {
    private final String HOST;
    private final int PORT;
    private Socket socket;
    private Thread accessThread;
    private String username;

    public ClientSocket(String host, int port) {
        this.HOST = host;
        this.PORT = port;
    }

    public boolean connectToServer(String username) {
        try {
            this.username = username;
    
            System.out.println("Connecting...");

            // Connecting to server
            this.socket = new Socket(this.HOST, this.PORT);

            // Waiting 1s
            Thread.sleep(1000);

            PrintStream streamToServer = new PrintStream(this.socket.getOutputStream());
            // Send to server username
            streamToServer.println(username);

            // this.accessThread = new Thread(runnable);
            // this.accessThread.start();
            return true;

        } catch (IOException | InterruptedException e) {
            System.err.println("Something went wrong while connecting to server!");
            return false;
        }
    }
}
