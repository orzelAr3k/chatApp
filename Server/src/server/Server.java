package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;

/**
 * The Server class is used to create the server and accept incoming connections
 * from clients.&nbsp;When a connection is made, the Server function will create a
 * new thread to handle that client's requests.
 * 
 * @author Arkadiusz Orzel
 * 
 */
public class Server {

    private final int PORT;
    private final ArrayList<Client> clientList = new ArrayList<Client>();
    private ServerSocket serverSocket;

    /**
     * The Server constructor is assign a value to the PORT variable.
     *
     * @param port Set the port number that the server will listen on
     *
     */
    public Server(int port) {
        this.PORT = port;
    }

    /**
     * The startServerSocket function creates a new ServerSocket object, which is
     * used to accept incoming client connections.
     * The function also calls the acceptClient function, which accepts incoming
     * client connections and starts a new thread for each connection.
     */
    public void startServerSocket() {
        try {
            System.out.println("Server start: localhost:" + PORT);
            serverSocket = new ServerSocket(PORT);
            acceptClient();
        } catch (IOException e) {
            System.err.println("Could not connect to port: " + PORT);
            System.exit(1);
        }
    }

    /**
     * The acceptClient function is responsible for accepting new clients and adding
     * them to the clientList.
     * It also sends a welcome message to the newly accepted client, as well as
     * broadcasting a list of all connected users.
     *
     */
    public void acceptClient() {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();

                String username = (new Scanner(socket.getInputStream())).nextLine().replaceAll("\\s", "");

                // Create new client
                Client newClient = new Client(socket, username);

                // Add new client to list
                clientList.add(newClient);

                // Create a new thread incoming message of new user
                new Thread(new Receiver(this, newClient)).start();

                // Create json object
                JSONObject jsonMessage = new JSONObject();

                System.out.println("Socket open: " + username);
                // Set vale for field message
                jsonMessage.put("message", "Welcome to the server!");
                // Send welcome message
                PrintStream userOut = newClient.getOutStream();
                userOut.println(jsonMessage.toString());

                // Send list of currently connected users
                broadcastClientList();

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed accepting new user!");
            }
        }
        closeSocketServer();
    }

    /**
     * The closeSocketServer function closes the socket server.
     *
     */
    private void closeSocketServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The getAllClients function returns a list of all clients connected to the
     * server.
     * 
     *
     * @return A list of all clients
     *
     */
    public List<Client> getAllClients() {
        return this.clientList;
    }

    /**
     * The getClientByName function takes a String username as an argument and
     * returns the Client object that has that username.
     * 
     *
     * @param username Find the client in the list of clients by username
     *
     * @return A client if it exists, or null otherwise
     *
     */
    public Client getClientByName(String username) {
        for (Client client : clientList) {
            if (client.getUserName().equals(username))
                return client;
        }
        return null;
    }

    /**
     * The deleteClientByName function removes a client from the server's list of
     * clients.
     * 
     *
     * @param username Find the client in the list by username
     *
     */
    public void deleteClientByName(String username) {
        clientList.removeIf(c -> c.getUserName().equals(username));
        JSONObject jsonMessage = new JSONObject(new HashMap<String, String>() {
            {
                put("userExit", username);
            }
        });
        broadcastMessage(jsonMessage);
    }

    /**
     * The broadcastMessage function sends a message to all clients connected to the
     * server.
     * 
     *
     * @param json The message which will be send to all clients
     *
     */
    public void broadcastMessage(JSONObject json) {
        for (Client client : clientList) {
            client.getOutStream().println(json.toString());
        }
    }

    /**
     * The broadcastClientList function is used to send a list of all the users
     * currently connected to the server to each client.
     * This function is called whenever a new user connects to the server.
     *
     */
    public void broadcastClientList() {
        for (Client client : clientList) {
            // Create json object
            JSONObject jsonMessage = new JSONObject();
            // User list without actual user
            List<String> users = clientList.stream().filter((c) -> c != client).map((c) -> c.getUserName())
                    .collect(Collectors.toList());

            // Set value for field users
            jsonMessage.put("users", users);
            client.getOutStream().println(jsonMessage.toString());
        }
    }
}
