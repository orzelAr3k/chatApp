package server;

import java.io.PrintStream;
import java.util.Scanner;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Receiver implements Runnable {
    private final Client client;
    private final Server server;

    public Receiver(Server server, Client client) {
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            Scanner fromClient = new Scanner(client.getInputStream());

            while (!client.isSocketClosed()) {
                if (fromClient.hasNextLine()) {
                    JSONObject json = (JSONObject) new JSONParser().parse(fromClient.nextLine());
                     // Set values
                    System.out.println(json);
                    String message = (String) json.get("message");
                    String username = (String) json.get("username");
                    String time = (String) json.get("time");
                    // Send message to other user
                    JSONObject jsonMessage = new JSONObject();
                    if (username != null) {
                        // Set vale for field message
                        jsonMessage.put("message", message);
                        jsonMessage.put("username", username);
                        jsonMessage.put("time", time);
                        jsonMessage.put("from", client.getUserName());
                        this.sendMessage(jsonMessage.toString(), server.getClientByName(username));
                        this.sendMessage(jsonMessage.toString(), client);
                    } else {
                        // Set vale for field message
                        jsonMessage.put("message", "Cannot send message!");
                        jsonMessage.put("username", client.getUserName());
                        jsonMessage.put("from", client.getUserName());
                        jsonMessage.put("time", time);
                        this.sendMessage(jsonMessage.toString(), client);
                    }
                   
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message, Client thread) {
        PrintStream userOut = thread.getOutStream();
        if (userOut != null) {
            userOut.println(message);
            userOut.flush();
        }
    }
}
