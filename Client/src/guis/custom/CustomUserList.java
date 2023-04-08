package guis.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import client.User;

/**
 * The CustomUserList class is use to display a list of user.
 * 
 * @author Arkadiusz Orzel
 */
public class CustomUserList extends JList<User> {

    /**
     * The CustomUserList constructor is used to displays the users in the
     * chatroom.
     * It extends JList and implements ListCellRenderer to customize how each user
     * is displayed.
     *
     */
    public CustomUserList() {
        super(new DefaultListModel<>());
        setCellRenderer(new CustomCellRenderer());
    }

    /**
     * The addUserIfNotExists function adds a user to the list if they are not
     * already in it.
     *
     * @param username Add a new user to the list
     *
     */
    public void addUserIfNotExists(String username) {
        DefaultListModel<User> model = (DefaultListModel<User>) getModel();
        for (int i = 0; i < model.getSize(); i++) {
            User user = model.getElementAt(i);
            if (user.getUsername().equals(username)) {
                return;
            }
        }
        model.addElement(new User(username));
        repaint();
    }

    /**
     * The setUserUnread function is used to set the unread status of a user in the
     * JList.
     * 
     * @param username Find the user in the list model
     * @param unread   Set the user's unread status
     *
     */
    public void setUserUnread(String username, boolean unread) {
        ListModel<User> model = getModel();
        for (int i = 0; i < model.getSize(); i++) {
            User user = model.getElementAt(i);
            if (user.getUsername().equals(username)) {
                user.setUnread(unread);
                break;
            }
        }
        repaint();
    }

    /**
     * The removeUser function removes a user from the list of users.
     * 
     * @param username Identify which user to remove from the list
     *
     */
    public void removeUser(String username) {
        DefaultListModel<User> model = (DefaultListModel<User>) getModel();
        for (int i = 0; i < model.getSize(); i++) {
            User user = model.getElementAt(i);
            if (user.getUsername().equals(username)) {
                model.removeElementAt(i);
                break;
            }
        }
        repaint();
    }

    private class CustomCellRenderer extends JLabel implements ListCellRenderer<User> {

        private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        /**
         * The getListCellRendererComponent function is called every time a cell in the
         * list needs to be rendered.
         * The function returns a component that will be used as the renderer for that
         * particular cell.
         * 
         * 
         *
         * @param list         Get the list that is being rendered
         * @param value        Get the username and unread messages from the user
         *                     object
         * @param index        Get the index of the item in the list
         * @param isSelected   Determine whether the cell is selected or not
         * @param cellHasFocus Determine whether the cell has focus or not
         *
         */
        @Override
        public Component getListCellRendererComponent(JList<? extends User> list, User value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value,
                    index, isSelected, cellHasFocus);

            User user = value;
            String name = user.getUsername();
            boolean unread = user.hasUnreadMessages();
            renderer.setFont(new Font("Arial", 1, 20));

            renderer.setText(name);

            if (unread) {
                renderer.setIcon(new CustomDotIcon(10, Color.BLUE));
                renderer.setIconTextGap(10);
            } else {
                renderer.setIcon(null);
            }

            return renderer;
        }
    }
}