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
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }

        if (!userRepository.existsByUsername("worker")) {
            User worker = new User();
            worker.setUsername("worker");
            worker.setPassword(passwordEncoder.encode("worker"));
            worker.setRole(Role.WORKER);
            userRepository.save(worker);
        }

//        if (!userRepository.existsByUsername("receptionist")) {
//            User receptionist = new User();
//            receptionist.setUsername("receptionist");
//            receptionist.setPassword(passwordEncoder.encode("receptionist"));
//            receptionist.setRole(Role.RECEPTIONIST);
//            userRepository.save(receptionist);
//        }
//
//        if (!userRepository.existsByUsername("cleaner")) {
//            User cleaner = new User();
//            cleaner.setUsername("cleaner");
//            cleaner.setPassword(passwordEncoder.encode("cleaner"));
//            cleaner.setRole(Role.CLEANER);
//            userRepository.save(cleaner);
//        }
    }
}
