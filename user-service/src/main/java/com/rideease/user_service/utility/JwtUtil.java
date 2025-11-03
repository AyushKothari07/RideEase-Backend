package com.rideease.user_service.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {


    @Value("${jwt.secret}")
    private String secret;

    private Key signingKey;

    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
//
//    private static final String SECRET = "RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR"; // Must be at least 32 bytes
//
//    private static final Key SIGNING_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private static final long EXPIRATION_TIME = 86400000;//1 day

    public String generateToken(String email, String role){
        return Jwts.builder().setSubject(email).
                claim("role", role).
                setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME)).signWith(
                        SignatureAlgorithm.HS256,signingKey
                ).compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, String email){
        final String extractedEmail = extractClaims(token).getSubject();
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {

        return extractClaims(token).getExpiration().before(new Date());
    }
}
