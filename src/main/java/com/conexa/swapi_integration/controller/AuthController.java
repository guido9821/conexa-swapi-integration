package com.conexa.swapi_integration.controller;

import com.conexa.swapi_integration.dto.TokenDTO;
import com.conexa.swapi_integration.dto.TokenRequestDTO;
import com.conexa.swapi_integration.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping()
    public TokenDTO generateToken(@RequestBody TokenRequestDTO tokenRequestDTO){
        return authService.generateToken(tokenRequestDTO.getUserName());
    }
}
