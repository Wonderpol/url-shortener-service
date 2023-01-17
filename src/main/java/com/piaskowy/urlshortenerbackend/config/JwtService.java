package com.piaskowy.urlshortenerbackend.config;

import com.piaskowy.urlshortenerbackend.auth.user.model.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "6B5970337336763979244226452948404D635166546A576D5A7134743777217A";

    public String extractEmail(String jwtToken) {
        return extractSingleClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractSingleClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    public String generateToken(CustomUserDetails userDetails) {
        //null or empty hashmap
        return generateToken(null, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, CustomUserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String jwtToken, CustomUserDetails customUserDetails) {
        final String email = extractEmail(jwtToken);
        return (email.equals(customUserDetails.getUsername())) && isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(final String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(final String jwtToken) {
        return extractSingleClaim(jwtToken, Claims::getExpiration);
    }

    private Claims extractClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(jwtToken)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
