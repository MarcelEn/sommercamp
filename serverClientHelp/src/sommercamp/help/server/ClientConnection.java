package sommercamp.help.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ClientConnection extends Thread implements ClientConnectionMethods {
    private BiConsumer<ClientConnection, String> onMessageConsumer;

    private BufferedReader inFromClient;
    private DataOutputStream outToClient;

    private static int idCounter;
    private int id;
    private Consumer<ClientConnection> onDisconnectConsumer;

    public ClientConnection(Socket clientSocket, //
                            BiConsumer<ClientConnection, String> onMessageConsumer, //
                            Consumer<ClientConnection> onDisconnectConsumer //
    ) throws IOException {

        id = idCounter++;
        this.onDisconnectConsumer = onDisconnectConsumer;
        this.onMessageConsumer = onMessageConsumer;
        inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outToClient = new DataOutputStream(clientSocket.getOutputStream());
        start();

    }

    @Override
    public void run() {
        super.run();
        ClientConnection that = this;
        while (true) try {
            String message = inFromClient.readLine();
            if (message == null) {
                onDisconnect(this);
                return;
            }
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    onMessage(that, message);
                }
            }.start();

        } catch (IOException e) {
            onClientError(e);
        }
    }

    @Override
    public void onMessage(ClientConnection clientConnection, String message) {
        onMessageConsumer.accept(clientConnection, message);
    }

    @Override
    public void onClientError(IOException e) {
        e.printStackTrace();
        onDisconnect(this);
    }

    @Override
    public void onDisconnect(ClientConnection clientConnection) {
        onDisconnectConsumer.accept(clientConnection);
    }


    public int getConnectionId() {
        return id;
    }

    public void send(String message) throws IOException {
        outToClient.writeBytes(message + "\n");
    }
}
