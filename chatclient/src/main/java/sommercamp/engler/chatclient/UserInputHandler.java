package sommercamp.engler.chatclient;

import sommercamp.engler.modules.Action;
import sommercamp.engler.modules.ActionJsonHandler;
import sommercamp.engler.modules.ActionTypes;
import sommercamp.engler.modules.payloads.RegisterPayload;

import java.io.IOException;
import java.util.Scanner;

public class UserInputHandler {
    Connection connection;
    public UserInputHandler(Connection connection){
        this.connection = connection;
        Scanner sc = new Scanner(System.in);

        while(true){
            try {
                connection.sendMessage(
                        ActionJsonHandler.serialize(
                                new Action(ActionTypes.REGISTER,
                                        RegisterPayload.builder()
                                        .username(sc.nextLine())
                                        .build()
                                )
                        )
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
