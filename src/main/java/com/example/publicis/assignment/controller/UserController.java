package com.example.publicis.assignment.controller;

import com.example.publicis.assignment.interfaces.IUserController;
import com.example.publicis.assignment.dto.UserResponse;
import com.example.publicis.assignment.interfaces.UserService;
import com.example.publicis.assignment.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController implements IUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseEntity<UserResponse> getUserById(Long id) {
        return ResponseEntity.ok(userMapper.toUserResponse(userService.getUserById(id)));
    }

    @Override
    public ResponseEntity<UserResponse> getUserByEmail(String email) {
        return ResponseEntity.ok(userMapper.toUserResponse(userService.getUserByEmail(email)));
    }

    @Override
    public ResponseEntity<List<UserResponse>> searchUsers(String keyword) {
        return ResponseEntity.ok(userService.searchUsers(keyword).
                stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<Void> loadUsersFromExternalApi() {
        userService.loadUsersFromExternalApi();
        return ResponseEntity.ok().build();
    }
}