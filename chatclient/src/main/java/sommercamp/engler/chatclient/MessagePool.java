package sommercamp.engler.chatclient;

import sommercamp.engler.modules.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MessagePool {
    private static ArrayList<Message> messages = new ArrayList<Message>();

    public static synchronized void addMessage(Message message) {
        messages.add(message);
    }

    public static ArrayList<Message> getMessagesOf(User user) {
        ArrayList<Message> messagesOfUser = new ArrayList<Message>();

        for (int i = 0; i < messages.size(); i++)
            if (messages.get(i).getTargetId() == user.getId() || messages.get(i).getSenderId() == user.getId())
                messagesOfUser.add(messages.get(i));

        return sortMessagesById(messagesOfUser);
    }

    private static ArrayList<Message> sortMessagesById(ArrayList<Message> filteredMessages) {
        Collections.sort(filteredMessages, new Comparator<Message>() {
            public int compare(Message m1, Message m2) {
                return m1.getId() > m2.getId() ? 1 : (m1.getId() < m2.getId()) ? -1 : 0;
            }
        });
        return filteredMessages;
    }
}
