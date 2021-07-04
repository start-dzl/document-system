package com.meiya.uploader.documentsystem.security;

import com.meiya.uploader.documentsystem.core.config.property.GlobalProperties;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final int INITIAL_CAPACITY = 5;
    private static String TYPE_KEY = "type";

    @Autowired
    private GlobalProperties globalProperties;

    private String token(String username, Date expiryDate, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, globalProperties.getSecret())
                .addClaims(claims).compact();
    }

    public String token(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + globalProperties.getExpire());
        Map<String, Object> claims = new HashMap<>(INITIAL_CAPACITY);
        claims.put(TYPE_KEY, "ADMIN");
        return JwtTokenProvider.this.token(username, expiryDate, claims);
    }

    public String token(UserDetails userDetails) {
        return token(userDetails.getUsername());
    }

    public Pair<String, String> parse(String token) {
        Claims claims = Jwts.parser().setSigningKey(globalProperties.getSecret()).parseClaimsJws(token).getBody();
        String type = (String) claims.get(TYPE_KEY);
        String username = claims.getSubject();
        return Pair.of(type, username);
    }

    public boolean validate(String authToken) {
        try {
            Jwts.parser().setSigningKey(globalProperties.getSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.debug("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.debug("Invalid JWT token. token:{}", authToken);
        } catch (ExpiredJwtException ex) {
            logger.debug("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.debug("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.debug("JWT claims string is empty.");
        }
        return false;
    }
}
