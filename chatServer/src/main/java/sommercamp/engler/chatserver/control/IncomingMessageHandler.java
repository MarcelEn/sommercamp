package sommercamp.engler.chatserver.control;


import sommercamp.engler.chatserver.model.ClientConnection;
import sommercamp.engler.chatserver.service.UserService;
import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.ActionJsonHandler;

import java.io.BufferedReader;
import java.io.IOException;

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

                final String finalMessage = message;
                new Thread() {
                    public void run() {
                        try {
                            handleIncomingAction(ActionJsonHandler.deserialize(finalMessage));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

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
                UserService.registerNewUser(clientConnection, action);
                break;
            case LOGIN:
                UserService.loginUser(clientConnection, action);
                break;
            case SEND_MESSAGE:
                UserService.sendMessage(clientConnection, action);
                break;
        }
    }
}
