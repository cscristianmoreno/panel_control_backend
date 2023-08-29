package com.example.myapp.services.tokenService;

import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.myapp.services.tokenService.build.TokenBuild;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.session.expired}")
    private int sessionExpired;

    public TokenBuild createToken(String userId, String email, String name, String lastname, String lastsession) {
        JwtBuilder jwt = Jwts.builder();

        HashMap<String, String> claims = new HashMap<String, String>();

        long issued = System.currentTimeMillis();
        long expire = System.currentTimeMillis() + (sessionExpired * 1000);

        claims.put("id", userId);
        claims.put("email", email);
        claims.put("name", name);        
        claims.put("lastname", lastname);
        claims.put("lastsession", lastsession);

        SecretKey key = getSecretKey();

        jwt.setSubject(userId);
        jwt.setClaims(claims);
        jwt.setExpiration(new Date(expire));
        jwt.setIssuedAt(new Date(issued));
        jwt.signWith(key);

        String token = jwt.compact();

        TokenBuild tokenBuild = TokenBuild.builder()
        .access_token(token)
        .token_type("Bearer")
        .expires_in(expire)
        .build();

        return tokenBuild;
    }

    public Claims decodeToken(String token) {
        SecretKey key = getSecretKey();

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims;
    }

    public SecretKey getSecretKey() {
        byte[] decoder = Decoders.BASE64.decode(secretKey);
        SecretKey key = Keys.hmacShaKeyFor(decoder);
        return key;
    }
}
