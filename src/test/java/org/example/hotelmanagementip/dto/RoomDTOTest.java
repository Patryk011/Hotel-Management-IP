package org.example.hotelmanagementip.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoomDTOTest {

    @Test
    void testNoArgsConstructor() {
        RoomDTO roomDTO = new RoomDTO();
        assertThat(roomDTO.getId()).isNull();
        assertThat(roomDTO.getHotelId()).isNull();
        assertThat(roomDTO.getNumber()).isEqualTo(0);
        assertThat(roomDTO.getType()).isNull();
        assertThat(roomDTO.getPrice()).isEqualTo(0.0);
        assertThat(roomDTO.isFree()).isFalse();
        assertThat(roomDTO.isClean()).isFalse();
    }

    @Test
    void testAllArgsConstructor() {
        RoomDTO roomDTO = new RoomDTO(1L, 2L, 101, "Single", 100.0, true, true);

        assertThat(roomDTO.getId()).isEqualTo(1L);
        assertThat(roomDTO.getHotelId()).isEqualTo(2L);
        assertThat(roomDTO.getNumber()).isEqualTo(101);
        assertThat(roomDTO.getType()).isEqualTo("Single");
        assertThat(roomDTO.getPrice()).isEqualTo(100.0);
        assertThat(roomDTO.isFree()).isTrue();
        assertThat(roomDTO.isClean()).isTrue();
    }

    @Test
    void testSettersAndGetters() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(1L);
        roomDTO.setHotelId(2L);
        roomDTO.setNumber(101);
        roomDTO.setType("Single");
        roomDTO.setPrice(100.0);
        roomDTO.setFree(true);
        roomDTO.setClean(true);

        assertThat(roomDTO.getId()).isEqualTo(1L);
        assertThat(roomDTO.getHotelId()).isEqualTo(2L);
        assertThat(roomDTO.getNumber()).isEqualTo(101);
        assertThat(roomDTO.getType()).isEqualTo("Single");
        assertThat(roomDTO.getPrice()).isEqualTo(100.0);
        assertThat(roomDTO.isFree()).isTrue();
        assertThat(roomDTO.isClean()).isTrue();
    }
}
