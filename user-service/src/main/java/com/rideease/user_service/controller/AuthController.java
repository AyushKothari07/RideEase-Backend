package com.rideease.user_service.controller;

import com.rideease.user_service.dto.JwTResponse;
import com.rideease.user_service.dto.LoginRequestDTO;
import com.rideease.user_service.entity.User;
import com.rideease.user_service.exception.UserNotFoundException;
import com.rideease.user_service.repository.UserRepository;
import com.rideease.user_service.response.ApiResponse;
import com.rideease.user_service.utility.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwTResponse>> login (@RequestBody LoginRequestDTO loginRequestDTO)
    {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new UserNotFoundException("User was not found"));

        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail(),user.getRole().name());
        JwTResponse jwTResponse = new JwTResponse(token, user.getRole().name(), user.getEmail());
        ApiResponse<JwTResponse> apiResponse = new ApiResponse<>(jwTResponse, "Login Successful", "success");
        return ResponseEntity.ok(apiResponse);

    }



}
