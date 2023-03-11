import client.ClientSocket;
import utils.Property;

public class App {

    final static Property clientProperties = new Property("./client.properties");
    public static void main(String[] args) throws Exception {
        ClientSocket socketClient = new ClientSocket(clientProperties.getProperty("host"), Integer.parseInt(clientProperties.getProperty("port")));
        socketClient.connectToServer("Grzegorz");
    }
}
