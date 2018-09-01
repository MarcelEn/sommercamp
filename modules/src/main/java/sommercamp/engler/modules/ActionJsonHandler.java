package sommercamp.engler.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import sommercamp.engler.modules.payloads.*;
import sommercamp.engler.modules.payloads.RegisterPayload;

import java.io.IOException;

import static sommercamp.engler.modules.ActionTypes.*;

public class ActionJsonHandler {
    private static ObjectMapper mapper = createMapper();

    private static ObjectMapper createMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

    public static Action deserialize(String json) throws IOException {
        ObjectNode node = mapper.readValue(json, ObjectNode.class);

        if (!node.has("type"))
            throw new IOException("type is not defined");

        if (!node.has("payload"))
            throw new IOException("payload is not defined");

        switch (ActionTypes.valueOf(node.get("type").asText())) {
            case REGISTER:
                return createAction(REGISTER, RegisterPayload.class, node);
            case LOGIN:
                return createAction(LOGIN, LoginPayload.class, node);
            case SEND_MESSAGE:
                return createAction(SEND_MESSAGE, SendMessagePayload.class, node);
            case SHOW_ACCESS_KEY:
                return createAction(SHOW_ACCESS_KEY, ShowAccessKeyPayload.class, node);
            case ADD_USER:
                return createAction(ADD_USER, AddUserPayload.class, node);
            case REMOVE_USER:
                return createAction(REMOVE_USER, RemoveUserPayload.class, node);
            case ADD_MESSAGE:
                return createAction(ADD_MESSAGE, AddMessagePayload.class, node);
            case ACCESS_KEY_IS_NOT_VALID:
                return createAction(ACCESS_KEY_IS_NOT_VALID, BlankPayload.class, node);
            case USERNAME_ALREADY_IN_USE:
                return createAction(USERNAME_ALREADY_IN_USE, BlankPayload.class, node);
            case LOGIN_SUCCESS:
                return createAction(LOGIN_SUCCESS, LoginSuccessPayload.class, node);

        }

        throw new IOException("invalid Payload Type");
    }

    private static Action createAction(ActionTypes actionType, Class payloadClass, ObjectNode node) throws IOException {
        return new Action(
                actionType,
                (Payload) mapper.readValue(node.get("payload").toString(), payloadClass)
        );
    }

    public static String serialize(Action action) throws IOException {
        return mapper.writeValueAsString(action);
    }
}
