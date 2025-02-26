package com.example.publicis.assignment.controller;

import com.example.publicis.assignment.model.User;
import com.example.publicis.assignment.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") @Email(message = "Invalid email format.") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(userService.searchUsers(keyword));
    }
}