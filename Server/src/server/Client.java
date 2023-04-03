package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.UUID;

/**
 * The Client class which stores user variables and allows communication by giving stream interface.
 * 
 * @author Arkadiusz Orzel
 */
public class Client {
    private final String clientId;
    private final PrintStream streamOut;
    private final InputStream streamIn;
    private final String username;
    private final Socket client;

    /**
     * The Client constructor takes in a Socket object and a String username,
     * and creates an instance of the Client class with those parameters. The
     * function also initializes two PrintStreams
     * (streamOut) and one InputStream (streamIn). These streams are used to send
     * data to/from the client's socket.
     *
     * @param client Get the input and output streams from the client
     * @param username Set the username of the client
     * @throws IOException If a problem occurs
     * 
     */
    public Client(Socket client, String username) throws IOException {
        this.clientId = UUID.randomUUID().toString();
        this.client = client;
        // v - character stream v - byte stream
        this.streamOut = new PrintStream(this.client.getOutputStream());
        this.streamIn = this.client.getInputStream();
        this.username = username;
    }

    /**
     * The getClientId function returns the clientId of the current instance of
     * Client.
     *
     * @return The clientid of the current object
     * 
     */
    public String getClientId() {
        return this.clientId;
    }

    /**
     * The getOutStream function returns the PrintStream object that is used to send
     * data to the server.
     * 
     *
     * @return The output stream of the client
     *
     */
    public PrintStream getOutStream() {
        return this.streamOut;
    }

    /**
     * The getInputStream function returns the input stream of the client.
     *
     * @return The input stream of the client
     *
     */
    public InputStream getInputStream() {
        return this.streamIn;
    }

    /**
     * The isSocketClosed function checks to see if the socket is closed.
     *
     * @return A boolean that socket is closed
     *
     */
    public boolean isSocketClosed() {
        return this.client.isClosed();
    }

    /**
     * The getUserName function returns the username of the client.
     *
     * @return The username of the client
     *
     */
    public String getUserName() {
        return this.username;
    }
}
