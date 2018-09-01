package sommercamp.engler.chatclient;

import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.ActionJsonHandler;
import sommercamp.engler.modules.ActionTypes;
import sommercamp.engler.modules.payloads.LoginPayload;
import sommercamp.engler.modules.payloads.RegisterPayload;
import sommercamp.engler.modules.payloads.SendMessagePayload;

import java.io.IOException;
import java.util.Scanner;


public class UserInputHandler {
    Connection connection;
    Scanner sc;
    ChatClient chatClient;

    public UserInputHandler(Connection connection, ChatClient chatClient) {
        this.connection = connection;
        this.chatClient = chatClient;
        sc = new Scanner(System.in);
        registerOrLogin();
    }

    void registerOrLogin() {
        System.out.println("Register or login? (r/l)");
        String userInput = "";
        while (!(userInput.equals("r") || userInput.equals("l"))) {
            userInput = sc.nextLine();
        }
        if (userInput.equals("r")) {
            register();
        } else {
            login();
        }
    }

    public void login() {
        String userInput = "";
        while (userInput.equals("") || userInput.split("-").length != 2) {
            System.out.println("Please enter your valid access Key");
            userInput = sc.nextLine();
        }

        sendToServer(new Action(ActionTypes.LOGIN, new LoginPayload(userInput)));
    }

    public void register() {
        System.out.println("Please Enter a Username");
        String userInput = "";
        while (userInput.equals("")) {
            userInput = sc.nextLine();
        }

        sendToServer(new Action(ActionTypes.REGISTER, new RegisterPayload(userInput)));

    }

    public void selectUser() {
        String userInput = null;
        while (!UserPool.isThisUserNameExist(userInput)) {
            userInput = sc.nextLine();
            if (userInput.equals("")) {
                UserPool.printUserSelectScreen();
            }
        }

        User user = UserPool.getUserByUsername(userInput);
        if (user == null) {
            selectUser();
            return;
        }
        chatClient.setIsChattingWith(user);
        startChat();
    }

    private void startChat() {
        if (chatClient.isChattingWith() == null) {
            selectUser();
            return;
        }
        UserPool.printMessages(chatClient.isChattingWith());
        String userInput;
        while (chatClient.isChattingWith() != null) {
            userInput = sc.nextLine();
            if (userInput.equals("")) {
                UserPool.printMessages(chatClient.isChattingWith());
                continue;
            }

            if (userInput.equals("q")) {
                chatClient.setIsChattingWith(null);
                continue;
            }
            sendToServer(new Action( //
                    ActionTypes.SEND_MESSAGE, //
                    new SendMessagePayload( //
                            userInput,
                            chatClient.getUserId(), //
                            chatClient.isChattingWith().getId() //
                            ) //
            ));

        }
        UserPool.printUserSelectScreen();

        selectUser();
    }


    private void sendToServer(Action action) {
        try {
            connection.sendMessage(ActionJsonHandler.serialize(action));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
