package org.example.hotelmanagementip.controller;

import org.example.hotelmanagementip.dto.PaymentDTO;
import org.example.hotelmanagementip.services.PaymentService;
import org.example.hotelmanagementip.strategy.CreditCardPaymentStrategy;
import org.example.hotelmanagementip.strategy.PayPalPaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final CreditCardPaymentStrategy creditCardPaymentStrategy;
    private final PayPalPaymentStrategy payPalPaymentStrategy;

    @Autowired
    public PaymentController(PaymentService paymentService, CreditCardPaymentStrategy creditCardPaymentStrategy,
                             PayPalPaymentStrategy payPalPaymentStrategy) {
        this.paymentService = paymentService;
        this.creditCardPaymentStrategy = creditCardPaymentStrategy;
        this.payPalPaymentStrategy = payPalPaymentStrategy;
    }

    @GetMapping("/all")
    public List<PaymentDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @PutMapping("/{id}/markAsPaid")
    public PaymentDTO markPaymentAsPaid(@PathVariable Long id) {
        return paymentService.markPaymentAsPaid(id);
    }

    @PutMapping("/{id}/markAsUnpaid")
    public PaymentDTO markPaymentAsUnPaid(@PathVariable Long id) {
        return paymentService.markPaymentAsUnPaid(id);
    }

    @PostMapping("/{id}/payWithCreditCard")
    public void payWithCreditCard(@PathVariable Long id, @RequestBody Map<String, String> cardDetails) {
        creditCardPaymentStrategy.setCardDetails(
                cardDetails.get("cardNumber"),
                cardDetails.get("cardHolderName"),
                cardDetails.get("cvv"),
                cardDetails.get("expirationDate")
        );
        paymentService.processPayment(id, creditCardPaymentStrategy);
    }

    @PostMapping("/{id}/payWithPayPal")
    public void payWithPayPal(@PathVariable Long id, @RequestBody Map<String, String> payPalDetails) {
        payPalPaymentStrategy.setPayPalDetails(
                payPalDetails.get("email"),
                payPalDetails.get("password")
        );
        paymentService.processPayment(id, payPalPaymentStrategy);
    }
}

