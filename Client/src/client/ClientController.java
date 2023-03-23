package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TimeZone;

import org.json.simple.JSONObject;

public class ClientController implements Runnable {

    private ClientSocket clientSocket;
    private MessageHandler messageHandler;

    private final LinkedList<String> newMessages = new LinkedList<>();
    private boolean messageWaiting = false;

    @Override
    public void run() {
        try {
            Socket socket = this.clientSocket.getSocket();
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
            InputStream serverInStream = socket.getInputStream();
            Scanner serverIn = new Scanner(serverInStream);

            while (true) {
                // If is new message
                if (serverIn.hasNextLine()) {
                    messageHandler.handleMessage(serverIn.nextLine());
                }

                // If there are new messages from current user
                if (this.messageWaiting) {
                    String nextSend = "";
                    synchronized (this.newMessages) {
                        nextSend = this.newMessages.pop();
                        this.messageWaiting = !this.newMessages.isEmpty();
                    }

                    // Output message
                    JSONObject json = new JSONObject();
                    json.put("message", nextSend);
                    json.put("timezone", TimeZone.getDefault().getID());

                    serverOut.println(json.toString());
                    serverOut.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setMessaageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
}
