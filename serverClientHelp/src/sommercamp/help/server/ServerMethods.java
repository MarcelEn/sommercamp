package sommercamp.help.server;

import java.io.IOException;
import java.net.Socket;

public interface ServerMethods {
    void onServerError(IOException e);
    void onConnection(Socket clientSocket);
}
