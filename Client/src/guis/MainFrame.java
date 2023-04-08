package guis;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

    /**
     * The MainFrame function is the constructor for the MainFrame class.
     * It creates a new instance of the MainFrame class, which is used to create and
     * display
     * all of the GUI elements that are used in this application. The function also
     * sets up
     * all of these GUI elements, including their listeners and action handlers.
     * Finally, it calls
     * userLogin() to prompt users for their login information when they first open
     * up ChatApp.
     *
     * @param clientSocket Pass the clientsocket object to this class
     *
     */
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

        userLogin();
    }

    /**
     * The userLogin function is responsible for adding ActionListeners to the
     * loginPanel's usernameField, loginJButton and exitButton.
     * The actionPerformed method of each ActionListener calls the appropriate
     * function (loginAction or exitAction) when an event occurs.
     *
     */
    private void userLogin() {
        loginPanel.getUsernameField().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    loginAction(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        loginPanel.getLoginJButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    loginAction(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        loginPanel.getExitButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    exitAction(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    /**
     * The loginAction function is called when the user clicks on the login button.
     * It checks if there is a username entered in the text field, and if so it
     * calls joinServer().
     *
     * @param e Get the source of the event
     *
     */
    public void loginAction(ActionEvent e) throws IOException {
        username = loginPanel.getUsernameField().getText();
        if (username.length() < 1) {
            JOptionPane.showMessageDialog(loginPanel, "Enter the username!", "WARNING", JOptionPane.WARNING_MESSAGE);
            System.out.println("Error username");
        } else {
            mainChatPanel.getMessageList().setCurrentUser(username);
            joinServer(e);
        }
    }

    /**
     * The exitAction function is called when the user clicks on the exit button.
     * It closes all of the windows and exits out of the program.
     * 
     * @param e Get the source of the event
     *
     */
    public void exitAction(ActionEvent e) throws IOException {
        this.dispose();
    }

    /**
     * The joinServer function is called when the user clicks on the login button.
     * It creates a new ClientController object, which will be used to control all
     * of the client's actions.
     * The function then sets up all of the necessary fields in order for
     * communication between server and client to occur.
     * Finally, it attempts to connect with a server using its username and
     * ClientController object as parameters. If successful, it hides loginPanel and
     * shows mainChatPanel; otherwise, an error message is displayed.
     *
     * @param e Get the source of the event
     *
     */
    private void joinServer(ActionEvent e) throws IOException {
        ClientController clientController = new ClientController();
        clientController.setUserSocket(clientSocket);
        clientController.setMessageList(mainChatPanel.getMessageList());
        clientController.setUserList(mainChatPanel.getUserList());
        clientController.setMessageField(mainChatPanel.getMessageField());
        clientController.setSendButton(mainChatPanel.getSendButton());

        // Wait till connected
        boolean connected = clientSocket.connectToServer(username, clientController);
        if (connected) {
            // Open chat screen
            loginPanel.setVisible(false);
            mainChatPanel.setVisible(true);

        } else {
            // Show error popup
            JOptionPane.showMessageDialog(loginPanel, "Something went wrong while connectiong to server!", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
