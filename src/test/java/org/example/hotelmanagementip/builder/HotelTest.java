package org.example.hotelmanagementip.builder;

import org.example.hotelmanagementip.entity.Hotel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HotelTest {

    @Test
    public void testHotelBuilder() {
        Hotel hotel = Hotel.builder()
                .setId(1L)
                .setName("Test Hotel")
                .setAddress("Test Address")
                .build();

        assertNotNull(hotel);
        assertEquals(1L, hotel.getId());
        assertEquals("Test Hotel", hotel.getName());
        assertEquals("Test Address", hotel.getAddress());
    }
}