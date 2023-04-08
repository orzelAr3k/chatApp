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
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import guis.custom.CustomMessageList;
import guis.custom.CustomUserList;

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

    // UI
    private CustomMessageList customMessageList;
    private CustomUserList customUserList;
    private JButton sendButton;
    private JTextField messageField;

    @Override
    public void run() {
        try {
            Socket socket = clientSocket.getSocket();
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
            InputStream serverInStream = socket.getInputStream();
            Scanner serverIn = new Scanner(serverInStream);

            customUserList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    selectUser(e);
                }
            });

            messageField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    messageSend(e, serverOut);
                }
            });

            sendButton.addActionListener(new ActionListener() {
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
     * The updateMessageList function updates the JList of messages to display all
     * messages sent between the current user and a selected user. The function is
     * called whenever a new message is received or when a different user is
     * selected from the list of users.
     *
     */
    private void updateMessageList() {
        customMessageList.setModel(new javax.swing.AbstractListModel<Message>() {
            public int getSize() {
                return messagesList.get(selectedUser).size();
            }

            public Message getElementAt(int i) {
                return messagesList.get(selectedUser).get(i);
            }
        });

        // Scroll to last message
        int lastIndex = customMessageList.getModel().getSize() - 1;
        if (lastIndex >= 0) {
            customMessageList.ensureIndexIsVisible(lastIndex);
        }
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
        if (!e.getValueIsAdjusting() && customUserList.getSelectedValue() != null) {
            selectedUser = customUserList.getSelectedValue().getUsername();

            if (messagesList.get(selectedUser) == null)
                messagesList.put(selectedUser, new ArrayList<Message>());
            updateMessageList();

            customUserList.setUserUnread(selectedUser, false);
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
        if (messageField.getText().length() >= 1 && selectedUser != null) {
            // Get time and format
            LocalDateTime time = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY, HH:mm");

            JSONObject json = new JSONObject();
            json.put("from", clientSocket.getUserName());
            json.put("username", selectedUser);
            json.put("message", messageField.getText());
            json.put("time", time.format(formatter).toString());
            serverOut.println(json.toString());
            serverOut.flush();

            messageField.setText("");
            messageField.requestFocus();
        } else {
            // Show information dialog
            JOptionPane.showMessageDialog(customMessageList, "Empty message or not selected user", "Warning",
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

                    if (username != null && message != null && time != null && from != null) {
                        if (messagesList.get(from) == null)
                            messagesList.put(from, new ArrayList<Message>());

                        if (from.equals(clientSocket.getUserName())) {
                            messagesList.get(username).add(new Message(from, message, time));
                        } else {
                            messagesList.get(from).add(new Message(from, message, time));
                            // Set notification for new message
                            if (selectedUser == null)
                                customUserList.setUserUnread(from, true);
                            else if (!selectedUser.equals(from))
                                customUserList.setUserUnread(from, true);
                        }

                        // Update message list if selectedUser is not null
                        if (selectedUser != null)
                            updateMessageList();

                    }

                    JSONArray usersArray = (JSONArray) json.get("users");
                    if (usersArray != null) {
                        for (Object u : usersArray) {
                            // Update user list
                            customUserList.addUserIfNotExists((String) u);
                        }
                    }

                    String userExit = (String) json.get("userExit");
                    if (userExit != null) {
                        if (selectedUser != null && selectedUser.equals(userExit))
                            customMessageList.setModel(new DefaultListModel<Message>());

                        // Deletes this user's messages
                        messagesList.remove(userExit);

                        // Remove user from list of users
                        customUserList.removeUser(userExit);

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
     * The setUserList function is used to set the customUserList parameter.
     * 
     * @param customUserList Set the customUserList field of this class
     *
     */
    public void setUserList(CustomUserList customUserList) {
        this.customUserList = customUserList;
    }

    /**
     * The setMessageList function sets the customMessageList variable.
     * 
     * @param customMessageList Set the customMessageList field of this class
     *
     */
    public void setMessageList(CustomMessageList customMessageList) {
        this.customMessageList = customMessageList;
    }

    /**
     * The setMessageField function sets the messageField variable to the
     * JTextField passed in as a parameter.
     * 
     * @param messageField Set the messageField field of this class
     *
     */
    public void setMessageField(JTextField messageField) {
        this.messageField = messageField;
    }

    /**
     * The setSendButton function sets the sendButton variable to the JButton
     * object passed in as a parameter.
     * 
     * @param sendButton Set the sendButton field of this class
     *
     */
    public void setSendButton(JButton sendButton) {
        this.sendButton = sendButton;
    }
}
