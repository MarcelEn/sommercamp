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

    Socket clientSocket = null;
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    DataOutputStream outToServer;
    BufferedReader inFromServer;

    ChatClient chatClient;

    ConnectionInformation connectionInformation;

    Connection(ChatClient chatClient) {
        this.chatClient = chatClient;
        while (clientSocket == null){
            this.connectionInformation = new ConnectionInformation();
            try {
                clientSocket = new Socket(connectionInformation.getAddress(), connectionInformation.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            startProcess();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void startProcess() throws IOException {
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        startOnMessageThread();
    }

    public void sendMessage(String message) {
        try {
            outToServer.writeBytes(message + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startOnMessageThread() {
        new Thread() {
            public void run() {
                String message;
                while (true) {
                    try {
                        message = inFromServer.readLine();
                        if (message == null)
                            throw new Exception("d");
                        chatClient.onMessage(ActionJsonHandler.deserialize(message));
                    } catch (Exception e) {

                        chatClient.onServerDisconnect();
                        //if (!e.getMessage().equals("d"))
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }.start();
    }
}
