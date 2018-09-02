package sommercamp.engler.simplechat.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class OnMessage extends Thread {

    private BufferedReader reader;
    private ConnectionInstance ref;

    public OnMessage(ConnectionInstance ref, Socket connectionSocket) {
        this.ref = ref;
        try {
            reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        while(true){
            final String message;
            try {
                message = reader.readLine();
                new Thread() {
                    public void run() {
                        ref.onMessage(message);
                    }
                }.start();
            } catch (IOException e) {
                ref.onError(e);
                return;
            }
        }
    }
}
