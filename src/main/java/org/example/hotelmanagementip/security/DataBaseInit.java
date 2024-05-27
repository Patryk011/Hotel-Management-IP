package org.example.hotelmanagementip.security;



import org.example.hotelmanagementip.entity.Role;
import org.example.hotelmanagementip.entity.User;
import org.example.hotelmanagementip.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DataBaseInit implements CommandLineRunner {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(Role.ADMIN);

        User worker = new User();
        worker.setUsername("worker");
        worker.setPassword(passwordEncoder.encode("worker"));
        worker.setRole(Role.WORKER);

        userRepository.save(admin);
        userRepository.save(worker);

    }
}