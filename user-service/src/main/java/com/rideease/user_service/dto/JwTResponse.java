package com.rideease.user_service.dto;

import com.rideease.user_service.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwTResponse {
    private String token;
    private String role;
    private String email;
}
