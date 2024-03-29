package guis;

public class LoginPanel extends javax.swing.JPanel {

    private javax.swing.JTextField addressField;
    private javax.swing.JLabel adressLabel;
    private javax.swing.JLabel appName;
    private javax.swing.JButton exitButton;
    private javax.swing.JPanel formPanel;
    private javax.swing.JPanel leftPanel;
    public javax.swing.JButton loginButton;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField portField;
    private javax.swing.JLabel portLabel;
    private javax.swing.JPanel rightPanel;

    public LoginPanel(MainFrame frame) {
        leftPanel = new javax.swing.JPanel();
        appName = new javax.swing.JLabel();
        rightPanel = new javax.swing.JPanel();
        exitButton = new javax.swing.JButton();
        loginButton = new javax.swing.JButton();
        formPanel = new javax.swing.JPanel();
        adressLabel = new javax.swing.JLabel();
        addressField = new javax.swing.JTextField();
        portLabel = new javax.swing.JLabel();
        portField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();

        setSize(960, 720);
        setPreferredSize(new java.awt.Dimension(960, 720));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        leftPanel.setBackground(new java.awt.Color(0, 51, 255));
        leftPanel.setPreferredSize(new java.awt.Dimension(400, 623));
        leftPanel.setLayout(new java.awt.CardLayout());

        appName.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        appName.setForeground(new java.awt.Color(255, 255, 255));
        appName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        appName.setText("ChatApp");
        leftPanel.add(appName, "card2");

        add(leftPanel);

        exitButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        exitButton.setText("Exit");
        exitButton.setPreferredSize(new java.awt.Dimension(120, 40));

        loginButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        loginButton.setText("Login");
        loginButton.setPreferredSize(new java.awt.Dimension(120, 40));
        loginButton.addActionListener(frame);

        formPanel.setLayout(new java.awt.GridLayout(3, 2, -200, 20));

        adressLabel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        adressLabel.setText("Address:");
        formPanel.add(adressLabel);
        formPanel.add(addressField);

        portLabel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        portLabel.setText("Port:");
        formPanel.add(portLabel);
        formPanel.add(portField);

        nameLabel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        nameLabel.setText("Name:");
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(formPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(186, 186, 186)
                        .addComponent(loginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(106, 106, 106))
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(209, 209, 209)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        add(rightPanel);
    }  
}
