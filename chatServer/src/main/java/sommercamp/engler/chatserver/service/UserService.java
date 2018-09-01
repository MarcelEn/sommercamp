package sommercamp.engler.chatserver.service;

import sommercamp.engler.chatserver.data.PoolAction;
import sommercamp.engler.chatserver.data.UserPool;
import sommercamp.engler.chatserver.gateway.Sender;
import sommercamp.engler.chatserver.model.ClientConnection;
import sommercamp.engler.chatserver.model.User;
import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.payloads.AddMessagePayload;
import sommercamp.engler.modules.payloads.SendMessagePayload;

import java.util.ArrayList;

public class UserService {
    public static void registerNewUser(ClientConnection clientConnection, Action action) {
        UserProcess.register(clientConnection, action);

    }

    public static void loginUser(ClientConnection clientConnection, Action action) {
        UserProcess.loginUser(clientConnection, action);
    }

    static void removeConnectionByConnectionId(int connectionId) {
        ArrayList<User> users = UserPool.perform(PoolAction.GET, null);
        assert users != null;
        for (User user : users) {
            if (user.getClientConnection() != null && user.getClientConnection().getConnectionId() == connectionId) {
                user.setClientConnection(null);
                System.out.println("removed connection of User: " + user.getUsername());
            }
        }
    }

    public static void sendMessage(ClientConnection clientConnection, Action action) {
        SendMessagePayload sendMessagePayload = (SendMessagePayload) action.getPayload();

        int messageId = MessageService.addMessage(sendMessagePayload);
        AddMessagePayload addMessagePayload = createAddMessagePayload(sendMessagePayload, messageId);


        ArrayList<User> users = UserPool.perform(PoolAction.GET, null);
        Sender.sendAddMessage(clientConnection, addMessagePayload);

        assert users != null;
        for (User user : users) {
            if (user.getClientConnection() != null && user.getId() == sendMessagePayload.getTargetId()) {
                Sender.sendAddMessage(user.getClientConnection(), addMessagePayload);
                return;
            }
        }
    }

    static AddMessagePayload createAddMessagePayload(SendMessagePayload sendMessagePayload, int id){
        return new AddMessagePayload( //
                sendMessagePayload.getContent(), //
                id, //
                sendMessagePayload.getSenderId(), //
                sendMessagePayload.getTargetId());
    }
}
