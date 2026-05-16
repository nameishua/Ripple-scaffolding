package top.coderak.core.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private static final long EXPIRATION = 86400000;

    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String account, String name) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("account", account);
        claims.put("name", name);
        return createToken(claims, account);
    }

    private static String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(KEY)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getAccount(String token) {
        Claims claims = parseToken(token);
        return claims.get("account", String.class);
    }

    public static String getName(String token) {
        Claims claims = parseToken(token);
        return claims.get("name", String.class);
    }

    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
