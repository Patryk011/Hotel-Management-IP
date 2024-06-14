package org.example.hotelmanagementip.model;

import org.example.hotelmanagementip.entity.Hotel;
import org.example.hotelmanagementip.entity.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    @Test
    void testRoomNoArgsConstructor() {
        Room room = new Room();
        assertNotNull(room);
    }

    @Test
    void testRoomAllArgsConstructor() {
        Hotel hotel = new Hotel();
        Room room = new Room(1L, hotel, 101, "Deluxe", 200.0, true, false);

        assertEquals(1L, room.getId());
        assertEquals(hotel, room.getHotel());
        assertEquals(101, room.getNumber());
        assertEquals("Deluxe", room.getType());
        assertEquals(200.0, room.getPrice());
        assertTrue(room.isFree());
        assertFalse(room.isClean());
    }

    @Test
    void testSettersAndGetters() {
        Room room = new Room();
        Hotel hotel = new Hotel();

        room.setId(1L);
        room.setHotel(hotel);
        room.setNumber(101);
        room.setType("Deluxe");
        room.setPrice(200.0);
        room.setFree(true);
        room.setClean(false);

        assertEquals(1L, room.getId());
        assertEquals(hotel, room.getHotel());
        assertEquals(101, room.getNumber());
        assertEquals("Deluxe", room.getType());
        assertEquals(200.0, room.getPrice());
        assertTrue(room.isFree());
        assertFalse(room.isClean());
    }

    @Test
    void testSetIsFree() {
        Room room = new Room();
        room.setFree(true);

        assertTrue(room.isFree());

        room.setFree(false);

        assertFalse(room.isFree());
    }

    @Test
    void testSetIsClean() {
        Room room = new Room();
        room.setClean(true);

        assertTrue(room.isClean());

        room.setClean(false);

        assertFalse(room.isClean());
    }
}

