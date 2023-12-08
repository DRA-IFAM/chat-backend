package com.dra.backend.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration.time}")
    private long EXPIRATION_TIME;

    @Value("${jwt.issuer}")
    private String ISSUER;

    public String generateToken(String userId) {
        Algorithm algorithm = Algorithm.HMAC256(this.SECRET);
        String jwtToken = JWT.create().withSubject(userId).withExpiresAt(this.getExpiryTime())
                .withIssuer(ISSUER).sign(algorithm);
        return jwtToken == null ? null : jwtToken;
    }

    public String refreshToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Algorithm algorithm = Algorithm.HMAC256(this.SECRET);
        String userId = JWT.require(algorithm).withIssuer(ISSUER).build().verify(token).getSubject();
        return userId == null ? null : this.generateToken(userId);
    }

    public String validateToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Algorithm algorithm = Algorithm.HMAC256(this.SECRET);
        String userId = JWT.require(algorithm).withIssuer(ISSUER).build().verify(token).getSubject();
        return userId == null ? null : userId;

    }

    private Date getExpiryTime() {
        Date now = new Date();
        return new Date(now.getTime() + EXPIRATION_TIME);
    }
}