package com.rideease.user_service.controller;

import com.rideease.user_service.dto.UserRequestDTO;
import com.rideease.user_service.dto.UserResponseDTO;
import com.rideease.user_service.entity.User;
import com.rideease.user_service.response.ApiResponse;
import com.rideease.user_service.service.UserService;
import com.rideease.user_service.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {


    private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser( @Valid  @RequestBody UserRequestDTO userRequestDTO){
        log.info("Creating new User with email: {}", userRequestDTO.getEmail());
        UserResponseDTO saved = userService.registerUser(userRequestDTO);
        return ResponseEntity.ok(new ApiResponse<>(saved,"User Registered Successfully", "SUCCESS"));
    }

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers(){
       List<UserResponseDTO> users = userService.getAllUsers();
       return ResponseEntity.ok(new ApiResponse<>(users,"All Users fetched Successfully", "SUCCESS"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Long id){
        UserResponseDTO userResponseDTO = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(userResponseDTO,"User Found Successfully", "SUCCESS"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(null,"User Deleted Successfully", "SUCCESS"));
    }
}
