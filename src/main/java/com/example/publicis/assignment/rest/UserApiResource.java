package com.example.publicis.assignment.rest;

import com.example.publicis.assignment.api.UserApi;
import com.example.publicis.assignment.interfaces.IUserController;
import com.example.publicis.assignment.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApiResource implements UserApi {
    @Autowired
    private IUserController userController;

    @Override
    public ResponseEntity<UserResponse> getUserByEmail(String email) {
        return userController.getUserByEmail(email);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(Long id) {
        return userController.getUserById(id);
    }

    @Override
    public ResponseEntity<List<UserResponse>> searchUser(String keyword) {
        return userController.searchUsers(keyword);
    }
}
