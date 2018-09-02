package sommercamp.engler.simplechat.service;

import lombok.Getter;
import sommercamp.engler.simplechat.control.ConnectionInformation;
import sommercamp.engler.simplechat.service.ConnectionInstance;
import sommercamp.engler.simplechat.service.OnMessage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


@Getter
public abstract class Client implements ConnectionInstance {

    Socket clientSocket = null;
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    DataOutputStream outToServer;
    BufferedReader inFromServer;

    public Client() {
        ConnectionInformation connectionInformation;
        while (clientSocket == null) {
            connectionInformation = new ConnectionInformation();
            try {
                this.clientSocket = new Socket(connectionInformation.getAddress(), connectionInformation.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            assignHandlers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void assignHandlers() throws IOException {
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        new OnMessage(this, clientSocket);
    }

    public void sendMessage(String message) {
        try {
            outToServer.writeBytes(message + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    abstract public void onMessage(String message);

    abstract public void onError(Exception e);
}
