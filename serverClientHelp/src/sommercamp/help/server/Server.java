package sommercamp.help.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server extends Thread implements ServerMethods {

    private ServerSocket socket;

    private Consumer<IOException> onErrorConsumer;
    private Consumer<Socket> onConnectionConsumer;

    public Server(int port, Consumer<Socket> onConnectionConsumer, Consumer<IOException> onErrorConsumer) throws IOException {
        socket = new ServerSocket(port);
        this.onConnectionConsumer = onConnectionConsumer;
        this.onErrorConsumer = onErrorConsumer;
        start();
    }

    @Override
    public void run() {
        super.run();
        while(true){
            try {
                Socket clientSocket = socket.accept();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        onConnection(clientSocket);
                    }
                }.start();
            } catch (IOException e) {
                onServerError(e);
            }
        }
    }

    @Override
    public void onServerError(IOException e) {
        onErrorConsumer.accept(e);
    }

    @Override
    public void onConnection(Socket clientSocket) {
        onConnectionConsumer.accept(clientSocket);
    }
}
