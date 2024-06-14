package org.example.hotelmanagementip.controllers;

import org.example.hotelmanagementip.controller.PaymentController;
import org.example.hotelmanagementip.dto.PaymentDTO;
import org.example.hotelmanagementip.services.PaymentService;
import org.example.hotelmanagementip.strategy.CreditCardPaymentStrategy;
import org.example.hotelmanagementip.strategy.PayPalPaymentStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @Mock
    private CreditCardPaymentStrategy creditCardPaymentStrategy;

    @Mock
    private PayPalPaymentStrategy payPalPaymentStrategy;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPayments() {
        List<PaymentDTO> payments = new ArrayList<>();
        payments.add(new PaymentDTO());
        when(paymentService.getAllPayments()).thenReturn(payments);

        List<PaymentDTO> result = paymentController.getAllPayments();

        assertEquals(1, result.size());
        verify(paymentService, times(1)).getAllPayments();
    }

    @Test
    void testMarkPaymentAsPaid() {
        PaymentDTO payment = new PaymentDTO();
        when(paymentService.markPaymentAsPaid(1L)).thenReturn(payment);

        PaymentDTO result = paymentController.markPaymentAsPaid(1L);

        assertEquals(payment, result);
        verify(paymentService, times(1)).markPaymentAsPaid(1L);
    }

    @Test
    void testMarkPaymentAsUnPaid() {
        PaymentDTO payment = new PaymentDTO();
        when(paymentService.markPaymentAsUnPaid(1L)).thenReturn(payment);

        PaymentDTO result = paymentController.markPaymentAsUnPaid(1L);

        assertEquals(payment, result);
        verify(paymentService, times(1)).markPaymentAsUnPaid(1L);
    }

    @Test
    void testPayWithCreditCard() {
        Map<String, String> cardDetails = Map.of(
                "cardNumber", "1234567890123456",
                "cardHolderName", "John Doe",
                "cvv", "123",
                "expirationDate", "12/24"
        );

        doNothing().when(creditCardPaymentStrategy).setCardDetails(
                cardDetails.get("cardNumber"),
                cardDetails.get("cardHolderName"),
                cardDetails.get("cvv"),
                cardDetails.get("expirationDate")
        );

        doNothing().when(paymentService).processPayment(1L, creditCardPaymentStrategy);

        paymentController.payWithCreditCard(1L, cardDetails);

        verify(creditCardPaymentStrategy, times(1)).setCardDetails(
                cardDetails.get("cardNumber"),
                cardDetails.get("cardHolderName"),
                cardDetails.get("cvv"),
                cardDetails.get("expirationDate")
        );
        verify(paymentService, times(1)).processPayment(1L, creditCardPaymentStrategy);
    }

    @Test
    void testPayWithPayPal() {
        Map<String, String> payPalDetails = Map.of(
                "email", "john.doe@example.com",
                "password", "securepassword"
        );

        doNothing().when(payPalPaymentStrategy).setPayPalDetails(
                payPalDetails.get("email"),
                payPalDetails.get("password")
        );

        doNothing().when(paymentService).processPayment(1L, payPalPaymentStrategy);

        paymentController.payWithPayPal(1L, payPalDetails);

        verify(payPalPaymentStrategy, times(1)).setPayPalDetails(
                payPalDetails.get("email"),
                payPalDetails.get("password")
        );
        verify(paymentService, times(1)).processPayment(1L, payPalPaymentStrategy);
    }
}
