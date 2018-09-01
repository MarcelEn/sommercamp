package sommercamp.engler.chatclient;

import lombok.Getter;
import sommercamp.engler.modules.model.Message;
import sommercamp.engler.modules.payloads.AddUserPayload;

import java.util.ArrayList;

@Getter
public class User {
    private String username;
    private int id;

    User(AddUserPayload addUserPayload){
        username = addUserPayload.getUsername();
        id = addUserPayload.getId();
    }
}
