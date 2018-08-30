package sommercamp.engler.chatserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(8080);

        while (true)

        {
            Socket connectionSocket = welcomeSocket.accept();
            UserPool.addUser(new User(connectionSocket));

        }
    }
}
