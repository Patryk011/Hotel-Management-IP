package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.PaymentDTO;
import org.example.hotelmanagementip.strategy.PaymentStrategy;

import java.util.Date;
import java.util.List;

public interface PaymentService {



    List<PaymentDTO> getAllPayments();
    PaymentDTO findById(Long id);

    List<PaymentDTO> findPaymentsByReservationId(Long reservationId);

    PaymentDTO createPayment(Long reservationId);



    PaymentDTO markPaymentAsPaid(Long paymentId);

    PaymentDTO markPaymentAsUnPaid(Long paymentId);


    PaymentDTO updatePayment(Long reservationId, Long newRoomId, Date newStartDate, Date newEndDate);

    void deletePayment(Long id);



    int calculateDuration(Date startDate, Date endDate);

    void processPayment(Long paymentId, PaymentStrategy paymentStrategy);
}
