package org.example.hotelmanagementip.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentDTOTest {

    @Test
    void testNoArgsConstructor() {
        PaymentDTO paymentDTO = new PaymentDTO();
        assertThat(paymentDTO.getId()).isNull();
        assertThat(paymentDTO.getReservationId()).isNull();
        assertThat(paymentDTO.getCustomerId()).isNull();
        assertThat(paymentDTO.getAmount()).isEqualTo(0.0);
        assertThat(paymentDTO.getStatus()).isNull();
        assertThat(paymentDTO.isPaid()).isFalse();
    }

    @Test
    void testAllArgsConstructor() {
        PaymentDTO paymentDTO = new PaymentDTO(1L, 2L, 3L, 100.0, "Pending", false);

        assertThat(paymentDTO.getId()).isEqualTo(1L);
        assertThat(paymentDTO.getReservationId()).isEqualTo(2L);
        assertThat(paymentDTO.getCustomerId()).isEqualTo(3L);
        assertThat(paymentDTO.getAmount()).isEqualTo(100.0);
        assertThat(paymentDTO.getStatus()).isEqualTo("Pending");
        assertThat(paymentDTO.isPaid()).isFalse();
    }

    @Test
    void testSettersAndGetters() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(1L);
        paymentDTO.setReservationId(2L);
        paymentDTO.setCustomerId(3L);
        paymentDTO.setAmount(100.0);
        paymentDTO.setStatus("Pending");
        paymentDTO.setPaid(false);

        assertThat(paymentDTO.getId()).isEqualTo(1L);
        assertThat(paymentDTO.getReservationId()).isEqualTo(2L);
        assertThat(paymentDTO.getCustomerId()).isEqualTo(3L);
        assertThat(paymentDTO.getAmount()).isEqualTo(100.0);
        assertThat(paymentDTO.getStatus()).isEqualTo("Pending");
        assertThat(paymentDTO.isPaid()).isFalse();
    }
}
