package com.conexa.swapi_integration.service;

import com.conexa.swapi_integration.dto.TokenDTO;

public interface AuthService {

    public TokenDTO generateToken(String username);
}
