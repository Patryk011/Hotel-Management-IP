package org.example.hotelmanagementip.factory;

import org.example.hotelmanagementip.entity.Hotel;
import org.example.hotelmanagementip.entity.Room;
import org.example.hotelmanagementip.exception.ReservationException;
import org.example.hotelmanagementip.repository.HotelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class FactoryTest {

    private RoomFactory roomFactory;

    @Mock
    private HotelRepository hotelRepository;

    @Test
    public void testCreateRoom(){
        String type = "double room";
        Long hotelId = 1L;
        int number = 1;
        double price = 202.2;
        Room room = roomFactory.createRoom(type, hotelId, number, price);
        Assertions.assertNotNull(room);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRoomNegative(){
        String type = "double room";
        Long hotelId = 2L;
        int number = 1;
        double price = 202.2;
        Room room = roomFactory.createRoom(type, hotelId, number, price);
        Assertions.assertNotNull(room);
    }

    @Before
    public void init() {
        this.roomFactory = new RoomFactoryImpl(hotelRepository);

        Mockito.when(hotelRepository.findById(1L)).thenReturn(Optional.of(new Hotel()));
    }


}
