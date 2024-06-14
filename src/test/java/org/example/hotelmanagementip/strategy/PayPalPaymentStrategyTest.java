package org.example.hotelmanagementip.strategy;

import org.example.hotelmanagementip.entity.Payment;
import org.example.hotelmanagementip.entity.Reservation;
import org.example.hotelmanagementip.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PayPalPaymentStrategyTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PayPalPaymentStrategy payPalPaymentStrategy;

    private Payment payment;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        payment = new Payment();
        payment.setId(1L);
        reservation = new Reservation();
        payment.setReservation(reservation);

        payPalPaymentStrategy.setPaymentId(1L);
    }

    @Test
    void testUpdatePaymentStatus() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        payPalPaymentStrategy.pay(100.0);

        assertTrue(payment.isPaid());
        assertEquals("Confirmed", payment.getStatus());
        assertEquals("Paid", reservation.getStatus());

        verify(paymentRepository).save(payment);
    }

    @Test
    void testUpdatePaymentStatus_PaymentNotFound() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            payPalPaymentStrategy.pay(100.0);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid payment ID", e.getMessage());
        }

        verify(paymentRepository, never()).save(any(Payment.class));
    }

}
