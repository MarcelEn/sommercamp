package sommercamp.engler.chatserver;

import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.payloads.AddMessagePayload;
import sommercamp.engler.modules.payloads.LoginPayload;
import sommercamp.engler.modules.payloads.RegisterPayload;
import sommercamp.engler.modules.payloads.SendMessagePayload;

import java.util.ArrayList;

class UserPool {
    private static ArrayList<User> users = new ArrayList<User>();
    private static int idCounter = 0;

    synchronized static void registerNewUser(ClientConnection clientConnection, Action action) {
        RegisterPayload registerPayload = (RegisterPayload) action.getPayload();

        for (User user : users)
            if (user.getUsername().equals(registerPayload.getUsername())) {
                Sender.sendUserNameInUser(clientConnection);
                return;
            }

        String accessKey = KeyGenerator.generateKey();
        User newUser = new User(accessKey, registerPayload.getUsername(), clientConnection);
        clientConnection.setUser(newUser);
        users.add(newUser);
        System.out.println("created new User " + newUser.getUsername());

        Sender.sendShowAccessKey(clientConnection, accessKey + "-" + registerPayload.getUsername());

        informAllOnlineUserAboutNewUser(newUser);
        afterLoginOrRegisterProcess(clientConnection);
    }

    private static void informAllOnlineUserAboutNewUser(User newUser){
        for (User user : users)
            if (user.getClientConnection() != null && user.getId() != newUser.getId()) {
                Sender.sendAddUserInformation(newUser, user.getClientConnection());
            }
    }

    synchronized static void loginUser(ClientConnection clientConnection, Action action) {
        LoginPayload loginPayload = (LoginPayload) action.getPayload();
        User user = null;
        for (int i = 0; i < users.size(); i++) {
            user = validateTokenAndConnectionState(users.get(i), loginPayload);
            if (user != null)
                i = users.size();
        }

        if (user == null) {
            Sender.sendAccessKeyIsNotValid(clientConnection);
            return;
        }

        user.setClientConnection(clientConnection);
        clientConnection.setUser(user);

        System.out.println(user.getUsername() + " logged in");

        afterLoginOrRegisterProcess(clientConnection);
    }

    private static User validateTokenAndConnectionState(User user, LoginPayload loginPayload) {
        String[] split = loginPayload.getAccessKey().split("-");
        if (split.length != 2) {
            return null;
        }
        if (user.getAccessKey().equals(split[0]) && user.getUsername().equals(split[1]) && user.getClientConnection() == null)
            return user;

        return null;
    }

    private static void afterLoginOrRegisterProcess(ClientConnection clientConnection) {
        Sender.sendLoginSuccess(clientConnection);

        for (User user : users) {
            if (!user.getUsername().equals(clientConnection.getUser().getUsername()))
                Sender.sendAddUserInformation(user, clientConnection);
        }
        MessagePool.sendAllMessages(clientConnection);
    }

    synchronized static void removeConnectionByConnectionId(int connectionId) {
        for (User user : users) {
            if (user.getClientConnection() != null && user.getClientConnection().getConnectionId() == connectionId) {
                user.setClientConnection(null);
                System.out.println("removed connection of User: " + user.getUsername());
            }
        }
    }

    synchronized static int getNextId() {
        return idCounter++;
    }

    synchronized static void sendMessage(ClientConnection clientConnection, Action action) {
        SendMessagePayload sendMessagePayload = (SendMessagePayload) action.getPayload();
        AddMessagePayload addMessagePayload = MessagePool.addMessage(sendMessagePayload);

        Sender.sendAddMessage(clientConnection, addMessagePayload);

        for (User user : users) {
            if (user.getClientConnection() != null && user.getId() == sendMessagePayload.getTargetId()) {
                Sender.sendAddMessage(user.getClientConnection(), addMessagePayload);
                return;
            }
        }
    }
}
