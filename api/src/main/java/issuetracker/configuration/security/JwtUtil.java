package issuetracker.configuration.security;

import io.jsonwebtoken.*;
import issuetracker.domain.model.Account;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private static final long WEEK_IN_MS = 7 * 24 * 60 * 60 * 1000;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    private final Logger logger;

    public String generateAccessToken(final Account account) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", account.getId(), account.getEmail()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + WEEK_IN_MS))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private Claims getClaims(final String token) {
        return parseClaims(token).getBody();
    }

    private Jws<Claims> parseClaims(final String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token);
    }

    public String getUserId(final String token) {
        return getClaims(token).getSubject().split(",")[0];
    }

    public String getUserEmail(final String token) {
        return getClaims(token).getSubject().split(",")[1];
    }

    public Date getExpiration(final String token) {
        return getClaims(token).getExpiration();
    }

    public boolean isValid(final String token) {
        try {
            parseClaims(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

}
