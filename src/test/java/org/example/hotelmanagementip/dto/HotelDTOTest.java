package org.example.hotelmanagementip.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HotelDTOTest {

    @Test
    void testNoArgsConstructor() {
        HotelDTO hotelDTO = new HotelDTO();
        assertThat(hotelDTO.getId()).isNull();
        assertThat(hotelDTO.getName()).isNull();
        assertThat(hotelDTO.getAddress()).isNull();
        assertThat(hotelDTO.getRooms()).isEmpty();
    }

    @Test
    void testAllArgsConstructor() {
        List<RoomDTO> rooms = new ArrayList<>();
        HotelDTO hotelDTO = new HotelDTO(1L, "Hotel Name", "Hotel Address", rooms);

        assertThat(hotelDTO.getId()).isEqualTo(1L);
        assertThat(hotelDTO.getName()).isEqualTo("Hotel Name");
        assertThat(hotelDTO.getAddress()).isEqualTo("Hotel Address");
        assertThat(hotelDTO.getRooms()).isEqualTo(rooms);
    }

    @Test
    void testSettersAndGetters() {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setId(1L);
        hotelDTO.setName("Hotel Name");
        hotelDTO.setAddress("Hotel Address");

        List<RoomDTO> rooms = new ArrayList<>();
        hotelDTO.setRooms(rooms);

        assertThat(hotelDTO.getId()).isEqualTo(1L);
        assertThat(hotelDTO.getName()).isEqualTo("Hotel Name");
        assertThat(hotelDTO.getAddress()).isEqualTo("Hotel Address");
        assertThat(hotelDTO.getRooms()).isEqualTo(rooms);
    }
}
