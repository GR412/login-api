package com.mindera.login.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static java.util.Collections.singletonList;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                // register filter that verifies authentication
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // instruct spring framework do do not use session policy - we will do that on our own
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()

                // open requests
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.POST, "/authentication/login").permitAll()

                // anything else authenticated
                .anyRequest()
                .authenticated()  //that match a route to the login page

                .and().cors();
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> registerFilter(@Autowired AuthenticationFilter authenticationFilter) {
        FilterRegistrationBean<AuthenticationFilter> filterRegistrationBean = new FilterRegistrationBean<>(authenticationFilter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(singletonList("*"));
        configuration.setAllowedOrigins(singletonList("*"));
        configuration.setAllowedHeaders(singletonList("*"));
        configuration.setMaxAge(1800L);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
