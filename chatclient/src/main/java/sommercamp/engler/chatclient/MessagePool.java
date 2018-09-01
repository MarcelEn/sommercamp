package sommercamp.engler.chatclient;

import sommercamp.engler.modules.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class MessagePool {
    private static ArrayList<Message> messages = new ArrayList<Message>();

    static synchronized void addMessage(Message message) {
        messages.add(message);
    }

    static ArrayList<Message> getMessagesOf(User user) {
        ArrayList<Message> messagesOfUser = new ArrayList<Message>();

        for (Message message : messages)
            if (message.getTargetId() == user.getId() || message.getSenderId() == user.getId())
                messagesOfUser.add(message);

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
