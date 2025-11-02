package com.rideease.user_service.service;

import com.rideease.user_service.dto.UserRequestDTO;
import com.rideease.user_service.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);
    UserResponseDTO getUserById(Long id);
    List<UserResponseDTO> getAllUsers();
    void deleteUserById(Long id);
}
