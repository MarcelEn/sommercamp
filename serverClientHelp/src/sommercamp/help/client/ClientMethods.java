package sommercamp.help.client;

import java.io.IOException;

public interface ClientMethods {
    void onError(IOException e);
    void onMessage(String message);
    void onDisconnect();
}
