package sommercamp.engler.modules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sommercamp.engler.modules.payloads.AddMessagePayload;
import sommercamp.engler.modules.payloads.SendMessagePayload;

@Getter
@AllArgsConstructor
public class Message {
    private String content;
    private int targetId;
    private Integer senderId, id = null;

    public Message(SendMessagePayload sendMessagePayload){
        content = sendMessagePayload.getContent();
        targetId = sendMessagePayload.getTargetId();
    }

    public Message(AddMessagePayload addMessagePayload){
        content = addMessagePayload.getContent();
        id = addMessagePayload.getId();
        senderId = addMessagePayload.getSenderId();
        targetId = addMessagePayload.getTargetId();
    }
}
