package sommercamp.engler.modules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sommercamp.engler.modules.payloads.AddMessagePayload;
import sommercamp.engler.modules.payloads.SendMessagePayload;

@Getter
@AllArgsConstructor
public class Message {
    private String content;
    private int senderId, targetId;
    private Integer id = null;

    public Message(SendMessagePayload sendMessagePayload){
        content = sendMessagePayload.getContent();
        senderId = sendMessagePayload.getSenderId();
        targetId = sendMessagePayload.getTargetId();
    }

    public Message(AddMessagePayload addMessagePayload){
        content = addMessagePayload.getContent();
        id = addMessagePayload.getId();
        senderId = addMessagePayload.getSenderId();
        targetId = addMessagePayload.getTargetId();
    }
}
