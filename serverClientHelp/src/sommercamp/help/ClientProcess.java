package sommercamp.help;

import sommercamp.help.client.Client;
import sommercamp.help.client.ClientMethods;

import java.io.IOException;
import java.util.Scanner;

public class ClientProcess implements ClientMethods {
    private Client client;
    private Scanner sc = new Scanner(System.in);
    ClientProcess(){
        try {
            client = new Client("localhost", 8080, this::onMessage, this::onDisconnect, this::onError);
            client.send(sc.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(IOException e) {
        e.printStackTrace();
    }

    @Override
    public void onMessage(String message) {
        System.out.println("FROM SERVER: " + message);
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisconnect() {

    }
}
