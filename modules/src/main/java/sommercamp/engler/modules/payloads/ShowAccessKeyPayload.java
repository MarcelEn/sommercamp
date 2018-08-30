package sommercamp.engler.modules.payloads;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShowAccessKeyPayload implements Payload{
    String accessKey;
}
