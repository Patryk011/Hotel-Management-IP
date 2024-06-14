package org.example.hotelmanagementip.model;

import org.example.hotelmanagementip.entity.Payment;
import org.example.hotelmanagementip.entity.Reservation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    void testPaymentNoArgsConstructor() {
        Payment payment = new Payment();
        assertNotNull(payment);
    }

    @Test
    void testPaymentAllArgsConstructor() {
        Reservation reservation = new Reservation();
        Payment payment = new Payment(1L, reservation, 100.0, true, "Completed");

        assertEquals(1L, payment.getId());
        assertEquals(reservation, payment.getReservation());
        assertEquals(100.0, payment.getAmount());
        assertTrue(payment.isPaid());
        assertEquals("Completed", payment.getStatus());
    }

    @Test
    void testSettersAndGetters() {
        Payment payment = new Payment();
        Reservation reservation = new Reservation();
        payment.setId(1L);
        payment.setReservation(reservation);
        payment.setAmount(100.0);
        payment.setPaid(true);
        payment.setStatus("Completed");

        assertEquals(1L, payment.getId());
        assertEquals(reservation, payment.getReservation());
        assertEquals(100.0, payment.getAmount());
        assertTrue(payment.isPaid());
        assertEquals("Completed", payment.getStatus());
    }

    @Test
    void testSetIsPaid() {
        Payment payment = new Payment();
        payment.setPaid(true);

        assertTrue(payment.isPaid());

        payment.setPaid(false);

        assertFalse(payment.isPaid());
    }
}

