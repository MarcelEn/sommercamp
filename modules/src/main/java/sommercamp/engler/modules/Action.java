package sommercamp.engler.modules;

import lombok.Builder;
import lombok.Getter;
import sommercamp.engler.modules.payloads.Payload;

@Getter
@Builder
public class Action {
    private ActionTypes type;
    private Payload payload;

}
