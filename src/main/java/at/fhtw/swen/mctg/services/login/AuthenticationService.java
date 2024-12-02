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
            String token = generateToken(username);
            //TODO beim login token in db speichern, aber wofür ... dann beim anderen beim spield/kauf z.b of token passt mit db
            //save token

            return token;
        }else {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }
    public void signup(String username, String password) throws DataAccessException {
        UnitOfWork unitOfWork = new UnitOfWork();
        try(unitOfWork) {
            User user = new User(username, password);
            int userId = new UserRepository(unitOfWork).save(user);
            //TODO fix create stack
            int stackId = new StackRepository(unitOfWork).create(userId);
            unitOfWork.commitTransaction();
        }catch (Exception e) {
            unitOfWork.rollbackTransaction();
            throw new DataAccessException("Error signup user: " + e.getMessage(), e);
        }

    }
    public boolean isUserExists(String username) throws DataAccessException {
        UnitOfWork unitOfWork = new UnitOfWork();
        try(unitOfWork) {
            User user = new UserRepository(unitOfWork).findByUsername(username);
            unitOfWork.commitTransaction();
            return user != null;
        }catch (Exception e) {
            unitOfWork.rollbackTransaction();
            throw new DataAccessException("Error finding user by username", e);
        }

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
