package com.mindera.login.security;

import com.mindera.login.models.database.User;
import com.mindera.login.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.isNull;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Component
public class AuthenticationFilter extends GenericFilterBean {

    @Autowired
    private UsersService usersService;

    public AuthenticationFilter() {
        super();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        authenticate((HttpServletRequest) request, (HttpServletResponse) response);
        filterChain.doFilter(request, response);
    }

    public void authenticate(HttpServletRequest request, HttpServletResponse response) {
        TokenAuthentication authentication = null;
        String token = request.getHeader("X-Auth-Token");

        User user = usersService.verifyToken(token);

        if (!isNull(user)) {
            authentication = new TokenAuthentication(user.getUsername());
        }

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

}