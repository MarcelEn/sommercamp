package sommercamp.engler.simplechat.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public abstract class Server implements ConnectionInstance {

    private Scanner sc = new Scanner(System.in);

    private DataOutputStream outToClient;

    public Server() {
        int port = -1;
        while (port < 0 || port > 65535) {
            System.out.println("Please enter a valid Port");
            try {
                port = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
            }
        }

        try {
            createServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createServer(int port) throws IOException {
        ServerSocket welcomeSocket = new ServerSocket(port);
        System.out.println("listening on: " + port);
        final Server serverRef = this;


        final Socket connectionSocket = welcomeSocket.accept();
        new Thread() {
            public void run() {
                try {
                    new OnMessage(serverRef, connectionSocket);
                    outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void sendMessage(String content) {
        try {
            if (outToClient != null)
                outToClient.writeBytes(content + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract public void onMessage(String message);

    abstract public void onError(Exception e);
}
