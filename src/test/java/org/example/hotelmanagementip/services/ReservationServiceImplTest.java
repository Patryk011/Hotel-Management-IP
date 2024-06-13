package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.PaymentDTO;
import org.example.hotelmanagementip.dto.ReservationDTO;
import org.example.hotelmanagementip.entity.Reservation;
import org.example.hotelmanagementip.entity.Room;
import org.example.hotelmanagementip.exception.ReservationException;
import org.example.hotelmanagementip.exception.RoomException;
import org.example.hotelmanagementip.mapper.ReservationMapper;
import org.example.hotelmanagementip.repository.CustomerRepository;
import org.example.hotelmanagementip.repository.HotelRepository;
import org.example.hotelmanagementip.repository.ReservationRepository;
import org.example.hotelmanagementip.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByCustomerId() {
        Long customerId = 1L;
        List<Reservation> reservations = Arrays.asList(new Reservation(), new Reservation());
        List<ReservationDTO> reservationDTOs = Arrays.asList(new ReservationDTO(), new ReservationDTO());

        when(reservationRepository.findByCustomerId(customerId)).thenReturn(reservations);
        when(reservationMapper.mapToDTO(reservations)).thenReturn(reservationDTOs);

        List<ReservationDTO> result = reservationService.findByCustomerId(customerId);

        assertEquals(reservationDTOs, result);
        verify(reservationRepository, times(1)).findByCustomerId(customerId);
        verify(reservationMapper, times(1)).mapToDTO(reservations);
    }

    @Test
    void testFindById_Success() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        ReservationDTO reservationDTO = new ReservationDTO();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationMapper.mapToDTO(reservation)).thenReturn(reservationDTO);

        ReservationDTO result = reservationService.findById(reservationId);

        assertEquals(reservationDTO, result);
        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationMapper, times(1)).mapToDTO(reservation);
    }

    @Test
    void testFindById_NotFound() {
        Long reservationId = 1L;

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(ReservationException.class, () -> reservationService.findById(reservationId));
        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationMapper, never()).mapToDTO((Reservation) any());
    }

    @Test
    void testCreateReservation_Success() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setRoomId(1L);
        reservationDTO.setStartDate(new Date());
        reservationDTO.setEndDate(new Date(new Date().getTime() + 86400000L)); // 1 day later
        Reservation reservation = new Reservation();
        Room room = new Room();
        room.setId(1L);

        when(roomRepository.findById(reservationDTO.getRoomId())).thenReturn(Optional.of(room));
        when(reservationMapper.mapFromDTO(reservationDTO)).thenReturn(reservation);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(paymentService.createPayment(reservation.getId())).thenReturn(new PaymentDTO());
        when(reservationMapper.mapToDTO(reservation)).thenReturn(reservationDTO);

        ReservationDTO result = reservationService.createReservation(reservationDTO);

        assertNotNull(result);
        verify(roomRepository, times(1)).findById(reservationDTO.getRoomId());
        verify(reservationMapper, times(1)).mapFromDTO(reservationDTO);
        verify(reservationRepository, times(1)).save(reservation);
        verify(paymentService, times(1)).createPayment(reservation.getId());
        verify(roomRepository, times(1)).save(room);
        verify(reservationMapper, times(1)).mapToDTO(reservation);
    }

    @Test
    void testUpdateReservation_Success() {
        Long reservationId = 1L;
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setRoomId(2L);
        reservationDTO.setStartDate(new Date());
        reservationDTO.setEndDate(new Date(new Date().getTime() + 86400000L)); // 1 day later
        Reservation existingReservation = new Reservation();
        Room oldRoom = new Room();
        oldRoom.setId(1L);
        existingReservation.setRoom(oldRoom);
        existingReservation.setStartDate(new Date());
        existingReservation.setEndDate(new Date(new Date().getTime() + 86400000L)); // 1 day later

        Room newRoom = new Room();
        newRoom.setId(2L);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(existingReservation));
        when(roomRepository.findById(reservationDTO.getRoomId())).thenReturn(Optional.of(newRoom));
        when(reservationMapper.mapFromDTO(existingReservation, reservationDTO)).thenReturn(existingReservation);
        when(reservationRepository.save(existingReservation)).thenReturn(existingReservation);
        when(reservationMapper.mapToDTO(existingReservation)).thenReturn(reservationDTO);

        ReservationDTO result = reservationService.updateReservation(reservationId, reservationDTO);

        assertNotNull(result);
        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationMapper, times(1)).mapFromDTO(existingReservation, reservationDTO);
        verify(reservationRepository, times(1)).save(existingReservation);
        verify(reservationMapper, times(1)).mapToDTO(existingReservation);
    }

    @Test
    void testCancelReservation_Success() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservationId);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationMapper.mapToDTO(reservation)).thenReturn(reservationDTO);

        PaymentDTO payment1 = new PaymentDTO();
        payment1.setId(1L);
        PaymentDTO payment2 = new PaymentDTO();
        payment2.setId(2L);
        List<PaymentDTO> payments = Arrays.asList(payment1, payment2);
        when(paymentService.findPaymentsByReservationId(reservationId)).thenReturn(payments);

        reservationService.cancelReservation(reservationId);

        verify(paymentService, times(1)).deletePayment(1L);
        verify(paymentService, times(1)).deletePayment(2L);
        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

    @Test
    void testCancelReservation_NotFound() {
        Long reservationId = 1L;

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(ReservationException.class, () -> reservationService.cancelReservation(reservationId));
        verify(reservationRepository, times(1)).findById(reservationId);
        verify(paymentService, never()).deletePayment(anyLong());
        verify(reservationRepository, never()).deleteById(reservationId);
    }

    @Test
    void testGetAllReservations() {
        List<Reservation> reservations = Arrays.asList(new Reservation(), new Reservation());
        List<ReservationDTO> reservationDTOs = Arrays.asList(new ReservationDTO(), new ReservationDTO());

        when(reservationRepository.findAll()).thenReturn(reservations);
        when(reservationMapper.mapToDTO(reservations)).thenReturn(reservationDTOs);

        List<ReservationDTO> result = reservationService.getAllReservations();

        assertEquals(reservationDTOs, result);
        verify(reservationRepository, times(1)).findAll();
        verify(reservationMapper, times(1)).mapToDTO(reservations);
    }
}
