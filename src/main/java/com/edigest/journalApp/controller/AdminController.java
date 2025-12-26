package com.edigest.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs")
public class AdminController {

    @Autowired
    private UserService userService;

    @Operation(summary = "View all the users and journal entries as an admin.")
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> all = userService.getAll();

        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create an user as an admin.")
    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user) {
        userService.saveAdmin(user);
    }
    
}
