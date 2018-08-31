package sommercamp.engler.modules.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddMessagePayload implements Payload {
    private String content;
    private int id, senderId, targetId;
}
