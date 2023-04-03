package guis;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import javax.swing.SwingUtilities;

import org.json.simple.JSONObject;

import client.ClientSocket;
import utils.Property;

/**
 * The ChatAppGUI class is used to create client connection to server and create GUI.
 * 
 * @author Arkadiusz Orzel
 */
public class ChatAppGUI {

    final static Property clientProperties = new Property("./client.properties");
    final ClientSocket clientSocket = new ClientSocket(clientProperties.getProperty("host"),
            Integer.parseInt(clientProperties.getProperty("port")));

    /**
     * The ChatAppGUI constructor creates the GUI for the chat application.
     *
     */
    public ChatAppGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * The createAndShowGUI function creates a new MainFrame object and adds a
     * WindowListener to it.
     * The WindowListener is used to close the socket connection when the window is
     * closed.
     *
     */
    private void createAndShowGUI() {
        new MainFrame(clientSocket).addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    if (clientSocket.getSocket() != null && clientSocket.getSocket().isConnected()) {
                        PrintStream streamToServer = new PrintStream(clientSocket.getSocket().getOutputStream());
                        streamToServer.println(new JSONObject(new HashMap<String, String>() {
                            {
                                put("exit", "true");
                            }
                        }));
                        streamToServer.close();
                        clientSocket.getSocket().close();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }
}
