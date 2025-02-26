package com.example.publicis.assignment.service;

import com.example.publicis.assignment.model.User;

import java.util.List;

public interface UserService {
    void loadUsersFromExternalApi();
    User getUserById(Long id);
    User getUserByEmail(String email);
    List<User> searchUsers(String keyword);
}
