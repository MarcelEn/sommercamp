package sommercamp.engler.chatserver;

import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.ActionJsonHandler;

import java.io.BufferedReader;

public class IncomingMessageHandler extends Thread {

    private BufferedReader inFromClient;
    private ClientConnection clientConnection;

    public IncomingMessageHandler(ClientConnection clientConnection, BufferedReader inFromClient) {
        this.clientConnection = clientConnection;
        this.inFromClient = inFromClient;
    }

    public void run() {
        String message;
        while (true) {
            try {
                message = inFromClient.readLine();
                if (message == null)
                    throw new Exception("d");
                handleIncomingAction(ActionJsonHandler.deserialize(message));
            } catch (Exception e) {
                clientConnection.close();
                if (!e.getMessage().equals("d"))
                    e.printStackTrace();
                return;
            }
        }
    }

    private void handleIncomingAction(Action action) {
        switch (action.getType()) {
            case REGISTER:
                UserPool.registerNewUser(clientConnection, action);
                break;
            case LOGIN:
                UserPool.loginUser(clientConnection, action);
                break;
            case SEND_MESSAGE:
        }
    }
}
