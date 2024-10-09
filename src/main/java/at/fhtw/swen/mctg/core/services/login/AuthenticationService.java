package at.fhtw.swen.mctg.core.services.login;

import at.fhtw.swen.mctg.persistence.dao.UserDao;

import java.util.Map;

public class AuthenticationService {
    private final static String USERNAME= "Username";
    private final static String PASSWORD = "Password";
    private UserDao userDao;
    public AuthenticationService() {
        this.userDao = new UserDao();
    }
    public String authenticate(Map<String,String> loginData) {
        String token = null;
        String username = loginData.get(USERNAME);
        String password = loginData.get(PASSWORD);
        if (isValid(username) && isValid(password)) {
            if (userDao.validateCredentials(username, password)) {
                token = generateToken(username);
            }
        }

        return token;
    }
    private String generateToken(String login) {
        return login + "-mtcgToken";
    }

    private boolean isValid(String data) {
        return data != null && !data.isEmpty();
    }

}
