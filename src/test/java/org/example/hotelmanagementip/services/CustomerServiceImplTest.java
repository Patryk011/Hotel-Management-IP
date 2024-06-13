package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.CustomerDTO;
import org.example.hotelmanagementip.entity.Customer;
import org.example.hotelmanagementip.exception.CustomerException;
import org.example.hotelmanagementip.mapper.CustomerMapper;
import org.example.hotelmanagementip.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllCustomers() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        List<CustomerDTO> customerDTOs = Arrays.asList(new CustomerDTO(), new CustomerDTO());

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerMapper.mapToDto(customers)).thenReturn(customerDTOs);

        List<CustomerDTO> result = customerService.findAllCustomers();

        assertEquals(customerDTOs, result);
        verify(customerRepository, times(1)).findAll();
        verify(customerMapper, times(1)).mapToDto(customers);
    }

    @Test
    void testUpdateCustomer_Success() {
        Long customerId = 1L;
        CustomerDTO customerDTO = new CustomerDTO();
        Customer existingCustomer = new Customer();
        Customer updatedCustomer = new Customer();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerMapper.mapFromDto(existingCustomer, customerDTO)).thenReturn(updatedCustomer);
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);
        when(customerMapper.mapToDto(updatedCustomer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.updateCustomer(customerId, customerDTO);

        assertEquals(customerDTO, result);
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerMapper, times(1)).mapFromDto(existingCustomer, customerDTO);
        verify(customerRepository, times(1)).save(updatedCustomer);
        verify(customerMapper, times(1)).mapToDto(updatedCustomer);
    }

    @Test
    void testUpdateCustomer_NotFound() {
        Long customerId = 1L;
        CustomerDTO customerDTO = new CustomerDTO();

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerException.class, () -> customerService.updateCustomer(customerId, customerDTO));
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerMapper, never()).mapFromDto(any(), any());
        verify(customerRepository, never()).save(any());
        verify(customerMapper, never()).mapToDto((Customer) any());
    }

    @Test
    void testAddCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        Customer customer = new Customer();
        Customer savedCustomer = new Customer();

        when(customerMapper.mapFromDto(customerDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(savedCustomer);
        when(customerMapper.mapToDto(savedCustomer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.addCustomer(customerDTO);

        assertEquals(customerDTO, result);
        verify(customerMapper, times(1)).mapFromDto(customerDTO);
        verify(customerRepository, times(1)).save(customer);
        verify(customerMapper, times(1)).mapToDto(savedCustomer);
    }

    @Test
    void testFindById_Success() {
        Long customerId = 1L;
        Customer customer = new Customer();
        CustomerDTO customerDTO = new CustomerDTO();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerMapper.mapToDto(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.findById(customerId);

        assertEquals(customerDTO, result);
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerMapper, times(1)).mapToDto(customer);
    }

    @Test
    void testFindById_NotFound() {
        Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> customerService.findById(customerId));
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerMapper, never()).mapToDto((Customer) any());
    }

    @Test
    void testFindCustomersByEmail() {
        String email = "test@example.com";
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        List<CustomerDTO> customerDTOs = Arrays.asList(new CustomerDTO(), new CustomerDTO());

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerMapper.mapToDto(customers)).thenReturn(customerDTOs);

        List<CustomerDTO> result = customerService.findCustomersByEmail(email);

        for (CustomerDTO customerDTO : result) {
            assertEquals(email, customerDTO.getEmail());
        }
        verify(customerRepository, times(1)).findAll();
        verify(customerMapper, times(1)).mapToDto(customers);
    }

    @Test
    void testFindByEmail() {
        String email = "test@example.com";
        Customer customer = new Customer();
        CustomerDTO customerDTO = new CustomerDTO();

        when(customerRepository.findByEmail(email)).thenReturn(customer);
        when(customerMapper.mapToDto(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.findByEmail(email);

        assertEquals(customerDTO, result);
        verify(customerRepository, times(1)).findByEmail(email);
        verify(customerMapper, times(1)).mapToDto(customer);
    }

    @Test
    void testDeleteCustomer() {
        Long customerId = 1L;

        doNothing().when(customerRepository).deleteById(customerId);

        customerService.deleteCustomer(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);
    }
}
