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

    public static void sendMessage(ClientConnection clientConnection, Action action) {
        SendMessagePayload sendMessagePayload = (SendMessagePayload) action.getPayload();

        int messageId = MessageService.addMessage(sendMessagePayload, clientConnection.getUser().getId());
        AddMessagePayload addMessagePayload = MessageService.createAddMessagePayload(sendMessagePayload, messageId, clientConnection.getUser().getId());


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


    public static void setConnectionToNull(User user){
        ArrayList<User> users = UserPool.perform(PoolAction.GET, null);
        assert users != null;
        for (User userListUser : users) {
            if (userListUser.getId() == user.getId()) {
                userListUser.setClientConnection(null);
                System.out.println("removed connection of User: " + userListUser.getUsername());
            }
        }
    }
}
