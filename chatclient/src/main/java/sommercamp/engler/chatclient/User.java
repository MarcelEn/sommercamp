package sommercamp.engler.chatclient;

import lombok.Getter;
import sommercamp.engler.modules.payloads.AddUserPayload;

@Getter
class User {
    private String username;
    private int id;

    User(AddUserPayload addUserPayload){
        username = addUserPayload.getUsername();
        id = addUserPayload.getId();
    }
}
