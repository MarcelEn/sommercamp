package sommercamp.engler.modules.payloads;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendMessagePayload implements Payload{
    int senderId, targetId;
    String content;
}
