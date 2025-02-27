package com.example.publicis.assignment.interfaces;

import com.example.publicis.assignment.dto.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserController {
    ResponseEntity<UserResponse> getUserById(Long id);

    ResponseEntity<UserResponse> getUserByEmail(String email);

    ResponseEntity<List<UserResponse>> searchUsers(String keyword);
}
