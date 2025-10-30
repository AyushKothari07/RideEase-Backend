package com.rideease.user_service.controller;

import com.rideease.user_service.entity.User;
import com.rideease.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        User saved = userService.registerUser(user);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/test")
    public String test(){
        return "User Service is running";
    }
}
