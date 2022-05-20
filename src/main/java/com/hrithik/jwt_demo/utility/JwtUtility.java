package com.hrithik.jwt_demo.utility;

import com.hrithik.jwt_demo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtility implements Serializable {

    private final String secretKey = "secretkey123";

    public JwtUtility() {
    }

    public String generateToken(User user) {
        // Claims are statements about an entity (typically, the user) and additional data. for payload
        Claims claims = Jwts.claims();
        claims.put("userId", user.getId() + "");
        claims.put("role", user.getUserType());
        return doGenerateToken(claims);
    }

    private String doGenerateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(2L, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String authToken) throws io.jsonwebtoken.ExpiredJwtException, io.jsonwebtoken.UnsupportedJwtException, io.jsonwebtoken.MalformedJwtException, io.jsonwebtoken.SignatureException, IllegalArgumentException {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
        return true;
    }


    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("userId").toString());
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token) throws RuntimeException {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validate(String authToken) {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
        return true;
    }
}
