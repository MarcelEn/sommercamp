package sommercamp.engler.chatserver;

import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.ActionTypes;
import sommercamp.engler.modules.payloads.*;

import java.util.ArrayList;

public class UserPool {
    private static ArrayList<User> users = new ArrayList<User>();
    private static int idCounter = 0;

    public synchronized static void registerNewUser(ClientConnection clientConnection, Action action) {
        RegisterPayload registerPayload = (RegisterPayload) action.getPayload();

        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getUsername().equals(registerPayload.getUsername())) {
                Sender.sendUserNameInUser(clientConnection);
                return;
            }

        String accessKey = KeyGenerator.generateKey();
        User newUser = new User(accessKey, registerPayload.getUsername(), clientConnection);
        clientConnection.setUser(newUser);
        users.add(newUser);
        System.out.println("created new User " + newUser.getUsername());

        Sender.sendShowAccessKey(clientConnection, accessKey + "-" + registerPayload.getUsername());

        afterLoginOrRegisterProcess(clientConnection);
    }

    public synchronized static void loginUser(ClientConnection clientConnection, Action action) {
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

        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).getUsername().equals(clientConnection.getUser().getUsername()))
                Sender.sendAddUserInformation(users.get(i), clientConnection);
        }
    }

    public synchronized static void removeConnectionByConnectionId(int connectionId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getClientConnection() != null && users.get(i).getClientConnection().getConnectionId() == connectionId) {
                users.get(i).setClientConnection(null);
                System.out.println("removed connection of User: " + users.get(i).getUsername());
            }
        }
    }

    public synchronized static int getNextId() {
        return idCounter++;
    }

}
