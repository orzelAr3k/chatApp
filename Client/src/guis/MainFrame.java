package guis;

import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame implements ActionListener {

    private LoginPanel loginPanel;
    private MainChatPanel mainChatPanel;
    
    public MainFrame() {
        loginPanel = new LoginPanel(this);
        mainChatPanel = new MainChatPanel();

        setTitle("ChatApp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(mainChatPanel);
        mainChatPanel.setVisible(false);
        add(loginPanel);
        loginPanel.setVisible(true);


        // Display the window.
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginPanel.loginButton) {
            loginPanel.setVisible(false);
            mainChatPanel.setVisible(true);
        }
    }
}
