package com.cm.authservice.util;

import com.cm.authservice.exception.TokenEmailDoesNotMatchException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret){ // Inject the secret key from env variables.
        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(
                StandardCharsets.UTF_8));

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String role){
        return Jwts.builder()
                .subject(email)         // Mark it for the individual
                .claim("role", role)   // Mark the individuals role
                .issuedAt(new Date())   // Creation time
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(secretKey)    // Sign with our secret key, basically our encoding method we have
                .compact();
    }

    public void validateToken(String token) {
        try{
            Jwts.parser().verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (JwtException e){
            throw new JwtException("Invalid JWT");
        }
    }

    public void validateTokenEmailMatchesProvidedEmail(String token, String email) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String subject = claims.getSubject();

            if (!subject.equalsIgnoreCase(email)) {
                throw new TokenEmailDoesNotMatchException("Token does not belong to the provided email.");
            }

        } catch (Exception e) {
            throw new JwtException("Invalid token");
        }
    }

}
