import server.Server;
import utils.Property;

public class App {

    private static final Property serverProperites = new Property("./server.properties");
    public static void main(String[] args) throws Exception {
        Server server = new Server(Integer.parseInt(serverProperites.getProperty("port")));
        server.startSocketServer();
    }
}
