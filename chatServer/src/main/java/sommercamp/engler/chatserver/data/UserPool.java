package sommercamp.engler.chatserver.data;

import sommercamp.engler.chatserver.model.User;

import java.util.ArrayList;

public class UserPool {
    private static ArrayList<User> users = new ArrayList<User>();

    public synchronized static ArrayList<User> perform(PoolAction poolAction, User user){
        switch (poolAction){
            case ADD:
                user.setId(users.size());
                users.add(user);
            case GET:
                return (ArrayList<User>) users.clone();
        }
        return null;
    }
}
