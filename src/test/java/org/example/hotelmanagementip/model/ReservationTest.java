package org.example.hotelmanagementip.model;

import org.example.hotelmanagementip.entity.Customer;
import org.example.hotelmanagementip.entity.Hotel;
import org.example.hotelmanagementip.entity.Reservation;
import org.example.hotelmanagementip.entity.Room;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReservationTest {

    @Test
    void testReservationNoArgsConstructor() {
        Reservation reservation = new Reservation();
        assertNotNull(reservation);
    }

    @Test
    void testReservationAllArgsConstructor() {
        Customer customer = new Customer();
        Room room = new Room();
        Hotel hotel = new Hotel();
        Date startDate = new Date();
        Date endDate = new Date();
        Reservation reservation = new Reservation(1L, customer, room, hotel, startDate, endDate, "Confirmed");

        assertEquals(1L, reservation.getId());
        assertEquals(customer, reservation.getCustomer());
        assertEquals(room, reservation.getRoom());
        assertEquals(hotel, reservation.getHotel());
        assertEquals(startDate, reservation.getStartDate());
        assertEquals(endDate, reservation.getEndDate());
        assertEquals("Confirmed", reservation.getStatus());
    }

    @Test
    void testSettersAndGetters() {
        Reservation reservation = new Reservation();
        Customer customer = new Customer();
        Room room = new Room();
        Hotel hotel = new Hotel();
        Date startDate = new Date();
        Date endDate = new Date();

        reservation.setId(1L);
        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setHotel(hotel);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setStatus("Confirmed");

        assertEquals(1L, reservation.getId());
        assertEquals(customer, reservation.getCustomer());
        assertEquals(room, reservation.getRoom());
        assertEquals(hotel, reservation.getHotel());
        assertEquals(startDate, reservation.getStartDate());
        assertEquals(endDate, reservation.getEndDate());
        assertEquals("Confirmed", reservation.getStatus());
    }

    @Test
    void testSetStatus() {
        Reservation reservation = new Reservation();
        reservation.setStatus("Pending");

        assertEquals("Pending", reservation.getStatus());

        reservation.setStatus("Cancelled");

        assertEquals("Cancelled", reservation.getStatus());
    }
}
