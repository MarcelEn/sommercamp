package sommercamp.engler.chatclient;

import sommercamp.engler.modules.model.Message;
import sommercamp.engler.modules.payloads.AddUserPayload;

import java.util.ArrayList;

class UserPool {
    private static ArrayList<User> users = new ArrayList<User>();

    static synchronized void addUser(AddUserPayload addUserPayload) {
        users.add(new User(addUserPayload));
    }

    static void printUserSelectScreen() {
        ChatClient.clearScreen();
        for (User userList : users) System.out.println(userList.getUsername());
        System.out.println("\nSelect a User to chat with or press enter to update your messages");
    }

    static boolean isThisUserNameExist(String userInput) {
        if (userInput == null || userInput.equals(""))
            return false;
        for (User userList : users)
            if (userList.getUsername().equals(userInput))
                return true;
        System.out.println("Please select an existing user.");
        return false;
    }

    static synchronized void printMessages(User user) {
        ArrayList<Message> messages = MessagePool.getMessagesOf(user);
        for (Message message : messages) {
            if (message.getSenderId() == user.getId()) {
                System.out.println(user.getUsername() + ":");
            } else {
                System.out.println("You:");
            }
            System.out.println(message.getContent() + "\n");
        }
    }

    static User getUserByUsername(String userInput) {
        for (User userList : users)
            if (userList.getUsername().equals(userInput)) {
                return userList;
            }

        return null;
    }
}
