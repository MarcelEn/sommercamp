package sommercamp.engler.chatclient;


import sommercamp.engler.modules.Action;
public class ChatClient implements ConnectionRequirements {

    private Connection connection;

    UserInputHandler userInputHandler;

    ChatClient() {
        connection = new Connection(this);
        System.out.println("Connected to: " + connection.getConnectionInformation());
        userInputHandler = new UserInputHandler(connection);
    }

    public void onMessage(Action action) {
        System.out.println(action.getType());
    }

    public void onServerDisconnect() {
        System.out.println("disconnect");
    }
}
