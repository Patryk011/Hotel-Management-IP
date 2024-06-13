package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.entity.Role;
import org.example.hotelmanagementip.entity.User;
import org.example.hotelmanagementip.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_Success() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        user.setPassword("password123");
        user.setRole(Role.WORKER);

        when(userRepository.findByUsername(username)).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertIterableEquals(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())), userDetails.getAuthorities());

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "nonexistentuser";

        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));

        verify(userRepository, times(1)).findByUsername(username);
    }
}
