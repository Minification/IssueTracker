package issuetracker.configuration.security;

public class TokenParser {

    public static String extractTokenFromAuthorizationHeader(final String authorizationHeader) {
        return authorizationHeader.split(" ")[1].trim();
    }

}
