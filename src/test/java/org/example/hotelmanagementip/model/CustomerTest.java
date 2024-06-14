package org.example.hotelmanagementip.model;

import org.example.hotelmanagementip.entity.Customer;
import org.example.hotelmanagementip.entity.Reservation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerTest {

    @Test
    void testCustomerNoArgsConstructor() {
        Customer customer = new Customer();
        assertNotNull(customer);
    }

    @Test
    void testCustomerAllArgsConstructor() {
        List<Reservation> reservations = new ArrayList<>();
        Customer customer = new Customer(1L, "John", "Doe", "john.doe@example.com", reservations);

        assertEquals(1L, customer.getId());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals(reservations, customer.getReservations());
    }

    @Test
    void testSettersAndGetters() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");

        List<Reservation> reservations = new ArrayList<>();
        customer.setReservations(reservations);

        assertEquals(1L, customer.getId());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals(reservations, customer.getReservations());
    }

    @Test
    void testAddReservation() {
        Customer customer = new Customer();
        Reservation reservation = new Reservation();
        customer.getReservations().add(reservation);

        assertEquals(1, customer.getReservations().size());
        assertEquals(reservation, customer.getReservations().get(0));
    }

    @Test
    void testRemoveReservation() {
        Customer customer = new Customer();
        Reservation reservation = new Reservation();
        customer.getReservations().add(reservation);
        customer.getReservations().remove(reservation);

        assertEquals(0, customer.getReservations().size());
    }
}
