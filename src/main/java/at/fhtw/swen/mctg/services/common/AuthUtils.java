package at.fhtw.swen.mctg.services.common;

public class AuthUtils {
    public static String extractToken(String rawToken) {
        if (rawToken == null || !rawToken.startsWith("Bearer ")) {
            throw new IllegalArgumentException("User is not logged in");
        }
        return rawToken.substring("Bearer ".length());
    }
}
