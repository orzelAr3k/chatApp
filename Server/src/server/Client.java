package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
    private static int userId = 0;
    private final PrintStream streamOut;
    private final InputStream streamIn;
    private final String username;
    private final Socket client;

    public Client(Socket client, String username) throws IOException {
        this.client = client;
        //                      v - character stream      v - byte stream
        this.streamOut = new PrintStream(this.client.getOutputStream());
        this.streamIn = this.client.getInputStream();
        this.username = username;
        userId += 1;
    }

    
    /** 
     * @return PrintStream
     */
    public PrintStream getOutStream() {
        return this.streamOut;
    }

    
    /** 
     * @return InputStream
     */
    public InputStream getInputStream() {
        return this.streamIn;
    }

    
    /** 
     * @return boolean
     */
    public boolean isSocketClosed() {
        return this.client.isClosed();
    }

    
    /** 
     * @return String
     */
    public String getUserName() {
        return this.username;
    }
}
