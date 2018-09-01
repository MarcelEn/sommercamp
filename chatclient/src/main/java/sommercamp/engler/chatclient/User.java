package sommercamp.engler.chatclient;

import lombok.Getter;
import sommercamp.engler.modules.model.Message;
import sommercamp.engler.modules.payloads.AddUserPayload;

import java.util.ArrayList;

@Getter
public class User {
    private String username;
    private int id;
    ArrayList<Message> messages = new ArrayList<Message>();

    User(AddUserPayload addUserPayload){
        username = addUserPayload.getUsername();
        id = addUserPayload.getId();
    }

    public synchronized void addMessage(Message message) {
        messages.add(message);
    }
}
