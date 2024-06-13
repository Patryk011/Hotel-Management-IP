package org.example.hotelmanagementip.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerDTOTest {

    @Test
    void testNoArgsConstructor() {
        CustomerDTO customerDTO = new CustomerDTO();
        assertThat(customerDTO.getId()).isNull();
        assertThat(customerDTO.getFirstName()).isNull();
        assertThat(customerDTO.getLastName()).isNull();
        assertThat(customerDTO.getEmail()).isNull();
        assertThat(customerDTO.getReservations()).isEmpty();
    }

    @Test
    void testAllArgsConstructor() {
        List<ReservationDTO> reservations = new ArrayList<>();
        CustomerDTO customerDTO = new CustomerDTO(1L, "John", "Doe", "john.doe@example.com", reservations);

        assertThat(customerDTO.getId()).isEqualTo(1L);
        assertThat(customerDTO.getFirstName()).isEqualTo("John");
        assertThat(customerDTO.getLastName()).isEqualTo("Doe");
        assertThat(customerDTO.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(customerDTO.getReservations()).isEqualTo(reservations);
    }

    @Test
    void testSettersAndGetters() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        customerDTO.setEmail("john.doe@example.com");

        List<ReservationDTO> reservations = new ArrayList<>();
        customerDTO.setReservations(reservations);

        assertThat(customerDTO.getId()).isEqualTo(1L);
        assertThat(customerDTO.getFirstName()).isEqualTo("John");
        assertThat(customerDTO.getLastName()).isEqualTo("Doe");
        assertThat(customerDTO.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(customerDTO.getReservations()).isEqualTo(reservations);
    }
}
