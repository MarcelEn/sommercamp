package sommercamp.engler.modules.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SendMessagePayload implements Payload {
    private String content;
    private int senderId, targetId;
}
