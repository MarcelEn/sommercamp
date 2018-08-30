package sommercamp.engler.chatclient;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.ActionTypes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Getter
public class Connection {

    private Socket socket = null;

    Socket clientSocket;
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    DataOutputStream outToServer;
    BufferedReader inFromServer;

    ChatClient chatClient;

    ConnectionInformation connectionInformation;

    Connection(ChatClient chatClient) {
        this.chatClient = chatClient;
        try {
            createConnection(new ConnectionInformation());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                while(clientSocket.isConnected()) {
                    try {
                        chatClient.onMessage(
                                deserialize(inFromServer.readLine())
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    private Action deserialize(String json) throws IOException {
        ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
        if(!node.has("type"))
            throw new IOException("type not defined");

        switch (node.get("type").asText()){

        }

        // mapper.readValue(deserialize(inFromServer.readLine()), Action.class)

    }
}
