package sommercamp.engler.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    Server(int port) {
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
            Socket connectionSocket = welcomeSocket.accept();
            ClientConnectionPool.addConnection(new ClientConnection(connectionSocket));
        }
    }
}
