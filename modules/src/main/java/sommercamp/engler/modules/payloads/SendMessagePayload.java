package sommercamp.engler.modules.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SendMessagePayload implements Payload{
    int senderId, targetId;
    String content;
}
