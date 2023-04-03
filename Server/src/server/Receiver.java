package server;

import java.io.PrintStream;
import java.util.Scanner;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * 
 * The Receiver class is a thread that runs in the background of the
 * server.&nbsp;It listens for incoming messages from clients and then sends
 * them to be processed by the Server class.
 * 
 * @author Arkadiusz Orzel
 */
public class Receiver implements Runnable {
    private final Client client;
    private final Server server;

    /**
     * The Receiver constructor to assign client and server variable.
     * 
     * @param server The server instance
     * @param client The client instance
     *
     */
    public Receiver(Server server, Client client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            // Read from client
            Scanner fromClient = new Scanner(client.getInputStream());

            while (!client.isSocketClosed()) {
                if (fromClient.hasNextLine()) {
                    JSONObject json = (JSONObject) new JSONParser().parse(fromClient.nextLine());
                    // Set values
                    Boolean exit = Boolean.parseBoolean((String) json.get("exit"));
                    String message = (String) json.get("message");
                    String username = (String) json.get("username");
                    String time = (String) json.get("time");

                    // Exit when client close socket
                    if (exit) {
                        break;
                    }

                    // Send message to other user
                    JSONObject jsonMessage = new JSONObject();
                    if (username != null) {
                        // Set vale for field message
                        jsonMessage.put("message", message);
                        jsonMessage.put("username", username);
                        jsonMessage.put("time", time);
                        jsonMessage.put("from", client.getUserName());
                        sendMessage(jsonMessage.toString(), server.getClientByName(username));
                        sendMessage(jsonMessage.toString(), client);
                    } else {
                        // Set vale for field message
                        jsonMessage.put("message", "Cannot send message!");
                        jsonMessage.put("username", client.getUserName());
                        jsonMessage.put("from", client.getUserName());
                        jsonMessage.put("time", time);
                        sendMessage(jsonMessage.toString(), client);
                    }
                }
            }
            closeSocket();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * The closeSocket function closes the socket and deletes the client from the
     * server's list of client.
     *
     */
    private void closeSocket() {
        server.deleteClientByName(client.getUserName());
        System.out.println("Socket close: " + client.getUserName());
    }

    /**
     * The sendMessage function takes a message and a Client thread as parameters.
     * It then creates a PrintStream object called userOut, which is assigned to the
     * output stream of the client thread.
     * If this PrintStream object is not null, it prints out the message to that
     * stream and flushes it.
     *
     * 
     * @param String message The message which will be send to client
     * @param Client thread THe instance of {@link server.Client Client}
     * 
     * @see server.Client Client class
     *
     */
    private void sendMessage(String message, Client thread) {
        PrintStream userOut = thread.getOutStream();
        if (userOut != null) {
            userOut.println(message);
            userOut.flush();
        }
    }
}
