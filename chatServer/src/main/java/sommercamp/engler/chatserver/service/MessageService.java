package sommercamp.engler.chatserver.service;

import sommercamp.engler.chatserver.data.MessagePool;
import sommercamp.engler.chatserver.data.PoolAction;
import sommercamp.engler.chatserver.gateway.Sender;
import sommercamp.engler.chatserver.model.ClientConnection;
import sommercamp.engler.modules.model.Message;
import sommercamp.engler.modules.payloads.AddMessagePayload;
import sommercamp.engler.modules.payloads.SendMessagePayload;

import java.util.ArrayList;

class MessageService {

    synchronized static int addMessage(SendMessagePayload sendMessagePayload){
        System.out.println("added message: " + sendMessagePayload.getSenderId() + " (" + sendMessagePayload.getContent() + ")-> " + sendMessagePayload.getTargetId());
        return MessagePool.perform(PoolAction.ADD, sendMessagePayload).get(0).getId();
    }

    static void sendAllMessages(ClientConnection clientConnection) {
        ArrayList<Message> messagesCopy = MessagePool.perform(PoolAction.GET, null);
        int clientId = clientConnection.getUser().getId();

        assert messagesCopy != null;
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
