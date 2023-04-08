package guis.custom;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

import client.Message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

/**
 * The CustomMessageList class is used to display a messeges.
 * 
 * @author Arkadiusz Orzel
 */
public class CustomMessageList extends JList<Message> {

    private String currentUser;

    /**
     * The CustomMessageList constructor is a custom JList that displays the messages
     * in the chat window.
     *
     */
    public CustomMessageList() {
        setCellRenderer(new CustomMessageRenderer());
    }

    private class CustomMessageRenderer extends JPanel implements ListCellRenderer<Message> {

        private JLabel usernameLabel;
        private JTextArea messageArea;
        private JLabel timeLabel;

        /**
         * The CustomMessageRenderer constructor is a custom renderer for the JList that
         * displays messages.
         * It takes in a Message object and renders it to the screen with its username,
         * message, and time sent.
         *
         */
        public CustomMessageRenderer() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));
            usernameLabel = new JLabel();
            messageArea = new JTextArea();
            messageArea.setLineWrap(true);
            messageArea.setWrapStyleWord(true);
            messageArea.setEditable(false);
            messageArea.setFont(new Font("Arial", 1, 20));
            timeLabel = new JLabel();
            add(usernameLabel, BorderLayout.NORTH);
            add(messageArea, BorderLayout.CENTER);
            add(timeLabel, BorderLayout.SOUTH);
        }

        /**
         * The getListCellRendererComponent function is called every time a new message
         * needs to be displayed in the chat window.
         * It takes in a JList, which contains all of the messages that need to be
         * displayed, and an index for which message it
         * should display. The function then sets the usernameLabel text to whatever
         * user sent that particular message, and sets
         * the text of the messageArea (the area where messages are actually displayed)
         * to whatever was sent by that user.
         *
         *
         * @param list         Get the jlist that this renderer
         *                     is being used for
         * @param message      Get the username, message and time from the message
         *                     object
         * @param index        Determine the position of the message in the list
         * @param isSelected   Determine whether the cell is selected or not
         * @param cellHasFocus Determine if the cell is focused or not
         *
         * @return The component that is used to render each cell in the list
         *
         */
        @Override
        public Component getListCellRendererComponent(JList<? extends Message> list, Message message, int index,
                boolean isSelected, boolean cellHasFocus) {
            usernameLabel.setText(message.getUsername());
            messageArea.setText(message.getMessage());
            timeLabel.setText(message.getTime());
            if (message.getUsername().equals(currentUser)) {
                usernameLabel.setText("You");
                setAlignmentX(RIGHT_ALIGNMENT);
                setBackground(Color.WHITE);
            } else {
                setAlignmentX(LEFT_ALIGNMENT);
                setBackground(Color.WHITE);
            }
            return this;
        }
    }

    /**
     * The setCurrentUser function sets the current user to the username passed in
     * as a parameter.
     * 
     *
     * @param username The current user name
     *
     */
    public void setCurrentUser(String username) {
        this.currentUser = username;
    }
}