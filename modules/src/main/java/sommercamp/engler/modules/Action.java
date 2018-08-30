package sommercamp.engler.modules;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sommercamp.engler.modules.payloads.Payload;

@Getter
@Setter
@AllArgsConstructor
public class Action {
    private ActionTypes type;
    private Payload payload;

}
