package com.conexa.swapi_integration.service.impl;

import com.conexa.swapi_integration.dto.TokenDTO;
import com.conexa.swapi_integration.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.expiration}")
    private Long expirationTime;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public TokenDTO generateToken(String username) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.
                setToken(Jwts.builder()
                        .setClaims(new HashMap<>())
                        .setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                        .signWith(SignatureAlgorithm.HS512, secret)
                        .compact());
        return tokenDTO;
    }
}
