package com.example.publicis.assignment.runner;

import com.example.publicis.assignment.interfaces.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;

    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        userService.loadUsersFromExternalApi();
        System.out.println("Users loaded into H2 database on startup.");
    }
}
