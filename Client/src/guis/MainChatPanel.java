package guis;

import javax.swing.JList;

public class MainChatPanel extends javax.swing.JPanel {

    private javax.swing.JScrollPane chatPane;
    private javax.swing.JPanel chatPanel;
    private javax.swing.JTextField messageField;
    private javax.swing.JList<String> messagesList;
    private javax.swing.JButton sendButton;
    private javax.swing.JList<String> userList;
    private javax.swing.JLabel userListLabel;
    private javax.swing.JScrollPane userScrolPane;
    private javax.swing.JPanel usersPanel;
    
    public MainChatPanel() {
        usersPanel = new javax.swing.JPanel();
        userListLabel = new javax.swing.JLabel();
        userScrolPane = new javax.swing.JScrollPane();
        userList = new javax.swing.JList<>();
        chatPanel = new javax.swing.JPanel();
        messageField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        chatPane = new javax.swing.JScrollPane();
        messagesList = new javax.swing.JList<>();

        setSize(960, 720);
        setPreferredSize(new java.awt.Dimension(960, 720));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        usersPanel.setPreferredSize(new java.awt.Dimension(200, 735));
        usersPanel.setLayout(new java.awt.BorderLayout(0, 20));

        userListLabel.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        userListLabel.setText("UserList:");
        usersPanel.add(userListLabel, java.awt.BorderLayout.PAGE_START);

        userScrolPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        userList.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        userList.setForeground(new java.awt.Color(102, 102, 102));
        // userList.setModel(new javax.swing.AbstractListModel<String>() {
        //     public int getSize() { return MessageHandler.userList.size(); }
        //     public String getElementAt(int i) { return MessageHandler.userList.get(i); }
        // });
        userList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userList.setFixedCellHeight(40);
        userList.setPreferredSize(new java.awt.Dimension(45, 600));
        userScrolPane.setViewportView(userList);

        usersPanel.add(userScrolPane, java.awt.BorderLayout.CENTER);

        add(usersPanel);

        messageField.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        messageField.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        sendButton.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        sendButton.setText("Send");

        messagesList.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        messagesList.setEnabled(false);
        messagesList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        chatPane.setViewportView(messagesList);

        javax.swing.GroupLayout chatPanelLayout = new javax.swing.GroupLayout(chatPanel);
        chatPanel.setLayout(chatPanelLayout);
        chatPanelLayout.setHorizontalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chatPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chatPane)
                    .addGroup(chatPanelLayout.createSequentialGroup()
                        .addComponent(messageField, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                        .addGap(53, 53, 53)
                        .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)))
                .addGap(36, 36, 36))
        );
        chatPanelLayout.setVerticalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chatPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(chatPane, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sendButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(messageField, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(34, 34, 34))
        );

        add(chatPanel);
    }

    public javax.swing.JList<String> getUserList() {
        return this.userList;
    }

    public javax.swing.JList<String> getMessageList() {
        return this.messagesList;
    }
}
