package sommercamp.engler.chatserver.service;

import sommercamp.engler.chatserver.data.PoolAction;
import sommercamp.engler.chatserver.data.UserPool;
import sommercamp.engler.chatserver.gateway.Sender;
import sommercamp.engler.chatserver.model.ClientConnection;
import sommercamp.engler.chatserver.model.User;
import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.payloads.LoginPayload;
import sommercamp.engler.modules.payloads.RegisterPayload;

import java.util.ArrayList;

class UserProcess {
    synchronized static void register(ClientConnection clientConnection, Action action) {
        RegisterPayload registerPayload = (RegisterPayload) action.getPayload();
        ArrayList<User> users = UserPool.perform(PoolAction.GET, null);

        assert users != null;
        for (User user : users)
            if (user.getUsername().equals(registerPayload.getUsername())) {
                Sender.sendUserNameInUser(clientConnection);
                return;
            }

        String accessKey = KeyGenerator.generateKey();
        User newUser = new User(accessKey, registerPayload.getUsername(), clientConnection);
        clientConnection.setUser(newUser);
        UserPool.perform(PoolAction.ADD, newUser);
        System.out.println("created new User " + newUser.getUsername());

        Sender.sendShowAccessKey(clientConnection, accessKey + "-" + registerPayload.getUsername());
        informAllOnlineUserAboutNewUser(users, newUser);
        afterLoginOrRegisterProcess(users, clientConnection);
    }

    synchronized static void loginUser(ClientConnection clientConnection, Action action) {
        LoginPayload loginPayload = (LoginPayload) action.getPayload();
        ArrayList<User> users = UserPool.perform(PoolAction.GET, null);
        User user = null;

        assert users != null;
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

        afterLoginOrRegisterProcess(users, clientConnection);
    }

    private static void informAllOnlineUserAboutNewUser(ArrayList<User> users, User newUser){
        for (User user : users)
            if (user.getClientConnection() != null && user.getId() != newUser.getId()) {
                Sender.sendAddUserInformation(newUser, user.getClientConnection());
            }
    }

    private static void afterLoginOrRegisterProcess(ArrayList<User> users, ClientConnection clientConnection) {
        Sender.sendLoginSuccess(clientConnection);

        for (User user : users) {
            if (!user.getUsername().equals(clientConnection.getUser().getUsername()))
                Sender.sendAddUserInformation(user, clientConnection);
        }
        MessageService.sendAllMessages(clientConnection);
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
}
