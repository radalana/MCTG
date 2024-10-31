package at.fhtw.swen.mctg.services.login;

import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.UserRepository;
import at.fhtw.swen.mctg.services.User;

public class AuthenticationService {
    public final static String USERNAME= "Username";
    public final static String PASSWORD = "Password";
    public final static String TOKEN = "-mtcgToken";
    public AuthenticationService() {
        //this.userDao = new UserRepository();
    }
    public String authenticateUser(String username, String password) throws DataAccessException {
        UnitOfWork unitOfWork = new UnitOfWork();
        User user = new UserRepository(unitOfWork).findByUsername(username);
        if (user.getPassword().equals(password)) {
            return generateToken(username);
        }
        return null;
    }
    public boolean isUserExists(String username) throws DataAccessException {
        UnitOfWork unitOfWork = new UnitOfWork();
        User user = new UserRepository(unitOfWork).findByUsername(username);
        return user != null;
    }

    private String generateToken(String login) {
        return login + TOKEN;
    }

    public boolean isValid(String data) {
        return data != null && !data.trim().isEmpty();
    }
    public boolean isTokenValid(String token) {
        return (token != null && !token.isEmpty());
    }
}
