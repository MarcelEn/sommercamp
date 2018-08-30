package sommercamp.engler.chatclient;


import sommercamp.engler.modules.Action;
public class ChatClient {

    private Connection connection;

    UserInputHandler userInputHandler;

    ChatClient() {
        connection = new Connection(this);
        System.out.println("Connected to: " + connection.getConnectionInformation());
        userInputHandler = new UserInputHandler(connection);
    }

    void onMessage(Action action) {
        System.out.println(action.getType());
    }
}
