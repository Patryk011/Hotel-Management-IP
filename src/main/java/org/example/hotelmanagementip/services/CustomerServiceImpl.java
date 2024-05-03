package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.CustomerDTO;
import org.example.hotelmanagementip.entity.Customer;
import org.example.hotelmanagementip.exception.CustomerException;
import org.example.hotelmanagementip.mapper.CustomerMapper;
import org.example.hotelmanagementip.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerServiceImpl implements CustomerService {



    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;


    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        return customerMapper.mapToDto(customerRepository.findAll());
    }


    @Override
    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerException("Customer with ID " + customerId + " not found."));



        existingCustomer = customerMapper.mapFromDto(existingCustomer, customerDTO);
        customerRepository.save(existingCustomer);
        return customerMapper.mapToDto(existingCustomer);
    }
    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.mapFromDto(customerDTO);
        customer = customerRepository.save(customer);

        return customerMapper.mapToDto(customer);
    }

    @Override
    public CustomerDTO findById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return customerMapper.mapToDto(customer);
    }

    @Override
    public List<CustomerDTO> findCustomersByEmail(String email) {
        List<CustomerDTO> filteredCustomers = new ArrayList<>();
        List<CustomerDTO> allCustomers = customerMapper.mapToDto(customerRepository.findAll());

        for (CustomerDTO customer : allCustomers) {
            if (customer.getEmail().equals(email)) {
                filteredCustomers.add(customer);
            }
        }

        return filteredCustomers;
    }

    @Override
    public CustomerDTO findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        return customerMapper.mapToDto(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
