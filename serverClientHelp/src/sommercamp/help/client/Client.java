package sommercamp.help.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread implements ClientMethods {

    private Socket clientSocket;
    private BufferedReader inFromServer;
    private DataOutputStream outToServer;
    private Consumer<String> onMessageConsumer;
    private Runnable onDisconnectRunnable;
    private Consumer<IOException> onError;

    public Client(String host, int port, Consumer<String> onMessageConsumer, Runnable onDisconnectRunnable, Consumer<IOException> onError) throws IOException {
        this.onMessageConsumer = onMessageConsumer;
        this.onDisconnectRunnable = onDisconnectRunnable;
        this.onError = onError;
        clientSocket = new Socket(host, port);
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        start();
    }

    @Override
    public void run() {
        super.run();
        while(true){
            try {
                String message = inFromServer.readLine();
                if(message == null){
                    onDisconnect();
                    return;
                }

                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        onMessage(message);
                    }
                }.start();
            } catch (IOException e) {
                if(e.getMessage().equals("Socket closed"))
                    onDisconnect();
                else
                    onError(e);

                return;
            }
        }
    }

    @Override
    public void onError(IOException e) {
        onError.accept(e);
    }

    @Override
    public void onMessage(String message) {
        onMessageConsumer.accept(message);
    }

    @Override
    public void onDisconnect() {
        onDisconnectRunnable.run();
    }

    public void send(String message) throws IOException {
        outToServer.writeBytes(message + "\n");
    }

    public void close() throws IOException {
        clientSocket.close();
    }
}
