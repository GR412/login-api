package com.mindera.login.controllers;

import com.mindera.login.models.api.LoginRequest;
import com.mindera.login.models.api.LoginResponse;
import com.mindera.login.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * This class is an endpoint that is exposed to the frontend. This is achieved via the request mapping URL '/login'.
 *
 * Each request from the frontend has a defined backend URL with both the request mapping '/login'.
 *
 * With this information the frontend calls the correct controller method based on the URL matching.
 */
@RestController
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private UsersService usersService;

    /**
     * Processes an incoming POST request to login a user.
     *
     * Frontend uses http://localhost:8080/login to call this method.
     *
     * @return the result of calling the login usersService method.
     */
    @RequestMapping(method = POST)
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return usersService.login(loginRequest);
    }
}