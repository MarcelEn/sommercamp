package sommercamp.engler.chatserver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class User {
    private String accessKey, username;
    private ClientConnection clientConnection;
    private int id;
    User(String accessKey, String username, ClientConnection clientConnection) {
        this.accessKey = accessKey;
        this.username = username;
        this.clientConnection = clientConnection;
        this.id = UserPool.getNextId();
    }
}
