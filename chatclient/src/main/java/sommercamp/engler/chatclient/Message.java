package sommercamp.engler.chatclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sommercamp.engler.modules.payloads.AddMessagePayload;

@Getter
@AllArgsConstructor
public class Message {
    private String content;
    private int id, senderId, targetId;

    Message(AddMessagePayload addMessagePayload){
        content = addMessagePayload.getContent();
        id = addMessagePayload.getId();
        senderId = addMessagePayload.getSenderId();
        targetId = addMessagePayload.getTargetId();
    }
}
