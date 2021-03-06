package sommercamp.engler.chatserver.gateway;

import sommercamp.engler.chatserver.model.ClientConnection;
import sommercamp.engler.chatserver.model.User;
import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.ActionTypes;
import sommercamp.engler.modules.payloads.*;

public class Sender {
    public static void sendAccessKeyIsNotValid(ClientConnection clientConnection) {
        Action showAccessKeyAction = new Action( //
                ActionTypes.ACCESS_KEY_IS_NOT_VALID, //
                new BlankPayload());
        clientConnection.sendMessage(showAccessKeyAction);
    }

    public static void sendAddUserInformation(User user, ClientConnection clientConnection) {
        Action showAccessKeyAction = new Action( //
                ActionTypes.ADD_USER, //
                new AddUserPayload(user.getUsername(), user.getId()));
        clientConnection.sendMessage(showAccessKeyAction);
    }

    public static void sendLoginSuccess(ClientConnection clientConnection) {
        Action loginSuccessAction = new Action( //
                ActionTypes.LOGIN_SUCCESS, //
                new LoginSuccessPayload(clientConnection.getUser().getId()));
        clientConnection.sendMessage(loginSuccessAction);
    }

    public static void sendUserNameInUser(ClientConnection clientConnection) {
        Action showAccessKeyAction = new Action( //
                ActionTypes.USERNAME_ALREADY_IN_USE, //
                new BlankPayload());
        clientConnection.sendMessage(showAccessKeyAction);
    }

    public static void sendShowAccessKey(ClientConnection clientConnection, String accessKeyWithUsername) {
        Action showAccessKeyAction = new Action( //
                ActionTypes.SHOW_ACCESS_KEY, //
                new ShowAccessKeyPayload(accessKeyWithUsername));

        clientConnection.sendMessage(showAccessKeyAction);
    }

    public static void sendAddMessage(ClientConnection clientConnection, AddMessagePayload addMessagePayload) {
        Action addMessageAction = new Action( //
                ActionTypes.ADD_MESSAGE, //
                addMessagePayload);

        clientConnection.sendMessage(addMessageAction);
    }
}
