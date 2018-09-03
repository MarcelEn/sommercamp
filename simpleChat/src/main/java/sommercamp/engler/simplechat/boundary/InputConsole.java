package sommercamp.engler.simplechat.boundary;

import sommercamp.engler.simplechat.control.RemoteConnectionClient;
import sommercamp.engler.simplechat.control.RemoteConnectionServer;
import sommercamp.engler.simplechat.service.Client;
import sommercamp.engler.simplechat.service.ConnectionInstance;

import java.util.Scanner;
import java.util.function.Consumer;

public class InputConsole extends Client {

    private Scanner sc = new Scanner(System.in);

    private ConnectionInstance peerConnection;

    private String myName, peerName = null;

    private Consumer<String> onPeerMessage = this::onPeerConnectionMessage;
    private Consumer<Exception> onPeerException = this::onPeerConnectionException;

    boolean informedPeerAboutMyName = false;

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
            peerConnection = new RemoteConnectionClient(onPeerMessage, onPeerException);
            peerConnection.sendMessage(myName);
            informedPeerAboutMyName = true;
        } else {
            peerConnection = new RemoteConnectionServer(onPeerMessage, onPeerException);
        }

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
            if(!informedPeerAboutMyName){
                peerConnection.sendMessage(myName);
                informedPeerAboutMyName = true;
            }
            return;
        }
        sendMessage(peerName + ":\n" + message + "\n");
    }

    private void onPeerConnectionException(Exception e) {
        e.printStackTrace();
    }


    public void onMessage(String message) {
    }

    public void onError(Exception e) {
        e.printStackTrace();
    }
}
