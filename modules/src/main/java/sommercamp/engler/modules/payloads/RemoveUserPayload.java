package sommercamp.engler.modules.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RemoveUserPayload implements Payload {
    int id;
}
