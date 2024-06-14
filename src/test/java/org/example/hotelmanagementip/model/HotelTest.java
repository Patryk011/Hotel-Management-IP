package org.example.hotelmanagementip.model;

import org.example.hotelmanagementip.entity.Hotel;
import org.example.hotelmanagementip.entity.Room;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HotelTest {

    @Test
    void testHotelNoArgsConstructor() {
        Hotel hotel = new Hotel();
        assertNotNull(hotel);
    }

    @Test
    void testHotelAllArgsConstructor() {
        List<Room> rooms = new ArrayList<>();
        Hotel hotel = new Hotel(1L, "Hotel Name", "Hotel Address", rooms);

        assertEquals(1L, hotel.getId());
        assertEquals("Hotel Name", hotel.getName());
        assertEquals("Hotel Address", hotel.getAddress());
        assertEquals(rooms, hotel.getRooms());
    }

    @Test
    void testSettersAndGetters() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Hotel Name");
        hotel.setAddress("Hotel Address");

        List<Room> rooms = new ArrayList<>();
        hotel.setRooms(rooms);

        assertEquals(1L, hotel.getId());
        assertEquals("Hotel Name", hotel.getName());
        assertEquals("Hotel Address", hotel.getAddress());
        assertEquals(rooms, hotel.getRooms());
    }

    @Test
    void testAddRoom() {
        Hotel hotel = new Hotel();
        Room room = new Room();
        hotel.getRooms().add(room);

        assertEquals(1, hotel.getRooms().size());
        assertEquals(room, hotel.getRooms().get(0));
    }

    @Test
    void testRemoveRoom() {
        Hotel hotel = new Hotel();
        Room room = new Room();
        hotel.getRooms().add(room);
        hotel.getRooms().remove(room);

        assertEquals(0, hotel.getRooms().size());
    }

    @Test
    void testHotelBuilder() {
        List<Room> rooms = new ArrayList<>();
        Hotel hotel = Hotel.builder()
                .setId(1L)
                .setName("Hotel Name")
                .setAddress("Hotel Address")
                .setRooms(rooms)
                .build();

        assertEquals(1L, hotel.getId());
        assertEquals("Hotel Name", hotel.getName());
        assertEquals("Hotel Address", hotel.getAddress());
        assertEquals(rooms, hotel.getRooms());
    }
}
