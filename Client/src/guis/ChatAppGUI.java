package guis;

import javax.swing.*;


public class ChatAppGUI {
    public ChatAppGUI() {


        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        new MainFrame();
    }
}
