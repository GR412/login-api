package com.mindera.login.models.api;

/**
 * This class defines the shape of a LoginRequest which is composed of a username and password.
 *
 * When a user logs in to the application, an instance of this object is created and is supplied to the UsersService
 * login method so the inputted details can be extracted and checked against the User table.
 */
public class LoginRequest {

    private String username;
    private String password;

    //Standard get and set methods

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}