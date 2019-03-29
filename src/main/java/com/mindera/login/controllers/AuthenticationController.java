package com.mindera.login.controllers;

import com.mindera.login.models.api.LoginRequest;
import com.mindera.login.models.api.LoginResponse;
import com.mindera.login.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = POST)
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return usersService.login(loginRequest);
    }

}