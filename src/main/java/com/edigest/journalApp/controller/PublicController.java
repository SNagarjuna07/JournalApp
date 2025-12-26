package com.edigest.journalApp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.edigest.journalApp.dto.LoginDto;
import com.edigest.journalApp.dto.UserDto;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.service.AuthService;
import com.edigest.journalApp.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/public")
@Tag(name = "Public APIs", description = "Signup, Login and health-check the app.")
public class PublicController {

    private final AuthService authService;
    private final UserService userService;

    public PublicController(AuthService authService,
                            UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Operation(summary = "Checking the health of the application.")
    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @Operation(summary = "Sign up a new user.")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto user) {

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword()); 
        newUser.setCity(user.getCity());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());

        userService.saveNewUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Log in an existing user.")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto user) {
        try {
            String token = authService.login(
                    user.getUserName(),
                    user.getPassword()
            );
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }
}
