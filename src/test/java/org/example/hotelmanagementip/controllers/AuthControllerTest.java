package org.example.hotelmanagementip.controllers;

import org.example.hotelmanagementip.controller.AuthController;
import org.example.hotelmanagementip.entity.User;
import org.example.hotelmanagementip.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        User authenticatedUser = new User();
        authenticatedUser.setUsername("testUser");
        authenticatedUser.setPassword("password");

        when(userRepository.findByUsername("testUser")).thenReturn(authenticatedUser);

        ResponseEntity<User> response = authController.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticatedUser, response.getBody());
        verify(authenticationManager, times(1)).authenticate(
                new UsernamePasswordAuthenticationToken("testUser", "password")
        );
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testLoginFailure() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("wrongPassword");

        doThrow(new BadCredentialsException("Incorrect username or password")).when(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("testUser", "wrongPassword")
        );

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authController.login(user);
        });


        assertEquals("401 UNAUTHORIZED \"Incorrect username or password\"", exception.getMessage());
        verify(authenticationManager, times(1)).authenticate(
                new UsernamePasswordAuthenticationToken("testUser", "wrongPassword")
        );
        verify(userRepository, never()).findByUsername(anyString());
    }
}
