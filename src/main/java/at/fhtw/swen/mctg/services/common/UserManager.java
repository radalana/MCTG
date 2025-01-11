package at.fhtw.swen.mctg.services.common;

import at.fhtw.swen.mctg.exceptions.UserNotFoundException;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.user.UserRepository;

public class UserManager {
    public static User validateAndFetchUser(String token, UnitOfWork unitOfWork) {
        UserRepository userRepository = new UserRepository(unitOfWork);
        User user = userRepository.findUserByToken(token);
        if (user == null) {
            throw new UserNotFoundException("User with the provided token does not exist");
        }
        return user;
    }
}
