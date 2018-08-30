package sommercamp.engler.modules.payloads;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginPayload implements Payload{
    String accessKey;
}
