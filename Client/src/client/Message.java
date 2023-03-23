package client;

class IMessage {
    private String username;
    private String message;
    private String time;

    public IMessage(String username, String message, String time) {
        this.username = username;
        this.message = message;
        this.time = time;
    }

    void setUsername(String username) { this.username = username; }
    String getUsername() { return this.username; }

    void setMessage(String message) { this.message = message; }
    String getMessage() { return this.message; }

    void setTime(String time) { this.time = time; }
    String getTime() { return this.time; }


}

public class Message extends IMessage {
    public Message(String username, String message, String time) {
        super(username, message, time);
    }
}
