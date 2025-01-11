package at.fhtw.swen.mctg.services.common;

import at.fhtw.swen.mctg.exceptions.MissingTokenException;

public class AuthUtils {
    public static String extractToken(String rawToken) {
        if (rawToken == null || !rawToken.startsWith("Bearer ")) {
            throw new MissingTokenException("User is not logged in");
        }
        return rawToken.substring("Bearer ".length());
    }
}
