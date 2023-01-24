package com.piaskowy.urlshortenerbackend.auth.service;

import com.piaskowy.urlshortenerbackend.user.model.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
public class JwtService {

    //TODO: move it to configuration properites
    private static final String SECRET_KEY = "6B5970337336763979244226452948404D635166546A576D5A7134743777217A";


    public String extractEmail(String jwtToken) {
        return extractSingleClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractSingleClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    public String generateToken(CustomUserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, CustomUserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(java.sql.Date.valueOf(LocalDate.now()))
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(30)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String jwtToken, CustomUserDetails customUserDetails) {
        final String email = extractEmail(jwtToken);
        return (email.equals(customUserDetails.getUsername())) && !isTokenExpired(jwtToken);
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
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}