package ndky.paper.kpimgrapp.Utils;

import io.jsonwebtoken.*;
import ndky.paper.kpimgrapp.Security.Services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JwtUtils {
    private static final Logger logger = Logger.getLogger(JwtUtils.class.getName());

    @Value("${paper.jwt.secret}")
    private String jwtSecret;

    @Value("${paper.jwt.expireMs}")
    private long jwtExpireMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpireMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.log(Level.SEVERE, "Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            logger.log(Level.SEVERE, "Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.log(Level.SEVERE, "JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.log(Level.SEVERE, "JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
