package com.example.publicis.assignment;

import com.example.publicis.assignment.controller.UserController;
import com.example.publicis.assignment.exception.GlobalExceptionHandler;
import com.example.publicis.assignment.exception.UserNotFoundException;
import com.example.publicis.assignment.model.User;
import com.example.publicis.assignment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getUserById_success() throws Exception {
        User user = new User(1L, "John", "Doe", 30, "male", "john.doe@example.com", "+1 555-1234", "1993-01-15", "123-45-6789");
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getUserById_notFound() throws Exception {
        when(userService.getUserById(anyLong())).thenThrow(new UserNotFoundException("User not found with id: 999"));

        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id: 999"));
    }


    @Test
    void searchUsers_notFound() throws Exception {
        when(userService.searchUsers("unknown")).thenThrow(new UserNotFoundException("No users found matching keyword: unknown"));

        mockMvc.perform(get("/api/users/search").param("keyword", "unknown"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No users found matching keyword: unknown"));
    }
}
