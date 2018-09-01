package sommercamp.engler.chatclient;

import sommercamp.engler.modules.model.Message;
import sommercamp.engler.modules.payloads.AddMessagePayload;
import sommercamp.engler.modules.payloads.AddUserPayload;

import java.util.ArrayList;

public class UserPool {
    private static ArrayList<User> users = new ArrayList<User>();

    public static synchronized void addUser(AddUserPayload addUserPayload) {
        users.add(new User(addUserPayload));
        System.out.println(users.size());
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
        System.out.println("\nSelect a User to chat with and send q to get back here or just press enter to update your messages");
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
        ArrayList<Message> messages = user.getMessages();
        System.out.println("printing messages-" + messages.size());
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

    public static void addMessage(AddMessagePayload payload) {
        ArrayList<User> userList = (ArrayList<User>) users.clone();
        System.out.println("gonna add a message");
        for (int i = 0; i < userList.size(); i++){
            System.out.println("mapping through dudes");
            if (userList.get(i).getId() == payload.getSenderId() || userList.get(i).getId() == payload.getTargetId() || true) {
                System.out.println("found dude");
                userList.get(i).addMessage(new Message(payload));
            }
        }

    }
}
