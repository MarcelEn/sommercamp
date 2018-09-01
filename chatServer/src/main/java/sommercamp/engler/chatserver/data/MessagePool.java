package sommercamp.engler.chatserver.data;

import sommercamp.engler.chatserver.model.User;
import sommercamp.engler.modules.model.Message;
import sommercamp.engler.modules.payloads.SendMessagePayload;

import java.util.ArrayList;

public class MessagePool {
    private static ArrayList<Message> messages = new ArrayList<Message>();

    public synchronized static ArrayList<Message> perform(PoolAction poolAction, SendMessagePayload sendMessagePayload){
        switch (poolAction){
            case ADD:
                Message message = new Message( //
                        sendMessagePayload.getContent(), //
                        sendMessagePayload.getSenderId(), //
                        sendMessagePayload.getTargetId(), //
                        messages.size());

                messages.add(message);
                ArrayList<Message> messageAsList = new ArrayList<Message>();
                messageAsList.add(message);
                return messageAsList;
            case GET:
                return (ArrayList<Message>) messages.clone();
        }
        return null;
    }
}
