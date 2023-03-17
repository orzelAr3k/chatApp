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
            Scanner fromClient = new Scanner(this.client.getInputStream());

            while (!this.client.isSocketClosed()) {
                if (fromClient.hasNextLine()) {
                    JSONObject json = (JSONObject) new JSONParser().parse(fromClient.nextLine());

                    // Send message
                    this.sendMessage(json.toString(), this.client);
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
