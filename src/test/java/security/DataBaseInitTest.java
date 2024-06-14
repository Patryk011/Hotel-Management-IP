package security;



import org.example.hotelmanagementip.entity.Role;
import org.example.hotelmanagementip.entity.User;
import org.example.hotelmanagementip.repository.UserRepository;
import org.example.hotelmanagementip.security.DataBaseInit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class DataBaseInitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataBaseInit dataBaseInit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRun() {
        when(userRepository.existsByUsername("admin")).thenReturn(false);
        when(userRepository.existsByUsername("worker")).thenReturn(false);
        when(userRepository.existsByUsername("receptionist")).thenReturn(false);
        when(userRepository.existsByUsername("cleaner")).thenReturn(false);

        when(passwordEncoder.encode(any(CharSequence.class))).thenAnswer(invocation -> invocation.getArgument(0).toString());

        dataBaseInit.run();

        verify(userRepository, times(1)).save(argThat(user ->
                user.getUsername().equals("admin") &&
                        user.getRole() == Role.ADMIN &&
                        user.getPassword().equals("admin")
        ));

        verify(userRepository, times(1)).save(argThat(user ->
                user.getUsername().equals("worker") &&
                        user.getRole() == Role.WORKER &&
                        user.getPassword().equals("worker")
        ));

        verify(userRepository, times(1)).save(argThat(user ->
                user.getUsername().equals("receptionist") &&
                        user.getRole() == Role.RECEPTIONIST &&
                        user.getPassword().equals("receptionist")
        ));

        verify(userRepository, times(1)).save(argThat(user ->
                user.getUsername().equals("cleaner") &&
                        user.getRole() == Role.CLEANER &&
                        user.getPassword().equals("cleaner")
        ));
    }

    @Test
    void testRunUsersAlreadyExist() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        dataBaseInit.run();

        verify(userRepository, never()).save(any(User.class));
    }
}
