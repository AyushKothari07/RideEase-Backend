package com.rideease.user_service.exception;

import com.rideease.user_service.dto.UserResponseDTO;
import com.rideease.user_service.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFound(UserNotFoundException ex){
        return new ResponseEntity<>(new ApiResponse<>(null, ex.getMessage(),"Failure"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationErrors(MethodArgumentNotValidException ex){
        String errorMsg = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity<>(new ApiResponse<>(null,errorMsg, "Failure"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneric(Exception ex)
    {
        return new ResponseEntity<>(new ApiResponse<>(null,ex.getMessage(),"Failure"), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
