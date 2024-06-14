package org.example.hotelmanagementip.controllers;

import org.example.hotelmanagementip.controller.RoomController;
import org.example.hotelmanagementip.dto.RoomDTO;
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

public class RoomControllerTest {

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRooms() {
        List<RoomDTO> rooms = new ArrayList<>();
        rooms.add(new RoomDTO());
        when(roomService.getAllRooms()).thenReturn(rooms);

        List<RoomDTO> result = roomController.getAllRooms();

        assertEquals(1, result.size());
        verify(roomService, times(1)).getAllRooms();
    }

    @Test
    void testGetAvailableRooms() {
        List<RoomDTO> rooms = new ArrayList<>();
        rooms.add(new RoomDTO());
        when(roomService.getAvailableRooms()).thenReturn(rooms);

        List<RoomDTO> result = roomController.getAvailableRooms();

        assertEquals(1, result.size());
        verify(roomService, times(1)).getAvailableRooms();
    }

    @Test
    void testGetRoomById() {
        RoomDTO room = new RoomDTO();
        when(roomService.getById(1L)).thenReturn(room);

        RoomDTO result = roomController.getRoomById(1L);

        assertEquals(room, result);
        verify(roomService, times(1)).getById(1L);
    }

    @Test
    void testToggleCleanStatus() {
        RoomDTO room = new RoomDTO();
        when(roomService.toggleCleanStatus(1L)).thenReturn(room);

        RoomDTO result = roomController.toggleCleanStatus(1L);

        assertEquals(room, result);
        verify(roomService, times(1)).toggleCleanStatus(1L);
    }

    @Test
    void testUpdateRoom() {
        RoomDTO room = new RoomDTO();
        when(roomService.updateRoom(1L, room)).thenReturn(room);

        RoomDTO result = roomController.updateRoom(1L, room);

        assertEquals(room, result);
        verify(roomService, times(1)).updateRoom(1L, room);
    }

    @Test
    void testAddRoom() {
        RoomDTO room = new RoomDTO();
        when(roomService.addRoom(room)).thenReturn(room);

        RoomDTO result = roomController.addRoom(room);

        assertEquals(room, result);
        verify(roomService, times(1)).addRoom(room);
    }

    @Test
    void testDeleteRoom() {
        doNothing().when(roomService).deleteRoom(1L);

        roomController.deleteRoom(1L);

        verify(roomService, times(1)).deleteRoom(1L);
    }
}
