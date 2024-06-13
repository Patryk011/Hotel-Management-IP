package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.PaymentDTO;
import org.example.hotelmanagementip.dto.ReservationDTO;
import org.example.hotelmanagementip.entity.Reservation;
import org.example.hotelmanagementip.entity.Room;
import org.example.hotelmanagementip.exception.ReservationException;
import org.example.hotelmanagementip.mapper.ReservationMapper;
import org.example.hotelmanagementip.repository.CustomerRepository;
import org.example.hotelmanagementip.repository.HotelRepository;
import org.example.hotelmanagementip.repository.ReservationRepository;
import org.example.hotelmanagementip.repository.RoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceCreateReservationTest {

    private ReservationService reservationService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @Test
    public void testCreateReservation() {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(1L);
        dto.setRoomId(1L);
        dto.setStartDate(new Date(1L));
        dto.setEndDate(new Date(3L));
        ReservationDTO created = reservationService.createReservation(dto);
        Assertions.assertNotNull(created);
    }

    @Test(expected = ReservationException.class)
    public void testCreateReservationNegative() {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(1L);
        dto.setRoomId(1L);
        dto.setStartDate(new Date(5L));
        dto.setEndDate(new Date(6L));
        ReservationDTO created = reservationService.createReservation(dto);
    }

    @Before
    public void init() {
        this.reservationService = new ReservationServiceImpl(reservationRepository,
                hotelRepository,
                customerRepository,
                paymentService,
                roomRepository,
                reservationMapper);
        Mockito.when(paymentService.createPayment(1L)).thenReturn(new PaymentDTO());
        Mockito.when(reservationRepository.findOverlappingReservations(1L, new Date(1L), new Date(3L))).thenReturn(new ArrayList<>());
        Mockito.when(reservationRepository.findOverlappingReservations(1L, new Date(5L), new Date(6L))).thenReturn(createNotEmptyListReservationList());
        Mockito.when(reservationMapper.mapFromDTO(Mockito.any())).thenReturn(new Reservation());
        Mockito.when(reservationMapper.mapToDTO(Mockito.any(Reservation.class))).thenReturn(new ReservationDTO());
        Mockito.when(reservationRepository.save(Mockito.any())).thenReturn(createReservation());
        Mockito.when(roomRepository.findById(1L)).thenReturn(Optional.of(new Room()));
        Mockito.when(roomRepository.save(Mockito.any())).thenReturn(new Room());
    }

    private Reservation createReservation() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        return reservation;
    }

    private List<Reservation> createNotEmptyListReservationList() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        return reservations;
    }

}
