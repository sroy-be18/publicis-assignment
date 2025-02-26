package com.example.publicis.assignment;

import com.example.publicis.assignment.model.User;
import com.example.publicis.assignment.repository.UserRepository;
import com.example.publicis.assignment.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById_success() {
        User user = new User(1L, "John", "Doe", 30, "male", "john.doe@example.com", "+1 555-1234", "1993-01-15", "123-45-6789");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void getUserByEmail_success() {
        User user = new User(2L, "Jane", "Smith", 28, "female", "jane.smith@example.com", "+1 555-5678", "1995-03-22", "987-65-4321");
        when(userRepository.findByEmail("jane.smith@example.com")).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail("jane.smith@example.com");

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getUserById(999L));
        assertTrue(exception.getMessage().contains("User not found"));
    }
}
