package sommercamp.engler.modules.payloads;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddUserPayload implements Payload {
    String username;
    int id;
}
