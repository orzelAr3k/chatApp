package client;

/**
 * The IMessage class contains basic filed of message.
 * 
 * @author Arkadiusz Orzel
 */
class IMessage {
    private String username;
    private String message;
    private String time;

    
    /**
     * The IMessage constructor which takes in three parameters:
     * username, message, and time. It then sets the values of these parameters to 
     * the corresponding instance variables. This function is used to create an IMessage
     * object with specific values for each parameter.
     *
     * @param username Set the username of the message
     * @param message Set the message variable
     * @param time Set the time of the message
     *
     */
    public IMessage(String username, String message, String time) {
        this.username = username;
        this.message = message;
        this.time = time;
    }

    
    /**
     * The setUsername function sets the username of a user.
     * 
     * @param username Set the username variable
     *
     */
    void setUsername(String username) { this.username = username; }
    
    /**
     * The getUsername function returns the username field.
     * 
     * @return The username of the user
     *
     */
    String getUsername() { return this.username; }

    
    /**
     * The setMessage function sets the message field.
     * 
     * @param message Set the message field
     *
     */
    void setMessage(String message) { this.message = message; }
    

    
    /**
     * The getMessage function returns the message field.
     * 
     * @return The value of the message
     *
     */
    String getMessage() { return this.message; }

    
    /**
     * The setTime function sets the time field.
     * 
     * @param time Set the time
     *
     */
    void setTime(String time) { this.time = time; }
    
    /**
     * The getTime function returns the time field.
     * 
     * @return The time value
     *
     */
    String getTime() { return this.time; }


}

/**
 * The Message class is the main message model that contains basic information and can be extended with additional information.
 * 
 * @author Arkadiusz Orzel
 */
public class Message extends IMessage {
    
    /**
     * The Message function is a constructor for the Message class.
     * It takes in three parameters: username, message, and time.
     * The function then sets the values of these parameters to their respective variables in the superclass (IMessage).
     
     *
     * @param username Set the username of the message
     * @param message Set the message of the message object
     * @param time Set the time of the message
     *
     */
    public Message(String username, String message, String time) {
        super(username, message, time);
    }
}
