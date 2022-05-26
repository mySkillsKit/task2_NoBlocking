
public class Main {
    public static void main(String[] args) {

        Server server = new Server();
        Client client = new Client();

        Thread serverThread = new Thread(server);
        Thread clientThread = new Thread(client);

        serverThread.start();
        clientThread.start();


    }
}
