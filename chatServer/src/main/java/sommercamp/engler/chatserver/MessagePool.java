package sommercamp.engler.chatserver;

import sommercamp.engler.modules.model.Message;
import sommercamp.engler.modules.payloads.AddMessagePayload;
import sommercamp.engler.modules.payloads.SendMessagePayload;

import java.util.ArrayList;

public class MessagePool {
    private static ArrayList<Message> messages = new ArrayList<Message>();

    private static int id = 0;


    public synchronized static AddMessagePayload addMessage(SendMessagePayload sendMessagePayload){
        AddMessagePayload addMessagePayload = new AddMessagePayload( //
                sendMessagePayload.getContent(), //
                getNextId(), //
                sendMessagePayload.getSenderId(), //
                sendMessagePayload.getTargetId() //
        );
        messages.add(new Message(addMessagePayload));
        System.out.println("added message: " + sendMessagePayload.getSenderId() + " (" + sendMessagePayload.getContent() + ")-> " + sendMessagePayload.getTargetId());
        return addMessagePayload;
    }

    private static int getNextId(){
        return id++;
    }

    public static void sendAllMessages(ClientConnection clientConnection) {
        ArrayList<Message> messagesCopy = (ArrayList<Message>) messages.clone();
        int clientId = clientConnection.getUser().getId();

        for(int i = 0; i < messagesCopy.size(); i++){
            if(messagesCopy.get(i).getSenderId() == clientId || messagesCopy.get(i).getTargetId() == clientId)
                Sender.sendAddMessage(clientConnection, new AddMessagePayload(
                        messagesCopy.get(i).getContent(),
                        messagesCopy.get(i).getId(),
                        messagesCopy.get(i).getSenderId(),
                        messagesCopy.get(i).getTargetId()
                ));
        }
    }
}
