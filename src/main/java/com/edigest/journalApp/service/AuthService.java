package com.edigest.journalApp.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.edigest.journalApp.utils.JwtUtil;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       UserDetailsServiceImpl userDetailsService,
                       JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);

        return jwtUtil.generateToken(userDetails.getUsername());
    }
}
