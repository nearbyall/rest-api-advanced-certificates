package com.epam.esm.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JWTProvider {

    @Value("${jwt.expiration}")
    private long expirationInMinutes;

    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Method for generating unique user token after authorization.
     *
     * @param login unique user identifier
     * @return generated token
     */
    public String generateToken(String login) {
        Date date = Date.from(LocalDateTime.now().plusMinutes(expirationInMinutes)
                .atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Method for validating token.
     *
     * @param token the unique user token
     * @return {@code true} if the token is valid, {@code false} otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Method for decrypting login from token.
     *
     * @param token the unique user token
     * @return user login
     */
    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
