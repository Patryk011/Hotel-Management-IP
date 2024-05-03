package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.ReservationDTO;

import java.util.List;

public interface ReservationService {

    List<ReservationDTO> findByCustomerId(Long customerId);

    ReservationDTO findById(Long id);

    ReservationDTO createReservation(ReservationDTO reservationDTO);

    ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO);

    void cancelReservation(Long reservationId);

    List<ReservationDTO> getAllReservations();
}