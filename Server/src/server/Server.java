package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;

public class Server {

    private final int PORT;
    private final ArrayList<Client> clientList = new ArrayList<Client>();
    private ServerSocket socketServer;

    /**
     * @param port port the socket should connect to.
     */
    public Server(int port) {
        this.PORT = port;
    }

    public void startSocketServer() {
        try {
            System.out.println("Server start: localhost:5634");
            socketServer = new ServerSocket(this.PORT);
            this.acceptClient();
        } catch (IOException e) {
            System.err.println("Could not connect to port: " + this.PORT);
            System.exit(1);
        }
    }

    
    /** 
     * @param socketServer
     */
    public void acceptClient() {
        while (!socketServer.isClosed()) {
            try {
                Socket socket = socketServer.accept();
                String username = (new Scanner(socket.getInputStream())).nextLine().replaceAll("\\s", "");
                
                // Create new client
                Client newClient = new Client(socket, username);

                // Add new client to list
                this.clientList.add(newClient);

                // Create a new thread incoming message of new user
                new Thread(new Receiver(this, newClient)).start();

                // Create json object
                JSONObject jsonMessage = new JSONObject();
                // Set vale for field message
                jsonMessage.put("message", "Welcome to the server!");
                // Send welcome message
                PrintStream userOut = newClient.getOutStream();
                userOut.println(jsonMessage.toString());

                this.broadcastClientList();
            } catch (IOException e) {
                System.err.println("Failed accepting new user!");
            }
        }
    }

    public void closeSocketServer() {
        try {
            if (socketServer != null) {
                socketServer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * @return List<Client>
     */
    public List<Client> getAllClients() {
        return this.clientList;
    }

    
    /** 
     * @param username
     * @return Client
     */
    public Client getClientByName(String username) {
        for (Client client : this.clientList) {
            if (client.getUserName().equals(username)) return client;
        }
        return null;
    }

    public void broadcastClientList() {
        for (Client client : this.clientList) {
            // Create json object
            JSONObject jsonMessage = new JSONObject();
            List<String> users =  this.clientList.stream().filter((c) -> c != client).map((c) -> c.getUserName()).collect(Collectors.toList());

            // Set vale for field users
            jsonMessage.put("users", users);
            client.getOutStream().println(jsonMessage.toString());
        }
    }
}
