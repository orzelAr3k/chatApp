package client;

/**
 * The User class represent a user for a list of users.
 * 
 * @author Arkadiusz Orzel
 */
public class User {
    private String username;
    private boolean unread;

    /**
     * The User function is a constructor for the User class.
     * It takes in a username and sets it to the instance variable username.
     * It also sets unread to false, as there are no messages yet.
     * 
     * @param username Set the username of the user
     *
     * 
     */
    public User(String username) {
        this.username = username;
        this.unread = false;
    }

    /**
     * The getUsername function returns the username of the user.
     * 
     * @return The username of the user
     *
     */
    public String getUsername() {
        return username;
    }

    /**
     * The hasUnreadMessages function returns a boolean value indicating whether or
     * not the user has unread messages.
     * 
     * @return A boolean value
     *
     */
    public boolean hasUnreadMessages() {
        return unread;
    }

    /**
     * The setUnread function sets the unread variable to true or false.
     * 
     *
     * @param unread Set the unread variable to true or false
     *
     */
    public void setUnread(boolean unread) {
        this.unread = unread;
    }
}
