package org.example.hotelmanagementip.repository;

import org.example.hotelmanagementip.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


    List<Payment> findByReservationId(Long reservationId);

}