package guis;

import javax.swing.JFrame;

import client.ClientController;
import client.ClientSocket;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    private final ClientSocket clientSocket;
    private String username;

    private LoginPanel loginPanel;
    private MainChatPanel mainChatPanel;

    public MainFrame(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;

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

        this.setUser();
    }

    private void setUser() {
        this.loginPanel.getLoginJButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    loginAction(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        this.loginPanel.getExitButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    exitAction(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        ;
    }

    public void loginAction(ActionEvent e) throws IOException {
        this.username = this.loginPanel.getUserName();
        if (this.username.length() < 1) {
            System.out.println("Error username");
        } else {
            joinServer(e);
        }
    }

    public void exitAction(ActionEvent e) throws IOException {
        this.dispose();
    }

    private void joinServer(ActionEvent e) throws IOException {
        ClientController clientController = new ClientController();
        clientController.setUserSocket(clientSocket);
        clientController.setMessageList(this.mainChatPanel.getMessageList());
        clientController.setUserList(this.mainChatPanel.getUserList());

        // Wait till connected
        boolean connected = clientSocket.connectToServer(this.username, clientController);
        if (connected) {
            // Open chat screen
            this.loginPanel.setVisible(false);
            this.mainChatPanel.setVisible(true);

        } else {
            // Show error popup
            System.out.println("Error");
        }
    }
}
