package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    
    private final int PORT;
    private final ArrayList<String> clientList = new ArrayList<String>();

    /**
     * @param port port the socket should connect to.
     */
    public Server(int port) {
        this.PORT = port;
    }

    public void startSocketServer() {
        try {
            System.out.println("Server start: localhost:5634");
            ServerSocket socketServer = new ServerSocket(this.PORT);
            this.acceptClient(socketServer);
        } catch (IOException e) {
            System.err.println("Could not connect to port: " + this.PORT);
        }
    }

    public void acceptClient(ServerSocket socketServer) {
        while(true) {
            try {
                Socket socket = socketServer.accept();
                String username = (new Scanner(socket.getInputStream())).nextLine().replaceAll("\\s", "");
                this.clientList.add(username);
                System.out.println(this.clientList);

            } catch (IOException e) {
                System.err.println("Failed accepting new user!");
            }
        }
    }

    public List<String> getNameClients() {
        return this.clientList;
    }
}
