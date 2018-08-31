package sommercamp.engler.chatclient;


import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.payloads.LoginSuccessPayload;
import sommercamp.engler.modules.payloads.ShowAccessKeyPayload;
import sommercamp.engler.modules.payloads.AddUserPayload;

import java.io.IOException;
import java.util.ArrayList;

public class ChatClient implements ConnectionRequirements {

    private Connection connection;

    UserInputHandler userInputHandler;

    private boolean isChatting;

    private int userId;

    ChatClient() {
        connection = new Connection(this);
        System.out.println("Connected to: " + connection.getConnectionInformation());
        userInputHandler = new UserInputHandler(connection);
    }

    public void onMessage(Action action) {
        System.out.println(action.getType());
        switch (action.getType()){
            case SHOW_ACCESS_KEY:
                ShowAccessKeyPayload showAccessKeyPayload = (ShowAccessKeyPayload) action.getPayload();
                System.out.println("Your access Key is: " + showAccessKeyPayload.getAccessKey());
                break;
            case USERNAME_ALREADY_IN_USE:
                System.out.println("The Username is already in use");
                userInputHandler.register();
                break;
            case ACCESS_KEY_IS_NOT_VALID:
                System.out.println("Access Key is not valid");
                userInputHandler.login();
                break;
            case LOGIN_SUCCESS:
                LoginSuccessPayload loginSuccessPayload = (LoginSuccessPayload) action.getPayload();
                userId = loginSuccessPayload.getId();
                // userInputHandler.selectUser();
                break;
            case ADD_USER:
                UserPool.addUser((AddUserPayload) action.getPayload());
                if(!isChatting)
                    UserPool.printUserSelectScreen();
                break;
        }

    }

    public void onServerDisconnect() {
        System.out.println("disconnect");
    }

    public static void clearScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}
