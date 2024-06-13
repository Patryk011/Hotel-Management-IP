package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.PaymentDTO;
import org.example.hotelmanagementip.dto.ReservationDTO;
import org.example.hotelmanagementip.entity.Payment;
import org.example.hotelmanagementip.entity.Reservation;
import org.example.hotelmanagementip.entity.Room;
import org.example.hotelmanagementip.exception.PaymentException;
import org.example.hotelmanagementip.exception.ReservationException;
import org.example.hotelmanagementip.mapper.PaymentMapper;
import org.example.hotelmanagementip.repository.PaymentRepository;
import org.example.hotelmanagementip.repository.ReservationRepository;
import org.example.hotelmanagementip.strategy.CreditCardPaymentStrategy;
import org.example.hotelmanagementip.strategy.PaymentContext;
import org.example.hotelmanagementip.strategy.PaymentStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RoomService roomService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationService reservationService;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private PaymentContext paymentContext;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = Arrays.asList(new Payment(), new Payment());
        List<PaymentDTO> paymentDTOs = Arrays.asList(new PaymentDTO(), new PaymentDTO());

        when(paymentRepository.findAll()).thenReturn(payments);
        when(paymentMapper.mapToDto(payments)).thenReturn(paymentDTOs);

        List<PaymentDTO> result = paymentService.getAllPayments();

        assertEquals(paymentDTOs, result);
        verify(paymentRepository, times(1)).findAll();
        verify(paymentMapper, times(1)).mapToDto(payments);
    }

    @Test
    void testFindById_Success() {
        Long paymentId = 1L;
        Payment payment = new Payment();
        PaymentDTO paymentDTO = new PaymentDTO();

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentMapper.mapToDTO(payment)).thenReturn(paymentDTO);

        PaymentDTO result = paymentService.findById(paymentId);

        assertEquals(paymentDTO, result);
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentMapper, times(1)).mapToDTO(payment);
    }

    @Test
    void testFindById_NotFound() {
        Long paymentId = 1L;

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(PaymentException.class, () -> paymentService.findById(paymentId));
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentMapper, never()).mapToDTO(any());
    }

    @Test
    void testFindPaymentsByReservationId() {
        Long reservationId = 1L;
        List<Payment> payments = Arrays.asList(new Payment(), new Payment());
        List<PaymentDTO> paymentDTOs = Arrays.asList(new PaymentDTO(), new PaymentDTO());

        when(paymentRepository.findByReservationId(reservationId)).thenReturn(payments);
        when(paymentMapper.mapToDto(payments)).thenReturn(paymentDTOs);

        List<PaymentDTO> result = paymentService.findPaymentsByReservationId(reservationId);

        assertEquals(paymentDTOs, result);
        verify(paymentRepository, times(1)).findByReservationId(reservationId);
        verify(paymentMapper, times(1)).mapToDto(payments);
    }

    @Test
    void testDeletePayment() {
        Long paymentId = 1L;

        doNothing().when(paymentRepository).deleteById(paymentId);

        paymentService.deletePayment(paymentId);

        verify(paymentRepository, times(1)).deleteById(paymentId);
    }

    @Test
    void testUpdatePayment_Success() {
        Long reservationId = 1L;
        Long newRoomId = 2L;
        Date newStartDate = new Date();
        Date newEndDate = new Date(newStartDate.getTime() + 86400000L); // 1 day later
        ReservationDTO reservationDTO = new ReservationDTO();
        Payment payment = new Payment();

        Reservation reservation = new Reservation();
        Room room = new Room();
        room.setId(newRoomId);
        reservation.setRoom(room);
        payment.setReservation(reservation);

        when(reservationService.findById(reservationId)).thenReturn(reservationDTO);
        when(roomService.getRoomPrice(newRoomId)).thenReturn(100.0);
        when(paymentRepository.findById(reservationId)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(paymentMapper.mapToDTO(payment)).thenReturn(new PaymentDTO());

        PaymentDTO result = paymentService.updatePayment(reservationId, newRoomId, newStartDate, newEndDate);

        assertNotNull(result);
        verify(reservationService, times(1)).findById(reservationId);
        verify(roomService, times(1)).getRoomPrice(newRoomId);
        verify(paymentRepository, times(1)).findById(reservationId);
        verify(paymentRepository, times(1)).save(payment);
        verify(paymentMapper, times(1)).mapToDTO(payment);
    }

    @Test
    void testUpdatePayment_ReservationNotFound() {
        Long reservationId = 1L;
        Long newRoomId = 2L;
        Date newStartDate = new Date();
        Date newEndDate = new Date(newStartDate.getTime() + 86400000L); // 1 day later

        when(reservationService.findById(reservationId)).thenReturn(null);

        assertThrows(ReservationException.class, () -> paymentService.updatePayment(reservationId, newRoomId, newStartDate, newEndDate));
        verify(reservationService, times(1)).findById(reservationId);
        verify(roomService, never()).getRoomPrice(any());
        verify(paymentRepository, never()).findById(any());
        verify(paymentRepository, never()).save(any());
        verify(paymentMapper, never()).mapToDTO(any());
    }

    @Test
    void testUpdatePayment_PaymentNotFound() {
        Long reservationId = 1L;
        Long newRoomId = 2L;
        Date newStartDate = new Date();
        Date newEndDate = new Date(newStartDate.getTime() + 86400000L); // 1 day later
        ReservationDTO reservationDTO = new ReservationDTO();

        when(reservationService.findById(reservationId)).thenReturn(reservationDTO);
        when(paymentRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(PaymentException.class, () -> paymentService.updatePayment(reservationId, newRoomId, newStartDate, newEndDate));
        verify(reservationService, times(1)).findById(reservationId);
        verify(paymentRepository, times(1)).findById(reservationId);
        verify(roomService, never()).getRoomPrice(any());
        verify(paymentRepository, never()).save(any());
        verify(paymentMapper, never()).mapToDTO(any());
    }

    @Test
    void testMarkPaymentAsPaid_Success() {
        Long paymentId = 1L;
        Payment payment = new Payment();
        Reservation reservation = new Reservation();

        payment.setReservation(reservation);

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(paymentMapper.mapToDTO(payment)).thenReturn(new PaymentDTO());

        PaymentDTO result = paymentService.markPaymentAsPaid(paymentId);

        assertNotNull(result);
        assertTrue(payment.isPaid());
        assertEquals("Confirmed", payment.getStatus());
        assertEquals("Paid", reservation.getStatus());
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentRepository, times(1)).save(payment);
        verify(reservationRepository, times(1)).save(reservation);
        verify(paymentMapper, times(1)).mapToDTO(payment);
    }

    @Test
    void testMarkPaymentAsPaid_NotFound() {
        Long paymentId = 1L;

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(PaymentException.class, () -> paymentService.markPaymentAsPaid(paymentId));
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentRepository, never()).save(any());
        verify(reservationRepository, never()).save(any());
        verify(paymentMapper, never()).mapToDTO(any());
    }

    @Test
    void testMarkPaymentAsUnPaid_Success() {
        Long paymentId = 1L;
        Payment payment = new Payment();
        Reservation reservation = new Reservation();

        payment.setReservation(reservation);

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(paymentMapper.mapToDTO(payment)).thenReturn(new PaymentDTO());

        PaymentDTO result = paymentService.markPaymentAsUnPaid(paymentId);

        assertNotNull(result);
        assertFalse(payment.isPaid());
        assertEquals("Cancelled", payment.getStatus());
        assertEquals("Cancelled", reservation.getStatus());
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentRepository, times(1)).save(payment);
        verify(reservationRepository, times(1)).save(reservation);
        verify(paymentMapper, times(1)).mapToDTO(payment);
    }

    @Test
    void testMarkPaymentAsUnPaid_NotFound() {
        Long paymentId = 1L;

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(PaymentException.class, () -> paymentService.markPaymentAsUnPaid(paymentId));
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentRepository, never()).save(any());
        verify(reservationRepository, never()).save(any());
        verify(paymentMapper, never()).mapToDTO(any());
    }

    @Test
    void testCalculateDuration() {
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 86400000L); // 1 day later

        int duration = paymentService.calculateDuration(startDate, endDate);

        assertEquals(1, duration);
    }

    @Test
    void testCreatePayment_Success() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        Room room = new Room();
        reservation.setRoom(room);
        reservation.setStartDate(new Date());
        reservation.setEndDate(new Date(new Date().getTime() + 86400000L)); // 1 day later
        Payment payment = new Payment();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(roomService.getRoomPrice(room.getId())).thenReturn(100.0);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(paymentMapper.mapToDTO(payment)).thenReturn(new PaymentDTO());

        PaymentDTO result = paymentService.createPayment(reservationId);

        assertNotNull(result);
        verify(reservationRepository, times(1)).findById(reservationId);
        verify(roomService, times(1)).getRoomPrice(room.getId());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(paymentMapper, times(1)).mapToDTO(payment);
    }

    @Test
    void testCreatePayment_ReservationNotFound() {
        Long reservationId = 1L;

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> paymentService.createPayment(reservationId));
        verify(reservationRepository, times(1)).findById(reservationId);
        verify(roomService, never()).getRoomPrice(any());
        verify(paymentRepository, never()).save(any());
        verify(paymentMapper, never()).mapToDTO(any());
    }

    @Test
    void testProcessPayment_Success() {
        Long paymentId = 1L;
        Payment payment = new Payment();
        Reservation reservation = new Reservation();
        payment.setReservation(reservation);

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        doNothing().when(paymentContext).executePayment(anyDouble());
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        CreditCardPaymentStrategy paymentStrategy = mock(CreditCardPaymentStrategy.class);
        doNothing().when(paymentStrategy).setPaymentId(paymentId);
        doNothing().when(paymentStrategy).pay(payment.getAmount());

        paymentService.processPayment(paymentId, paymentStrategy);

        assertTrue(payment.isPaid());
        assertEquals("Confirmed", payment.getStatus());
        assertEquals("Paid", reservation.getStatus());
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentStrategy, times(1)).setPaymentId(paymentId);
        verify(paymentContext, times(1)).setPaymentStrategy(paymentStrategy);
        verify(paymentContext, times(1)).executePayment(payment.getAmount());
        verify(paymentRepository, times(1)).save(payment);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testProcessPayment_PaymentNotFound() {
        Long paymentId = 1L;
        CreditCardPaymentStrategy paymentStrategy = mock(CreditCardPaymentStrategy.class);

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(PaymentException.class, () -> paymentService.processPayment(paymentId, paymentStrategy));
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentContext, never()).setPaymentStrategy(any());
        verify(paymentContext, never()).executePayment(anyDouble());
        verify(paymentRepository, never()).save(any());
        verify(reservationRepository, never()).save(any());
    }
}
