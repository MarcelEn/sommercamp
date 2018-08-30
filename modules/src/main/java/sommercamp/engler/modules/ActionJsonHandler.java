package sommercamp.engler.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ActionJsonHandler {
    private static ObjectMapper mapper = new ObjectMapper();

    public static Action deserialize(String json) throws IOException {
        return mapper.readValue(json, Action.class);
    }

    public static String serialize(Action action) throws IOException {
        return mapper.writeValueAsString(action);
    }
}
