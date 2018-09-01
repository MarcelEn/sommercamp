package sommercamp.engler.chatserver;

import lombok.Getter;
import lombok.Setter;
import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.ActionJsonHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Getter
@Setter
public class ClientConnection extends Thread {
    private Socket socket;
    private IncomingMessageHandler incomingMessageHandler;
    private DataOutputStream outToClient;
    private User user = null;
    private int connectionId;
    private boolean isConnected;

    ClientConnection(Socket socket) {
        this.socket = socket;
        connectionId = ClientConnectionPool.getNextId();
        start();
    }

    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            incomingMessageHandler = new IncomingMessageHandler(this, inFromClient);
            incomingMessageHandler.start();
            outToClient = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(Action action) {
        try {
            outToClient.writeBytes(ActionJsonHandler.serialize(action) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void setUser(User user) {
        this.user = user;
    }

    int getConnectionId() {
        return connectionId;
    }

    void close() {
        ClientConnectionPool.removeById(connectionId);
    }

    void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }
}
