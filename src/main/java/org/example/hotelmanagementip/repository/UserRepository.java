package org.example.hotelmanagementip.repository;

import org.example.hotelmanagementip.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}