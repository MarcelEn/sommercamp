package sommercamp.engler.chatclient;

import sommercamp.engler.modules.payloads.AddUserPayload;

import java.util.ArrayList;

public class UserPool {
    private static ArrayList<User> users = new ArrayList<User>();

    public static synchronized void addUser(AddUserPayload addUserPayload){
        users.add(new User(addUserPayload));
    }

    public static int getLength(){
        return users.size();
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void printUserSelectScreen() {
        ChatClient.clearScreen();
        ArrayList<User> userList = (ArrayList<User>) users.clone();
        for(int i = 0; i < userList.size(); i++)
            System.out.println(i + " " + userList.get(i).getUsername());
        System.out.println("\nSelect a User to chat with");
    }
}
