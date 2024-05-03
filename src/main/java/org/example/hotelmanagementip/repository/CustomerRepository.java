package org.example.hotelmanagementip.repository;

import org.example.hotelmanagementip.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Customer findByEmail(String email);
    List<Customer> findCustomersByEmail(String email);




}