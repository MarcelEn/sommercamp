package sommercamp.engler.modules.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddUserPayload implements Payload {
    String username;
    int id;
}
