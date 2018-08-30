package sommercamp.engler.chatclient;

import lombok.Getter;
import sommercamp.engler.modules.ActionJsonHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Getter
public class Connection {

    Socket clientSocket;
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    DataOutputStream outToServer;
    BufferedReader inFromServer;

    ChatClient chatClient;

    ConnectionInformation connectionInformation;

    boolean connected = false;

    Connection(ChatClient chatClient) {
        this.chatClient = chatClient;
        try {
            createConnection(new ConnectionInformation());
        } catch (IOException e) {
            e.printStackTrace();
        }
        connected = true;
    }

    private void createConnection(ConnectionInformation connectionInformation) throws IOException {
        this.connectionInformation = connectionInformation;
        clientSocket = new Socket(connectionInformation.getAddress(), connectionInformation.getPort());
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        startOnMessageThread();
    }

    public void sendMessage(String message){
        try {
            outToServer.writeBytes(message + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startOnMessageThread() {
        new Thread() {
            public void run() {
                while(connected) {
                    try {
                        chatClient.onMessage(
                                ActionJsonHandler.deserialize(inFromServer.readLine())
                        );
                    } catch (Exception e) {
                        connected = false;
                        chatClient.onServerDisconnect();
                    }
                }
            }
        }.start();
    }
}
