package sommercamp.engler.chatclient;

import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.ActionJsonHandler;
import sommercamp.engler.modules.ActionTypes;
import sommercamp.engler.modules.payloads.RegisterPayload;

import java.io.IOException;
import java.util.Scanner;

public class UserInputHandler {
    Connection connection;
    Scanner sc;

    public UserInputHandler(Connection connection) {
        this.connection = connection;
        sc = new Scanner(System.in);
        registerOrLogin();
    }

    void registerOrLogin() {
        System.out.println("Register or login? (r/l)");
        String userInput = "";
        while (!(userInput.equals("r") || userInput.equals("l"))) {
            userInput = sc.nextLine();
        }
        if(userInput.equals("r")){
            register();
        }else{
            login();
        }
    }

    private void login() {
    }

    private void register() {
        System.out.println("Please Enter a Username");
        String userInput = "";
        while (userInput.equals("")) {
            userInput = sc.nextLine();
        }


        sendToServer(new Action(ActionTypes.REGISTER, new RegisterPayload(userInput)));

    }

    private void sendToServer(Action action) {
        try {
            connection.sendMessage(ActionJsonHandler.serialize(action));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
