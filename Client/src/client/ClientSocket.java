package client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


/**
 * The ClientSocket class is used to make the connection with server and store user socket and username.
 * 
 * @author Arkadiusz Orzel
 */
public class ClientSocket {
    private final String HOST;
    private final int PORT;
    private Socket socket;
    private Thread accessThread;
    private String username;

    
    /**
     * The ClientSocket constructor assign variables required to make connection to the server.
     * 
     * @param host Connect to the server
     * @param port Set the port number
     *
     */
    public ClientSocket(String host, int port) {
        this.HOST = host;
        this.PORT = port;
    }
    
    /**
     * The connectToServer function is used to connect the client to the server.
     * 
     *
     * @param username The username of this client
     * @param runnable The Runnable object to run in thread as ClientController
     *
     * @return Client is connect to server or not
     */
    public boolean connectToServer(String username, Runnable runnable) {
        try {
            this.username = username;
    
            System.out.println("Connecting...");

            // Connecting to server
            socket = new Socket(HOST, PORT);

            // Waiting 1s
            Thread.sleep(1000);

            PrintStream streamToServer = new PrintStream(socket.getOutputStream());
            // Send to server username
            streamToServer.println(username);

            // Start thread
            accessThread = new Thread(runnable);
            accessThread.start();
            return true;

        } catch (IOException | InterruptedException e) {
            System.err.println("Something went wrong while connecting to server!");
            return false;
        }
    }

    
    /**
     * The getUserName function returns the username of the user.
     *
     * @return The username of the user
     *
     */
    public String getUserName() {
        return this.username;
    }

    
    /**
     * The getAccessThread function returns the accessThread variable.
     * 
     * @return The accessthread variable
     *
     */
    public Thread getAccessThread() {
        return this.accessThread;
    }

    
    /**
     * The getSocket function returns the socket that is used to connect to the server.
     * 
     * @return The socket object
     *
     */
    public Socket getSocket() {
        return this.socket;
    }
}
