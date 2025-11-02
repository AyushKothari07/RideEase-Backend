package com.rideease.user_service.service;

import com.rideease.user_service.dto.UserRequestDTO;
import com.rideease.user_service.dto.UserResponseDTO;
import com.rideease.user_service.entity.User;
import com.rideease.user_service.exception.UserNotFoundException;
import com.rideease.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO){
        log.info("Registering User: {}",userRequestDTO.getEmail());
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setRole(userRequestDTO.getRole());
        userRepository.save(user);
        return new UserResponseDTO(user.getId(),user.getName(),user.getEmail(),user.getRole());

    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        log.info("Getting User: {}",id);
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with id " + id));

        return new UserResponseDTO(user.getId(),user.getName(),user.getEmail(),user.getRole());
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        log.info("Getting All Users:");
        return userRepository.findAll().stream().map(u -> new UserResponseDTO(u.getId(),u.getName(),u.getEmail(),u.getRole())).collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long id) {
       log.info("Deleting User with id: {}",id);
       if(!userRepository.existsById(id)){
           throw new UserNotFoundException("User not found with id " + id);
       }
       userRepository.deleteById(id);
    }


}
