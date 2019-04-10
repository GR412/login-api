package com.mindera.login.models.api;

/**
 * This class defines the shape of a UserResponse which is composed of an id, username, email and password.
 *
 * It is used as a return in the UsersService methods instead of a normal User object. This is due to a User and Session
 * entities having a one to one relationship to each other, which can cause an infinite recursion error when a normal
 * User is returned.
 */
public class UserResponse {

    private int id;
    private String username;
    private String email;
    private String password;

    /**
     * Constructor that assigns the class fields with the incoming parameters.
     *
     * @param id a UserResponse must have a supplied user ID.
     * @param username a UserResponse must have a supplied username.
     * @param email a UserResponse must have a supplied email.
     * @param password a UserResponse must have a supplied password.
     */
    public UserResponse(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    //Standard get and set methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
