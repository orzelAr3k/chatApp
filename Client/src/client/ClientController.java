package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ClientController implements Runnable {

    private ClientSocket clientSocket;

    private final LinkedList<String> newMessages = new LinkedList<>();
    private boolean messageWaiting = false;

    public String selectedUser;
    public HashMap<String, ArrayList<Message>> messagesList = new HashMap<String, ArrayList<Message>>();
    public List<String> userList = new ArrayList<String>();

    // UI
    private JList<String> messagesJList;
    private JList<String> userJList;
    private JButton sendButton;
    private JTextField messageField;

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
                    if (!e.getValueIsAdjusting()) {
                        selectedUser = userJList.getSelectedValue();
                        System.out.println(selectedUser);

                        if (messagesList.get(selectedUser) == null)
                            messagesList.put(selectedUser, new ArrayList<Message>());
                        messagesJList.setModel(new javax.swing.AbstractListModel<String>() {
                            public int getSize() {
                                return messagesList.get(selectedUser).size();
                            }

                            public String getElementAt(int i) {
                                Message element = messagesList.get(selectedUser).get(i);
                                return "(" + element.getTime() + ")" + element.getUsername() + ": "
                                        + element.getMessage();
                            }
                        });

                    }
                }
            });

            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // messageSend(e);
                    if (messageField.getText().length() >= 1) {
                        JSONObject json = new JSONObject();
                        json.put("from", clientSocket.getUserName());
                        json.put("username", selectedUser);
                        json.put("message", messageField.getText());
                        json.put("time", TimeZone.getDefault().getID());
                        serverOut.println(json.toString());
                        serverOut.flush();

                        messageField.setText("");
                        messageField.requestFocus();
                    }

                }
            });

            while (!socket.isClosed()) {

                // If is new message
                if (serverIn.hasNextLine()) {
                    handleMessage(serverIn.nextLine());
                }

                // If there are new messages from current user
                if (this.messageWaiting) {
                    String nextSend = "";
                    // synchronized (this.newMessages) {
                    nextSend = this.newMessages.pop();
                    this.messageWaiting = !this.newMessages.isEmpty();
                    // }

                    // // Update message list
                    // if (messagesList.get(selectedUser) == null)
                    // messagesList.put(selectedUser, new ArrayList<Message>());

                    // messagesList.get(selectedUser).add(new Message(clientSocket.getUserName(),
                    // nextSend, TimeZone.getDefault().getID()));
                    // messagesJList.setModel(new javax.swing.AbstractListModel<String>() {
                    // public int getSize() {
                    // return messagesList.get(selectedUser).size();
                    // }

                    // public String getElementAt(int i) {
                    // Message element = messagesList.get(selectedUser).get(i);
                    // return "(" + element.getTime() + ")" + element.getUsername() + ": "
                    // + element.getMessage();
                    // }
                    // });

                    // Output message
                    JSONObject json = new JSONObject();
                    json.put("from", clientSocket.getUserName());
                    json.put("username", selectedUser);
                    json.put("message", nextSend);
                    json.put("time", TimeZone.getDefault().getID());
                    System.out.println(json);
                    serverOut.println(json.toString());
                    serverOut.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void messageSend(ActionEvent e) {
        if (messageField.getText().length() >= 1) {
            if (clientSocket.getAccessThread().isAlive()) {
                // synchronized (this.newMessages) {
                this.messageWaiting = true;
                this.newMessages.push(messageField.getText());
                // }
            }
            messageField.setText("");
            messageField.requestFocus();
        }
    }

    public void handleMessage(String jsonString) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = (JSONObject) new JSONParser().parse(jsonString);
                    System.out.println(json);
                    // Set values
                    String username = (String) json.get("username");
                    String message = (String) json.get("message");
                    String from = (String) json.get("from");
                    String time = (String) json.get("time");

                    if (username != null && message != null && time != null) {
                        if (messagesList.get(from) == null)
                            messagesList.put(from, new ArrayList<Message>());
                        messagesList.get(from).add(new Message(username, message, time));

                        // // Update message list
                        messagesJList.setModel(new javax.swing.AbstractListModel<String>() {
                            public int getSize() {
                                return messagesList.get(selectedUser).size();
                            }

                            public String getElementAt(int i) {
                                Message element = messagesList.get(selectedUser).get(i);
                                return "(" + element.getTime() + ")" + element.getUsername() + ": "
                                        + element.getMessage();
                            }
                        });

                    }

                    JSONArray usersArray = (JSONArray) json.get("users");
                    if (usersArray != null) {
                        userList = (List<String>) usersArray.stream().map(Object::toString)
                                .collect(Collectors.toList());
                        userJList.setModel(new javax.swing.AbstractListModel<String>() {
                            public int getSize() {
                                return userList.size();
                            }

                            public String getElementAt(int i) {
                                return userList.get(i);
                            }
                        });
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setUserSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setUserList(JList<String> userJList) {
        this.userJList = userJList;
    }

    public void setMessageList(JList<String> messagesJList) {
        this.messagesJList = messagesJList;
    }

    public void setMessageField(JTextField messageField) {
        this.messageField = messageField;
    }

    public void setSendButton(JButton sendButton) {
        this.sendButton = sendButton;
    }
}
