package at.fhtw.swen.mctg.persistence.dao;

import at.fhtw.swen.mctg.core.User;

import java.util.HashMap;
import java.util.Map;


public class UserDao {
    private final static Map<String,User> dummyDB = new HashMap<>();
    private Map<String,User> dummyDB1;

    /*
    public UserDao() {
        dummyDB = new HashMap<>();
        dummyDB.put("kienboec", new User("kienboec", "daniel"));
        dummyDB.put("altenhof", new User("altenhof", "markus"));
        dummyDB.put("admin", new User("admin", "istrator"));
    }*/
    public  UserDao() {
       // dummyDB = new HashMap<>();
    }
    public boolean validateCredentials(String login, String password) {
        //TODO add logic password passt to user

        System.out.println("befor user exists in validateCredentials");
        System.out.println("login to validate: " + login);
        var result = isUserExists(login);
        System.out.println("isUserExists(login) in validate " + result);

        if (result) {
            System.out.println("user exists in validateCredentials");
            User user = findUserByUsername(login);
            if (user != null) {
                boolean passwordOk = user.getPassword().equals(password);
                System.out.println("passwordOk: " + passwordOk);
                return passwordOk;
            }
            return false;
        }
        return false;
    }

    public boolean isUserExists(String login) {
        printDb();
        boolean result = dummyDB.containsKey(login);

        System.out.println("isUserExists function dummyDB.containsKey(login): " + result);
        return result;
    }

    public boolean save(User user) {
        if (user == null || user.getLogin() == null) {
            return false;
        }
        if (isUserExists(user.getLogin())) {
            return false;
        }
        dummyDB.put(user.getLogin(), user);
        return true;
    }

    public User findUserByUsername(String login) {
        User result = dummyDB.get(login);
        System.out.println("in findUserByUsername "+ result);
        return result;
    }

    public void printDb() {
        System.out.println(" ");
        var entries = dummyDB.entrySet();
        for(var pair : entries) {
            System.out.println("    login-key: " + pair.getKey() + ", value-user: " + pair.getValue());
        }
        System.out.println(" ");
    }
}
