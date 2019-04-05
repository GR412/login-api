package com.mindera.login.security;

import com.mindera.login.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends GenericFilterBean {

    @Autowired
    private UsersService usersService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        authenticate((HttpServletRequest) request, (HttpServletResponse) response);
        filterChain.doFilter(request, response);
    }

    public void authenticate(HttpServletRequest request, HttpServletResponse response) {
        TokenAuthentication authentication = null;
        String token = request.getHeader("X-Auth-Token");


        // TODO
        // - load session and user using token
        // - make sure token is not expired
        // - if all good then
        // authentication = new TokenAuthentication(username);


        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}