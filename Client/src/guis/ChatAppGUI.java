package guis;

import javax.swing.*;

import client.ClientSocket;
import utils.Property;


public class ChatAppGUI {
    
    final static Property clientProperties = new Property("./client.properties");
    final ClientSocket socketClient = new ClientSocket(clientProperties.getProperty("host"), Integer.parseInt(clientProperties.getProperty("port")));
    
    public ChatAppGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        new MainFrame(socketClient);
    }
}
