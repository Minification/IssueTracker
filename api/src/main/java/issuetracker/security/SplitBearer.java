package issuetracker.security;

public class SplitBearer {

    public static String extractTokenFromAuthorizationHeader(final String authorizationHeader) {
        return authorizationHeader.split(" ")[1].trim();
    }

}
