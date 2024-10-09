package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.core.User;

import java.util.HashMap;
import java.util.Map;


public class UserDao {
    Map<String,User> dummyDB = new HashMap<>();
    public boolean validateCredentials(String login, String password) {
        //TODO add logic password passt to user
        if (!isUserExists(login)) {
            System.out.println("User with this login doesn't exist");
            return false;
        }
        User user = findUserByUsername(login);
        return user.getPassword().equals(password);
    }

    public boolean isUserExists(String login) {
        //System.out.println(dummyDB);
        return findUserByUsername(login) != null;
    }

    public boolean save(User user) {

        return null == dummyDB.put(user.getLogin(), user);
    }

    public User findUserByUsername(String login) {
        return dummyDB.get(login);
    }



}
