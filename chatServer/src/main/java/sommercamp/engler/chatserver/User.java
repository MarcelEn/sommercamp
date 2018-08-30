package sommercamp.engler.chatserver;

import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.ActionJsonHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class User extends Thread {
    private Socket socket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private User that = this;

    private Integer id = null;

    public User(Socket socket) {
        this.socket = socket;
        start();
    }

    public void run() {
        try {
            inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outToClient = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        startMessageHandler();
    }

    void sendMessage(Action action) {
        try {
            outToClient.writeBytes(ActionJsonHandler.serialize(action) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startMessageHandler() {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        handleIncomingAction(ActionJsonHandler.deserialize(inFromClient.readLine()));
                    } catch (IOException e) {
                        return;
                    }
                }
            }

            private void handleIncomingAction(Action action) {
                switch (action.getType()){
                    case REGISTER:
                        UserPool.registerNewUser(that, action);
                        break;
                    case LOGIN:
                    case SEND_MESSAGE:
                }
            }
        }.start();
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
