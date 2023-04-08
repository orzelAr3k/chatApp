package guis;

import guis.custom.CustomMessageList;
import guis.custom.CustomUserList;

public class MainChatPanel extends javax.swing.JPanel {

	private javax.swing.JScrollPane chatPane;
	private javax.swing.JPanel chatPanel;
	private javax.swing.JTextField messageField;
	private javax.swing.JButton sendButton;
	private javax.swing.JLabel userListLabel;
	private javax.swing.JScrollPane userScrollPane;
	private javax.swing.JPanel usersPanel;
	private CustomMessageList customMessagesList;
	private CustomUserList customUserList;

	public MainChatPanel() {
		usersPanel = new javax.swing.JPanel();
		userListLabel = new javax.swing.JLabel();
		userScrollPane = new javax.swing.JScrollPane();
		chatPanel = new javax.swing.JPanel();
		messageField = new javax.swing.JTextField();
		sendButton = new javax.swing.JButton();
		chatPane = new javax.swing.JScrollPane();
		customMessagesList = new CustomMessageList();
		customUserList = new CustomUserList();

		setSize(960, 720);
		setPreferredSize(new java.awt.Dimension(960, 720));
		setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

		usersPanel.setPreferredSize(new java.awt.Dimension(200, 735));
		usersPanel.setLayout(new java.awt.BorderLayout(0, 20));

		userListLabel.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
		userListLabel.setText("UserList:");
		usersPanel.add(userListLabel, java.awt.BorderLayout.PAGE_START);

		userScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		userScrollPane.setViewportView(customUserList);

		usersPanel.add(userScrollPane, java.awt.BorderLayout.CENTER);

		add(usersPanel);

		messageField.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		messageField.setHorizontalAlignment(javax.swing.JTextField.LEFT);

		sendButton.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
		sendButton.setText("Send");

		chatPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatPane.setViewportView(customMessagesList);

		javax.swing.GroupLayout chatPanelLayout = new javax.swing.GroupLayout(chatPanel);
		chatPanel.setLayout(chatPanelLayout);
		chatPanelLayout.setHorizontalGroup(
				chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chatPanelLayout
								.createSequentialGroup()
								.addGap(27, 27, 27)
								.addGroup(chatPanelLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(chatPane)
										.addGroup(chatPanelLayout
												.createSequentialGroup()
												.addComponent(messageField,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														613,
														Short.MAX_VALUE)
												.addGap(53, 53, 53)
												.addComponent(sendButton,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														126,
														Short.MAX_VALUE)))
								.addGap(36, 36, 36)));
		chatPanelLayout.setVerticalGroup(
				chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chatPanelLayout
								.createSequentialGroup()
								.addGap(38, 38, 38)
								.addComponent(chatPane,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										560,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(32, 32, 32)
								.addGroup(chatPanelLayout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(sendButton,
												javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												71, Short.MAX_VALUE)
										.addComponent(messageField,
												javax.swing.GroupLayout.Alignment.TRAILING))
								.addGap(34, 34, 34)));

		add(chatPanel);
	}

	/**
	 * The getUserList function returns the CustomUserList object that is used to
	 * display all of the users in a chatroom.
	 * 
	 * @return The CustomUserList variable
	 *
	 */
	public CustomUserList getUserList() {
		return this.customUserList;
	}

	/**
	 * The getMessageList function returns the customMessagesList object.
	 *
	 * @return A CustomMessageList object
	 *
	 */
	public CustomMessageList getMessageList() {
		return this.customMessagesList;
	}

	/**
	 * The getMessageField function returns the messageField field.
	 *
	 * @return The messagefield variable
	 *
	 */
	public javax.swing.JTextField getMessageField() {
		return this.messageField;
	}

	/**
	 * The getSendButton function returns the sendButton object.
	 * 
	 * @return The sendbutton
	 *
	 */
	public javax.swing.JButton getSendButton() {
		return this.sendButton;
	}
}
