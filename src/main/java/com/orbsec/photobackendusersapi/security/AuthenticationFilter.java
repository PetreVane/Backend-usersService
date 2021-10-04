package com.orbsec.photobackendusersapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbsec.photobackendusersapi.domain.models.LoginModel;
import com.orbsec.photobackendusersapi.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Environment environment;
    private final UserService userService;


    public AuthenticationFilter(Environment environment, UserService userService, AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        this.environment = environment;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            // fetch credentials submitted by the user and map them to your LoginModel
            LoginModel credentials = new ObjectMapper().readValue(request.getInputStream(), LoginModel.class);

            // build an authenticationToken object using these credentials. This comes from Spring Security. Read the documentation for why an empty array is passed to constructor.
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), new ArrayList<>());

            // call the authentication manager and pass the token to its authentication method
            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // start by getting the principal
        // Principal: The identity of the principal being authenticated. In the case of an authentication request with username and password, this would be the username.
        // auth.getPrincipal returns an Object. You need to cast that Object as User (Models core user information retrieved by a UserDetailsService, from Spring Security).
        // Once you obtain the User object, call the getUserName() which will return the email address the user has used for authentication.
        String userEmailAddress = ((User) authResult.getPrincipal()).getUsername();

        // Use the email address to fetch the User object from database
        var existingUser = userService.findUserByEmail(userEmailAddress).get();

        // create a JWT Token using the user id.
        String token = Jwts.builder().setSubject(existingUser.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret")).compact();

        // add it to the response header
        response.addHeader("token", token);
        response.addHeader("userId", existingUser.getUserId());
    }
}
