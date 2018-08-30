package sommercamp.engler.chatserver;

import sommercamp.engler.modules.Action;

import java.util.ArrayList;

public class UserPool {
    private static ArrayList<User> users = new ArrayList<User>();

    public static void addUser(User user) {
        users.add(user);
    }

    public static void registerNewUser(User user, Action action) {
        users.add(user);
        System.out.println(users.size());
    }
}
