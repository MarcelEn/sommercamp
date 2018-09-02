package sommercamp.engler.simplechat.boundary;

import sommercamp.engler.simplechat.control.RemoteConnectionClient;
import sommercamp.engler.simplechat.control.RemoteConnectionServer;
import sommercamp.engler.simplechat.service.Client;
import sommercamp.engler.simplechat.service.ConnectionInstance;

import java.util.Scanner;

public class InputConsole extends Client {

    private Scanner sc = new Scanner(System.in);

    private ConnectionInstance peerConnection;

    private String myName, peerName = null;

    public InputConsole() {
        super();
        String userInput = "";

        System.out.println("What is your name?");

        myName = sc.nextLine();

        while (!(userInput.equals("1") || userInput.equals("2"))) {
            System.out.println("call or wait for someone? (1/2)");
            userInput = sc.nextLine();
        }

        if (userInput.equals("1")) {
            assignPeerConnectionClient();
        } else {
            assignPeerConnectionServer();
        }

        peerConnection.sendMessage(myName);
        startChatProcess();
    }

    private void startChatProcess() {
        String userInput;
        while (true) {
            userInput = sc.nextLine();
            sendMessage(myName + ":\n" + userInput + "\n");
            peerConnection.sendMessage(userInput);
        }
    }

    private void onPeerConnectionMessage(String message) {
        if (peerName == null) {
            peerName = message;
            return;
        }
        sendMessage(peerName + ":\n" + message + "\n");
    }

    private void onPeerConnectionException(Exception e) {
        e.printStackTrace();
    }


    private void assignPeerConnectionServer() {
        peerConnection = new RemoteConnectionServer() {
            public void onMessage(String message) {
                onPeerConnectionMessage(message);
            }

            public void onError(Exception e) {
                onPeerConnectionException(e);
            }
        };
    }

    private void assignPeerConnectionClient() {
        peerConnection = new RemoteConnectionClient() {
            public void onMessage(String message) {
                onPeerConnectionMessage(message);
            }

            public void onError(Exception e) {
                onPeerConnectionException(e);
            }
        };
    }

    public void onMessage(String message) {
    }

    public void onError(Exception e) {
        e.printStackTrace();
    }
}
