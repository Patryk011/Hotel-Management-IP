package org.example.hotelmanagementip.controllers;

import org.example.hotelmanagementip.controller.HotelController;
import org.example.hotelmanagementip.dto.HotelDTO;
import org.example.hotelmanagementip.dto.RoomDTO;
import org.example.hotelmanagementip.services.HotelService;
import org.example.hotelmanagementip.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HotelControllerTest {

    @Mock
    private HotelService hotelService;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private HotelController hotelController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllHotels() {
        List<HotelDTO> hotels = new ArrayList<>();
        hotels.add(new HotelDTO());
        when(hotelService.getAllHotels()).thenReturn(hotels);

        List<HotelDTO> result = hotelController.getAllHotels();

        assertEquals(1, result.size());
        verify(hotelService, times(1)).getAllHotels();
    }

    @Test
    void testGetHotelById() {
        HotelDTO hotel = new HotelDTO();
        when(hotelService.getHotelById(1L)).thenReturn(hotel);

        HotelDTO result = hotelController.getHotelById(1L);

        assertEquals(hotel, result);
        verify(hotelService, times(1)).getHotelById(1L);
    }

    @Test
    void testFindByHotelId() {
        List<RoomDTO> rooms = new ArrayList<>();
        rooms.add(new RoomDTO());
        when(roomService.findByHotelId(1L)).thenReturn(rooms);

        List<RoomDTO> result = hotelController.findByHotelId(1L);

        assertEquals(1, result.size());
        verify(roomService, times(1)).findByHotelId(1L);
    }

    @Test
    void testSaveHotel() {
        HotelDTO hotel = new HotelDTO();
        when(hotelService.saveHotel(hotel)).thenReturn(hotel);

        HotelDTO result = hotelController.saveHotel(hotel);

        assertEquals(hotel, result);
        verify(hotelService, times(1)).saveHotel(hotel);
    }

    @Test
    void testDeleteHotelById() {
        doNothing().when(hotelService).deleteHotelById(1L);

        hotelController.deleteHotelById(1L);

        verify(hotelService, times(1)).deleteHotelById(1L);
    }

    @Test
    void testUpdateHotel() {
        HotelDTO hotel = new HotelDTO();
        when(hotelService.updateHotel(1L, hotel)).thenReturn(hotel);

        HotelDTO result = hotelController.updateHotel(1L, hotel);

        assertEquals(hotel, result);
        verify(hotelService, times(1)).updateHotel(1L, hotel);
    }
}
