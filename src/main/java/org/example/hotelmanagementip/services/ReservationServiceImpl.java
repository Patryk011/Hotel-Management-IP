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
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final HotelRepository hotelRepository;

    private final CustomerRepository customerRepository;

    private final PaymentService paymentService;

    private final RoomRepository roomRepository;

    private final ReservationMapper reservationMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, HotelRepository hotelRepository, CustomerRepository customerRepository, PaymentService paymentService, RoomRepository roomRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.customerRepository = customerRepository;
        this.paymentService = paymentService;
        this.roomRepository = roomRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<ReservationDTO> findByCustomerId(Long customerId) {
        List<Reservation> reservations = reservationRepository.findByCustomerId(customerId);
        return reservationMapper.mapToDTO(reservations);
    }

    @Override
    public ReservationDTO findById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ReservationException("Room not found"));
        return reservationMapper.mapToDTO(reservation);
    }

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Long roomId = reservationDTO.getRoomId();
        Date startDate = reservationDTO.getStartDate();
        Date endDate = reservationDTO.getEndDate();


        if (!isRoomAvailable(roomId, startDate, endDate)) {
            throw new ReservationException("The room is already booked in this time.");
        }

        Reservation reservation = reservationMapper.mapFromDTO(reservationDTO);
        reservation.setStatus("Pending");
        reservation = reservationRepository.save(reservation);

        PaymentDTO paymentDTO = paymentService.createPayment(reservation.getId());

        Room room = roomRepository.findById(reservationDTO.getRoomId()).orElseThrow(() -> new RoomException("Room not found"));
        room.setFree(false);
        roomRepository.save(room);

        return reservationMapper.mapToDTO(reservation);
    }

    private boolean isRoomAvailable(Long roomId, Date startDate, Date endDate) {
        List<Reservation> reservations = reservationRepository.findOverlappingReservations(roomId, startDate, endDate);
        return reservations.isEmpty();
    }

    @Override
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationException("Reservation with ID " + id + " not found."));

        Date oldStartDate = existingReservation.getStartDate();
        Date oldEndDate = existingReservation.getEndDate();
        Room oldRoom = existingReservation.getRoom();

        Date newStartDate = reservationDTO.getStartDate();
        Date newEndDate = reservationDTO.getEndDate();
        Long newRoomId = reservationDTO.getRoomId();

        if (!oldStartDate.equals(newStartDate) || !oldEndDate.equals(newEndDate)) {
            PaymentDTO paymentDTO = paymentService.updatePayment(existingReservation.getId(), oldRoom.getId(), newStartDate, newEndDate);


            if (!oldRoom.getId().equals(newRoomId)) {
                if (!isRoomAvailable(newRoomId, newStartDate, newEndDate)) {
                    throw new ReservationException("The room is already booked in this time.");
                }
                paymentDTO = paymentService.updatePayment(existingReservation.getId(), newRoomId, newStartDate, newEndDate);

                oldRoom.setFree(true);
                roomRepository.save(oldRoom);

                Room newRoom = roomRepository.findById(newRoomId)
                        .orElseThrow(() -> new RoomException("New room not found."));
                newRoom.setFree(false);
                roomRepository.save(newRoom);
            }
        }

        existingReservation = reservationMapper.mapFromDTO(existingReservation, reservationDTO);
        reservationRepository.save(existingReservation);
        return reservationMapper.mapToDTO(existingReservation);
    }

    @Override
    public void cancelReservation(Long reservationId) {
        ReservationDTO reservationDTO = findById(reservationId);
        if (reservationDTO != null) {

            List<PaymentDTO> payments = paymentService.findPaymentsByReservationId(reservationId);
            for (PaymentDTO payment : payments) {
                paymentService.deletePayment(payment.getId());
            }
            reservationRepository.deleteById(reservationId);
        } else {
            throw new ReservationException("Reservation with ID " + reservationId + " not found.");
        }
    }


    @Override
    public List<ReservationDTO> getAllReservations() {
        return reservationMapper.mapToDTO(reservationRepository.findAll());
    }
}
