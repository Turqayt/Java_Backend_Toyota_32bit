package com.j32bit.backend.utility;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;


@Log4j2
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private static final int EXPIRATION_MILLISECONDS = 40 * 60 * 1000;

    @Value("${info.app.jwt.secret}")
    private String secret;

    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date((System.currentTimeMillis() + EXPIRATION_MILLISECONDS)))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String refresh(String token) {
        Claims claims = getAllClaimsFromToken(token);

        return Jwts.builder()
                .addClaims(claims)
                .setExpiration(new Date((System.currentTimeMillis() + EXPIRATION_MILLISECONDS)))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        return (!isExpired(token));
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationdateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean isExpired(String token) {
        return !(new Date().before(getExpirationdateFromToken(token)));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) throws ExpiredJwtException{
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}
