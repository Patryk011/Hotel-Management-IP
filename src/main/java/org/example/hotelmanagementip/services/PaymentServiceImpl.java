package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.PaymentDTO;
import org.example.hotelmanagementip.dto.ReservationDTO;
import org.example.hotelmanagementip.entity.Payment;
import org.example.hotelmanagementip.entity.Reservation;
import org.example.hotelmanagementip.exception.PaymentException;
import org.example.hotelmanagementip.exception.ReservationException;
import org.example.hotelmanagementip.mapper.PaymentMapper;
import org.example.hotelmanagementip.repository.PaymentRepository;
import org.example.hotelmanagementip.repository.ReservationRepository;
import org.example.hotelmanagementip.strategy.CreditCardPaymentStrategy;
import org.example.hotelmanagementip.strategy.PayPalPaymentStrategy;
import org.example.hotelmanagementip.strategy.PaymentContext;
import org.example.hotelmanagementip.strategy.PaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final RoomService roomService;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final PaymentMapper paymentMapper;
    private final PaymentContext paymentContext;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, RoomService roomService,
                              ReservationRepository reservationRepository, @Lazy ReservationService reservationService,
                              PaymentMapper paymentMapper, PaymentContext paymentContext) {
        this.paymentRepository = paymentRepository;
        this.roomService = roomService;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.paymentMapper = paymentMapper;
        this.paymentContext = paymentContext;
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentMapper.mapToDto(paymentRepository.findAll());
    }

    @Override
    public PaymentDTO findById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentException("Payment with ID " + id + " not found."));
        return paymentMapper.mapToDTO(payment);
    }

    @Override
    public List<PaymentDTO> findPaymentsByReservationId(Long reservationId) {
        List<Payment> payments = paymentRepository.findByReservationId(reservationId);
        return paymentMapper.mapToDto(payments);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public PaymentDTO updatePayment(Long reservationId, Long newRoomId, Date newStartDate, Date newEndDate) {
        ReservationDTO reservationDTO = reservationService.findById(reservationId);
        if (reservationDTO == null) {
            throw new ReservationException("Reservation with ID " + reservationId + " not found.");
        }

        Payment payment = paymentRepository.findById(reservationId)
                .orElseThrow(() -> new PaymentException("Payment for Reservation with ID " + reservationId + " not found."));

        double newAmount = roomService.getRoomPrice(newRoomId) * calculateDuration(newStartDate, newEndDate);

        if (!payment.getReservation().getRoom().getId().equals(newRoomId)) {
            newAmount = roomService.getRoomPrice(newRoomId) * calculateDuration(newStartDate, newEndDate);
        }

        payment.setAmount(newAmount);
        paymentRepository.save(payment);

        return paymentMapper.mapToDTO(payment);
    }

    @Override
    public PaymentDTO markPaymentAsPaid(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentException("Payment with ID " + paymentId + " not found."));

        payment.setPaid(true);
        payment.setStatus("Confirmed");
        payment = paymentRepository.save(payment);

        Reservation reservation = payment.getReservation();
        reservation.setStatus("Paid");
        reservationRepository.save(reservation);

        return paymentMapper.mapToDTO(payment);
    }

    @Override
    public PaymentDTO markPaymentAsUnPaid(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentException("Payment with ID " + paymentId + " not found."));

        payment.setPaid(false);
        payment.setStatus("Cancelled");
        payment = paymentRepository.save(payment);

        Reservation reservation = payment.getReservation();
        reservation.setStatus("Cancelled");
        reservationRepository.save(reservation);

        return paymentMapper.mapToDTO(payment);
    }

    @Override
    public int calculateDuration(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        long durationInMillis = 1000 * 60 * 60 * 24; // 1 dzień w milisekundach
        return (int) (diff / durationInMillis);
    }

    @Override
    public PaymentDTO createPayment(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("Reservation with ID " + reservationId + " not found."));

        double amount = roomService.getRoomPrice(reservation.getRoom().getId()) * calculateDuration(reservation.getStartDate(), reservation.getEndDate());

        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setAmount(amount);
        payment.setPaid(false);
        payment.setStatus("Pending");
        payment = paymentRepository.save(payment);

        return paymentMapper.mapToDTO(payment);
    }

    @Override
    public void processPayment(Long paymentId, PaymentStrategy paymentStrategy) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentException("Payment with ID " + paymentId + " not found."));

        if (paymentStrategy instanceof CreditCardPaymentStrategy) {
            ((CreditCardPaymentStrategy) paymentStrategy).setPaymentId(paymentId);
        } else if (paymentStrategy instanceof PayPalPaymentStrategy) {
            ((PayPalPaymentStrategy) paymentStrategy).setPaymentId(paymentId);
        }

        paymentContext.setPaymentStrategy(paymentStrategy);
        paymentContext.executePayment(payment.getAmount());

        payment.setPaid(true);
        payment.setStatus("Confirmed");
        paymentRepository.save(payment);

        Reservation reservation = payment.getReservation();
        if (reservation != null) {
            reservation.setStatus("Paid");
            reservationRepository.save(reservation);
        } else {
            throw new PaymentException("Associated reservation not found for payment ID: " + paymentId);
        }
    }
}
