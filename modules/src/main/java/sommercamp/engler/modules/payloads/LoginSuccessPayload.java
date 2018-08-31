package sommercamp.engler.modules.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginSuccessPayload implements Payload {
    int id;
}
