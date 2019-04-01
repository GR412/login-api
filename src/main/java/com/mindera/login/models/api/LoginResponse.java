package com.mindera.login.models.api;

/**
 * This class defines the shape of a LoginResponse which is composed of just an auth token.
 *
 * If the username and password are satisfied from the LoginRequest then an auth token is generated and stored in an
 * instance of this class.
 */

public class LoginResponse
{

    private String authToken;

    /**
     * Default constructor
     */

    public LoginResponse()
    {

    }

    /**
     * Constructor that assigns the class field authToken with the incoming authToken parameter.
     *
     * @param authToken a login response must have a supplied auth token used for authentication purposes.
     */

    public LoginResponse(String authToken)
    {
        this.authToken = authToken;
    }

    //Standard get and set methods

    public String getAuthToken()
    {
        return authToken;
    }

    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }

}