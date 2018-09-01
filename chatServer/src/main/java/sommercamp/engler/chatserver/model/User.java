package sommercamp.engler.chatserver.model;

import lombok.Getter;
import lombok.Setter;
import sommercamp.engler.chatserver.data.UserPool;

@Getter
@Setter
public
class User {
    private String accessKey, username;
    private ClientConnection clientConnection;
    private int id;

    public User(String accessKey, String username, ClientConnection clientConnection) {
        this.accessKey = accessKey;
        this.username = username;
        this.clientConnection = clientConnection;
    }
}
