package org.example.hotelmanagementip.strategy;

import org.example.hotelmanagementip.entity.Payment;
import org.example.hotelmanagementip.entity.Reservation;
import org.example.hotelmanagementip.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayPalPaymentStrategy implements PaymentStrategy {
    private String email;
    private String password;
    private Long paymentId;

    private final PaymentRepository paymentRepository;

    @Autowired
    public PayPalPaymentStrategy(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void setPayPalDetails(String email, String password) {
        this.email = email;
        this.password = password;
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
