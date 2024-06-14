package org.example.hotelmanagementip.dto;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationDTOTest {

    @Test
    void testNoArgsConstructor() {
        ReservationDTO reservationDTO = new ReservationDTO();
        assertThat(reservationDTO.getId()).isNull();
        assertThat(reservationDTO.getCustomerId()).isNull();
        assertThat(reservationDTO.getCustomerEmail()).isNull();
        assertThat(reservationDTO.getRoomId()).isNull();
        assertThat(reservationDTO.getHotelId()).isNull();
        assertThat(reservationDTO.getStartDate()).isNull();
        assertThat(reservationDTO.getEndDate()).isNull();
        assertThat(reservationDTO.getStatus()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        Date startDate = new Date();
        Date endDate = new Date();
        ReservationDTO reservationDTO = new ReservationDTO(1L, 2L, "customer@example.com", 3L, 4L, startDate, endDate, "Confirmed");

        assertThat(reservationDTO.getId()).isEqualTo(1L);
        assertThat(reservationDTO.getCustomerId()).isEqualTo(2L);
        assertThat(reservationDTO.getCustomerEmail()).isEqualTo("customer@example.com");
        assertThat(reservationDTO.getRoomId()).isEqualTo(3L);
        assertThat(reservationDTO.getHotelId()).isEqualTo(4L);
        assertThat(reservationDTO.getStartDate()).isEqualTo(startDate);
        assertThat(reservationDTO.getEndDate()).isEqualTo(endDate);
        assertThat(reservationDTO.getStatus()).isEqualTo("Confirmed");
    }

    @Test
    void testSettersAndGetters() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(1L);
        reservationDTO.setCustomerId(2L);
        reservationDTO.setCustomerEmail("customer@example.com");
        reservationDTO.setRoomId(3L);
        reservationDTO.setHotelId(4L);

        Date startDate = new Date();
        Date endDate = new Date();
        reservationDTO.setStartDate(startDate);
        reservationDTO.setEndDate(endDate);
        reservationDTO.setStatus("Confirmed");

        assertThat(reservationDTO.getId()).isEqualTo(1L);
        assertThat(reservationDTO.getCustomerId()).isEqualTo(2L);
        assertThat(reservationDTO.getCustomerEmail()).isEqualTo("customer@example.com");
        assertThat(reservationDTO.getRoomId()).isEqualTo(3L);
        assertThat(reservationDTO.getHotelId()).isEqualTo(4L);
        assertThat(reservationDTO.getStartDate()).isEqualTo(startDate);
        assertThat(reservationDTO.getEndDate()).isEqualTo(endDate);
        assertThat(reservationDTO.getStatus()).isEqualTo("Confirmed");
    }
}
