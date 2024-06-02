package org.example.hotelmanagementip.strategy;

import org.example.hotelmanagementip.entity.Payment;
import org.example.hotelmanagementip.entity.Reservation;
import org.example.hotelmanagementip.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditCardPaymentStrategy implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;
    private String cvv;
    private String expirationDate;
    private Long paymentId;

    private final PaymentRepository paymentRepository;

    @Autowired
    public CreditCardPaymentStrategy(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void setCardDetails(String cardNumber, String cardHolderName, String cvv, String expirationDate) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public void pay(double amount) {




        updatePaymentStatus();
    }



    private void updatePaymentStatus() {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment ID"));

        payment.setPaid(true);
        payment.setStatus("Confirmed");
        paymentRepository.save(payment);

        Reservation reservation = payment.getReservation();
        if (reservation != null) {
            reservation.setStatus("Paid");
        } else {
            System.err.println("Reservation is null for payment with ID: " + paymentId);
        }
        System.out.println("Payment status updated to 'Confirmed'");
    }
}