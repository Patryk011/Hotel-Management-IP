package org.example.hotelmanagementip.controllers;

import org.example.hotelmanagementip.controller.ReservationController;
import org.example.hotelmanagementip.dto.ReservationDTO;
import org.example.hotelmanagementip.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReservationById() {
        ReservationDTO reservation = new ReservationDTO();
        when(reservationService.findById(1L)).thenReturn(reservation);

        ReservationDTO result = reservationController.getReservationById(1L);

        assertEquals(reservation, result);
        verify(reservationService, times(1)).findById(1L);
    }

    @Test
    void testCreateReservation() {
        ReservationDTO reservation = new ReservationDTO();
        when(reservationService.createReservation(reservation)).thenReturn(reservation);

        ReservationDTO result = reservationController.createReservation(reservation);

        assertEquals(reservation, result);
        verify(reservationService, times(1)).createReservation(reservation);
    }

    @Test
    void testUpdateReservation() {
        ReservationDTO reservation = new ReservationDTO();
        when(reservationService.updateReservation(1L, reservation)).thenReturn(reservation);

        ReservationDTO result = reservationController.updateReservation(1L, reservation);

        assertEquals(reservation, result);
        verify(reservationService, times(1)).updateReservation(eq(1L), eq(reservation));
    }

    @Test
    void testCancelReservation() {
        doNothing().when(reservationService).cancelReservation(1L);

        reservationController.cancelReservation(1L);

        verify(reservationService, times(1)).cancelReservation(1L);
    }

    @Test
    void testGetAllReservations() {
        List<ReservationDTO> reservations = new ArrayList<>();
        reservations.add(new ReservationDTO());
        when(reservationService.getAllReservations()).thenReturn(reservations);

        List<ReservationDTO> result = reservationController.getAllReservations();

        assertEquals(1, result.size());
        verify(reservationService, times(1)).getAllReservations();
    }
}
