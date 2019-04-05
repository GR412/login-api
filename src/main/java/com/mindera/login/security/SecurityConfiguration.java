package com.mindera.login.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // instruct spring framework do do not use session policy - we will do that on our own
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // on error sent 401
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()

                // register filter that verifies authentication
                .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // then set up paths
                .authorizeRequests() //all requests
                .antMatchers(HttpMethod.POST, "/login").permitAll() //that match a route to the login page
                .anyRequest() //are allowed by non-authenticated users
                .authenticated(); //else if the request doesn't match the /login path then a user must be authenticated
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> registerFilter(@Autowired AuthenticationFilter authenticationFilter) {
        FilterRegistrationBean<AuthenticationFilter> filterRegistrationBean = new FilterRegistrationBean<>(authenticationFilter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }

}
