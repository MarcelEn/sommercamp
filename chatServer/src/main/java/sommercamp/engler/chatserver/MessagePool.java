package sommercamp.engler.chatserver;

import sommercamp.engler.modules.model.Message;
import sommercamp.engler.modules.payloads.AddMessagePayload;
import sommercamp.engler.modules.payloads.SendMessagePayload;

import java.util.ArrayList;

class MessagePool {
    private static ArrayList<Message> messages = new ArrayList<Message>();

    private static int id = 0;


    synchronized static AddMessagePayload addMessage(SendMessagePayload sendMessagePayload){
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

    static void sendAllMessages(ClientConnection clientConnection) {
        ArrayList<Message> messagesCopy = (ArrayList<Message>) messages.clone();
        int clientId = clientConnection.getUser().getId();

        for (Message aMessagesCopy : messagesCopy) {
            if (aMessagesCopy.getSenderId() == clientId || aMessagesCopy.getTargetId() == clientId)
                Sender.sendAddMessage(clientConnection, new AddMessagePayload(
                        aMessagesCopy.getContent(),
                        aMessagesCopy.getId(),
                        aMessagesCopy.getSenderId(),
                        aMessagesCopy.getTargetId()
                ));
        }
    }
}
