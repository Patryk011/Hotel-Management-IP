package org.example.hotelmanagementip.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.example.hotelmanagementip.dto.ReservationDTO;
import org.example.hotelmanagementip.entity.Customer;
import org.example.hotelmanagementip.entity.Hotel;
import org.example.hotelmanagementip.entity.Reservation;
import org.example.hotelmanagementip.entity.Room;
import org.example.hotelmanagementip.exception.CustomerException;
import org.example.hotelmanagementip.exception.HotelException;
import org.example.hotelmanagementip.exception.RoomException;
import org.example.hotelmanagementip.repository.CustomerRepository;
import org.example.hotelmanagementip.repository.HotelRepository;
import org.example.hotelmanagementip.repository.RoomRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationMapper {

    private final HotelRepository hotelRepository;

    private final RoomRepository roomRepository;

    private final CustomerRepository customerRepository;


    public ReservationMapper(HotelRepository hotelRepository, RoomRepository roomRepository, CustomerRepository customerRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.customerRepository = customerRepository;
    }

    public ReservationDTO mapToDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setCustomerEmail(reservation.getCustomer().getEmail());
        reservationDTO.setRoomId(reservation.getRoom().getId());
        reservationDTO.setHotelId(reservation.getHotel().getId());
        reservationDTO.setStartDate(reservation.getStartDate());
        reservationDTO.setEndDate(reservation.getEndDate());
        reservationDTO.setStatus(reservation.getStatus());
        return reservationDTO;
    }

    public List<ReservationDTO> mapToDTO(Collection<Reservation> reservations) {
        return reservations.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Reservation mapFromDTO(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();



        Hotel hotel = hotelRepository.findById(reservationDTO.getHotelId()).orElseThrow(() -> new HotelException("Hotel not found"));

        Room room = roomRepository.findById(reservationDTO.getRoomId()).orElseThrow(() -> new RoomException("Room not found"));

        Customer customer = customerRepository.findByEmail(reservationDTO.getCustomerEmail());

        if (customer == null) {
            throw new CustomerException("Customer not found");
        }


        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setHotel(hotel);
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStatus(reservationDTO.getStatus());
        return reservation;
    }

    public Reservation mapFromDTO(Reservation reservation, ReservationDTO reservationDTO) {

        Hotel hotel = hotelRepository.findById(reservationDTO.getHotelId()).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        Room room = roomRepository.findById(reservationDTO.getRoomId()).orElseThrow(() -> new EntityNotFoundException("Room not found"));

        Customer customer = customerRepository.findByEmail(reservationDTO.getCustomerEmail());


        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setHotel(hotel);
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStatus(reservationDTO.getStatus());
        return reservation;
    }
}

