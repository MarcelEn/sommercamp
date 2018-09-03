package sommercamp.engler.simplechat.control;

import sommercamp.engler.simplechat.service.Server;

import java.util.function.Consumer;

public class RemoteConnectionServer extends Server {

    private Consumer<String> onMessage;
    private Consumer<Exception> onError;
    public RemoteConnectionServer(Consumer<String> onMessage, Consumer<Exception> onError){
        super();
        this.onMessage = onMessage;
        this.onError = onError;
    }

    public void onMessage(String message) {
        onMessage.accept(message);
    }

    public void onError(Exception e) {
        onError.accept(e);
    }
}
