package com.mindera.login.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static java.util.Collections.emptyList;

public class TokenAuthentication extends UsernamePasswordAuthenticationToken {

    public TokenAuthentication(String username) {
        super(username, null, emptyList());
    }

}
