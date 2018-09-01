package sommercamp.engler.chatserver.boundary;

import sommercamp.engler.chatserver.model.ClientConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public Server(int port) {
        try {
            createServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createServer(int port) throws IOException {
        ServerSocket welcomeSocket = new ServerSocket(port);

        while (true)
        {
            final Socket connectionSocket = welcomeSocket.accept();
            new Thread() {
                public void run() {
                    new ClientConnection(connectionSocket);
                }
            }.start();
        }
    }
}
