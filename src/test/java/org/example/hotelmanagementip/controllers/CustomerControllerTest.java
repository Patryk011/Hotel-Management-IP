package org.example.hotelmanagementip.controllers;

import org.example.hotelmanagementip.controller.CustomerController;
import org.example.hotelmanagementip.dto.CustomerDTO;
import org.example.hotelmanagementip.dto.PaymentDTO;
import org.example.hotelmanagementip.dto.ReservationDTO;
import org.example.hotelmanagementip.services.CustomerService;
import org.example.hotelmanagementip.services.PaymentService;
import org.example.hotelmanagementip.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private ReservationService reservationService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomers() {
        List<CustomerDTO> customers = new ArrayList<>();
        customers.add(new CustomerDTO());
        when(customerService.findAllCustomers()).thenReturn(customers);

        List<CustomerDTO> result = customerController.getAllCustomers();

        assertEquals(1, result.size());
        verify(customerService, times(1)).findAllCustomers();
    }

    @Test
    void testFindByCustomerId() {
        List<ReservationDTO> reservations = new ArrayList<>();
        reservations.add(new ReservationDTO());
        when(reservationService.findByCustomerId(1L)).thenReturn(reservations);

        List<ReservationDTO> result = customerController.findByCustomerId(1L);

        assertEquals(1, result.size());
        verify(reservationService, times(1)).findByCustomerId(1L);
    }

    @Test
    void testFindPaymentByCustomerId() {
        List<ReservationDTO> reservations = new ArrayList<>();
        ReservationDTO reservation = new ReservationDTO();
        reservation.setId(1L);
        reservations.add(reservation);

        List<PaymentDTO> payments = new ArrayList<>();
        payments.add(new PaymentDTO());
        when(reservationService.findByCustomerId(1L)).thenReturn(reservations);
        when(paymentService.findPaymentsByReservationId(1L)).thenReturn(payments);

        List<PaymentDTO> result = customerController.findPaymentByCustomerId(1L);

        assertEquals(1, result.size());
        verify(reservationService, times(1)).findByCustomerId(1L);
        verify(paymentService, times(1)).findPaymentsByReservationId(1L);
    }

    @Test
    void testGetCustomerById() {
        CustomerDTO customer = new CustomerDTO();
        when(customerService.findById(1L)).thenReturn(customer);

        CustomerDTO result = customerController.getCustomerById(1L);

        assertEquals(customer, result);
        verify(customerService, times(1)).findById(1L);
    }

    @Test
    void testGetCustomersByEmail() {
        List<CustomerDTO> customers = new ArrayList<>();
        customers.add(new CustomerDTO());
        when(customerService.findCustomersByEmail("test@example.com")).thenReturn(customers);

        List<CustomerDTO> result = customerController.getCustomersByEmail("test@example.com");

        assertEquals(1, result.size());
        verify(customerService, times(1)).findCustomersByEmail("test@example.com");
    }

    @Test
    void testAddCustomer() {
        CustomerDTO customer = new CustomerDTO();
        when(customerService.addCustomer(customer)).thenReturn(customer);

        CustomerDTO result = customerController.addCustomer(customer);

        assertEquals(customer, result);
        verify(customerService, times(1)).addCustomer(customer);
    }

    @Test
    void testUpdateCustomer() {
        CustomerDTO customer = new CustomerDTO();
        when(customerService.updateCustomer(1L, customer)).thenReturn(customer);

        CustomerDTO result = customerController.updateCustomer(1L, customer);

        assertEquals(customer, result);
        verify(customerService, times(1)).updateCustomer(1L, customer);
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerService).deleteCustomer(1L);

        customerController.deleteCustomer(1L);

        verify(customerService, times(1)).deleteCustomer(1L);
    }
}