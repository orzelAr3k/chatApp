package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The ClientController class is used to send and receive client-side messages
 * and GUI updates.
 * 
 * @author Arakadiusz Orzel
 * 
 */
public class ClientController implements Runnable {

    private ClientSocket clientSocket;

    public String selectedUser;
    public HashMap<String, ArrayList<Message>> messagesList = new HashMap<String, ArrayList<Message>>();
    public List<String> userList = new ArrayList<String>();

    // UI
    private JList<String> messagesJList;
    private JList<String> userJList;
    private JButton sendJButton;
    private JTextField messageJField;

    @Override
    public void run() {
        try {
            Socket socket = clientSocket.getSocket();
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
            InputStream serverInStream = socket.getInputStream();
            Scanner serverIn = new Scanner(serverInStream);

            userJList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    selectUser(e);
                }
            });

            messageJField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    messageSend(e, serverOut);
                }
            });

            sendJButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    messageSend(e, serverOut);
                }
            });

            while (!socket.isClosed()) {
                // If is new message
                if (serverIn.hasNextLine()) {
                    handleMessage(serverIn.nextLine());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The updateUserList function updates the user list in the GUI.
     * 
     */
    private void updateUserList() {
        userJList.setModel(new javax.swing.AbstractListModel<String>() {
            public int getSize() {
                return userList.size();
            }

            public String getElementAt(int i) {
                return userList.get(i);
            }
        });
    }

    /**
     * The updateMessageList function updates the JList of messages to display all
     * messages sent between the current user and a selected user. The function is
     * called whenever a new message is received or when a different user is
     * selected from the list of users.
     *
     */
    private void updateMessageList() {
        messagesJList.setModel(new javax.swing.AbstractListModel<String>() {
            public int getSize() {
                return messagesList.get(selectedUser).size();
            }

            public String getElementAt(int i) {
                Message element = messagesList.get(selectedUser).get(i);
                return "(" + element.getTime() + ") " + element.getUsername() + ": "
                        + element.getMessage();
            }
        });
    }

    /**
     * The selectUser function is called when the user selects a user from the list
     * of users.
     * It sets the selectedUser variable to be equal to that user, and then updates
     * the messageList with all messages sent between this client and that
     * selectedUser.
     * 
     * @param e The Jlist event from user List
     *
     */
    private void selectUser(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            selectedUser = userJList.getSelectedValue();

            if (messagesList.get(selectedUser) == null)
                messagesList.put(selectedUser, new ArrayList<Message>());
            updateMessageList();

        }
    }

    /**
     * The messageSend function is called when the user clicks on the send button.
     * It sends a message to another client, if there is text in the message field
     * and a user has been selected.
     * 
     *
     * @param e         The event from JButton responsible for sending messages
     * @param serverOut The server stream which send the message to the server
     *
     * 
     */
    private void messageSend(ActionEvent e, PrintWriter serverOut) {
        if (messageJField.getText().length() >= 1 || !selectedUser.isEmpty()) {
            // Get time and format
            LocalDateTime time = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY, HH:mm");

            JSONObject json = new JSONObject();
            json.put("from", clientSocket.getUserName());
            json.put("username", selectedUser);
            json.put("message", messageJField.getText());
            json.put("time", time.format(formatter).toString());
            serverOut.println(json.toString());
            serverOut.flush();

            messageJField.setText("");
            messageJField.requestFocus();
        } else {
            // Show information dialog
            JOptionPane.showMessageDialog(messagesJList, "Empty message or not selected user", "Warning",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * The handleMessage function is responsible for handling the messages that are
     * received from the server.
     * It parses through a JSON string and updates the userList, messageList, and
     * chatBox accordingly.
     *
     * @param jsonString The message from the server as a string
     *
     */
    private void handleMessage(String jsonString) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = (JSONObject) new JSONParser().parse(jsonString);

                    // Set values
                    String username = (String) json.get("username");
                    String message = (String) json.get("message");
                    String from = (String) json.get("from");
                    String time = (String) json.get("time");

                    if (username != null && message != null && time != null) {
                        if (messagesList.get(from) == null)
                            messagesList.put(from, new ArrayList<Message>());

                        if (from.equals(clientSocket.getUserName()))
                            messagesList.get(username).add(new Message("You", message, time));
                        else
                            messagesList.get(from).add(new Message(from, message, time));

                        // Update message list
                        updateMessageList();

                    }

                    JSONArray usersArray = (JSONArray) json.get("users");
                    if (usersArray != null) {
                        userList = (List<String>) usersArray.stream().map(Object::toString)
                                .collect(Collectors.toList());

                        // Update user list
                        updateUserList();
                    }

                    String usernameExit = (String) json.get("usernameExit");
                    if (usernameExit != null) {
                        // Remove user from user list
                        userList.removeIf(u -> u.equals(usernameExit));
                        messagesList.remove(usernameExit);

                        // Update user list
                        updateUserList();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * The setUserSocket function sets the user's socket to the clientSocket
     * parameter.
     * 
     * @param clientSocket Set the clientsocket field of this class
     *
     */
    public void setUserSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * The setUserList function is used to set the userJList parameter.
     * 
     * @param userJList Set the userjlist field of this class
     *
     */
    public void setUserList(JList<String> userJList) {
        this.userJList = userJList;
    }

    /**
     * The setMessageList function sets the messagesJList variable.
     * 
     * @param messagesJList Set the messagesjlist field of this class
     *
     */
    public void setMessageList(JList<String> messagesJList) {
        this.messagesJList = messagesJList;
    }

    /**
     * The setMessageField function sets the messageJField variable to the
     * JTextField passed in as a parameter.
     * 
     * @param messageJField Set the messagejfield field of this class
     *
     */
    public void setMessageField(JTextField messageJField) {
        this.messageJField = messageJField;
    }

    /**
     * The setSendButton function sets the sendJButton variable to the JButton
     * object passed in as a parameter.
     * 
     * @param sendJButton Set the sendjbutton field of this class
     *
     */
    public void setSendButton(JButton sendJButton) {
        this.sendJButton = sendJButton;
    }
}
