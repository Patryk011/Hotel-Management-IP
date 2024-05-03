package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {


    List<CustomerDTO> findAllCustomers();


    CustomerDTO addCustomer(CustomerDTO customer);
    CustomerDTO findById(Long id);

    List<CustomerDTO> findCustomersByEmail(String email);
    CustomerDTO findByEmail(String email);

    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);

    void deleteCustomer(Long id);


}
