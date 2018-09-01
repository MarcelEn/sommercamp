package sommercamp.engler.chatclient;

import sommercamp.engler.modules.model.Message;
import sommercamp.engler.modules.payloads.AddMessagePayload;
import sommercamp.engler.modules.payloads.AddUserPayload;

import java.util.ArrayList;

public class UserPool {
    private static ArrayList<User> users = new ArrayList<User>();

    public static synchronized void addUser(AddUserPayload addUserPayload) {
        users.add(new User(addUserPayload));
    }

    public static int getLength() {
        return users.size();
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void printUserSelectScreen() {
        ChatClient.clearScreen();
        ArrayList<User> userList = (ArrayList<User>) users.clone();
        for (int i = 0; i < userList.size(); i++)
            System.out.println(userList.get(i).getUsername());
        System.out.println("\nSelect a User to chat with or press enter to update your messages");
    }

    public static boolean isThisUserNameExist(String userInput) {
        ArrayList<User> userList = (ArrayList<User>) users.clone();
        if (userInput == null || userInput.equals(""))
            return false;
        for (int i = 0; i < userList.size(); i++)
            if (userList.get(i).getUsername().equals(userInput))
                return true;
        System.out.println("Please select an existing user.");
        return false;
    }

    public static synchronized void printMessages(User user) {
        ArrayList<Message> messages = MessagePool.getMessagesOf(user);
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getSenderId() == user.getId()) {
                System.out.println(user.getUsername() + ":");
            } else {
                System.out.println("You:");
            }
            System.out.println(messages.get(i).getContent() + "\n");
        }
    }

    public static User getUserByUsername(String userInput) {
        ArrayList<User> userList = (ArrayList<User>) users.clone();
        for (int i = 0; i < userList.size(); i++)
            if (userList.get(i).getUsername().equals(userInput)) {
                return userList.get(i);
            }

        return null;
    }
}
