package sommercamp.help.server;

import java.io.IOException;

public interface ClientConnectionMethods {
    void onMessage(ClientConnection clientConnection, String message);

    void onClientError(IOException e);
    void onDisconnect(ClientConnection clientConnection);
}
