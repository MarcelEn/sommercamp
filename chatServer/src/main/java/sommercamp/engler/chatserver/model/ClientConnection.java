package sommercamp.engler.chatserver.model;

import lombok.Getter;
import lombok.Setter;
import sommercamp.engler.chatserver.control.IncomingMessageHandler;
import sommercamp.engler.chatserver.service.UserService;
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
    private boolean isConnected;

    private int connectionIdCounter = 0;

    public ClientConnection(Socket socket) {
        this.socket = socket;
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

    public void sendMessage(Action action) {
        try {
            outToClient.writeBytes(ActionJsonHandler.serialize(action) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void close() {
        UserService.setConnectionToNull(user);
    }
}
