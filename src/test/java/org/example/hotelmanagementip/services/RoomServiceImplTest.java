package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.RoomDTO;
import org.example.hotelmanagementip.entity.Room;
import org.example.hotelmanagementip.exception.RoomException;
import org.example.hotelmanagementip.factory.RoomFactory;
import org.example.hotelmanagementip.mapper.RoomMapper;
import org.example.hotelmanagementip.repository.HotelRepository;
import org.example.hotelmanagementip.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomMapper roomMapper;

    @Mock
    private RoomFactory roomFactory;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRooms() {
        List<Room> rooms = Arrays.asList(new Room(), new Room());
        List<RoomDTO> roomDTOs = Arrays.asList(new RoomDTO(), new RoomDTO());

        when(roomRepository.findAll()).thenReturn(rooms);
        when(roomMapper.mapToDto(rooms)).thenReturn(roomDTOs);

        List<RoomDTO> result = roomService.getAllRooms();

        assertEquals(roomDTOs, result);
        verify(roomRepository, times(1)).findAll();
        verify(roomMapper, times(1)).mapToDto(rooms);
    }

    @Test
    void testGetAvailableRooms() {
        List<Room> rooms = Arrays.asList(new Room(), new Room());
        List<RoomDTO> roomDTOs = Arrays.asList(new RoomDTO(), new RoomDTO());

        when(roomRepository.findByIsFree(true)).thenReturn(rooms);
        when(roomMapper.mapToDto(rooms)).thenReturn(roomDTOs);

        List<RoomDTO> result = roomService.getAvailableRooms();

        assertEquals(roomDTOs, result);
        verify(roomRepository, times(1)).findByIsFree(true);
        verify(roomMapper, times(1)).mapToDto(rooms);
    }

    @Test
    void testFindByHotelId() {
        Long hotelId = 1L;
        List<Room> rooms = Arrays.asList(new Room(), new Room());
        List<RoomDTO> roomDTOs = Arrays.asList(new RoomDTO(), new RoomDTO());

        when(roomRepository.findByHotelId(hotelId)).thenReturn(rooms);
        when(roomMapper.mapToDto(rooms)).thenReturn(roomDTOs);

        List<RoomDTO> result = roomService.findByHotelId(hotelId);

        assertEquals(roomDTOs, result);
        verify(roomRepository, times(1)).findByHotelId(hotelId);
        verify(roomMapper, times(1)).mapToDto(rooms);
    }

    @Test
    void testGetById_Success() {
        Long roomId = 1L;
        Room room = new Room();
        RoomDTO roomDTO = new RoomDTO();

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(roomMapper.mapToDto(room)).thenReturn(roomDTO);

        RoomDTO result = roomService.getById(roomId);

        assertEquals(roomDTO, result);
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomMapper, times(1)).mapToDto(room);
    }

    @Test
    void testGetById_NotFound() {
        Long roomId = 1L;

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> roomService.getById(roomId));
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomMapper, never()).mapToDto((Room) any());
    }

    @Test
    void testAddRoom_Success() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setHotelId(1L);
        roomDTO.setNumber(101);
        Room room = new Room();

        when(roomRepository.findByHotelIdAndNumber(roomDTO.getHotelId(), roomDTO.getNumber())).thenReturn(null);
        when(roomFactory.createRoom(roomDTO.getType(), roomDTO.getHotelId(), roomDTO.getNumber(), roomDTO.getPrice())).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(room);
        when(roomMapper.mapToDto(room)).thenReturn(roomDTO);

        RoomDTO result = roomService.addRoom(roomDTO);

        assertEquals(roomDTO, result);
        verify(roomRepository, times(1)).findByHotelIdAndNumber(roomDTO.getHotelId(), roomDTO.getNumber());
        verify(roomFactory, times(1)).createRoom(roomDTO.getType(), roomDTO.getHotelId(), roomDTO.getNumber(), roomDTO.getPrice());
        verify(roomRepository, times(1)).save(room);
        verify(roomMapper, times(1)).mapToDto(room);
    }

    @Test
    void testAddRoom_RoomExists() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setHotelId(1L);
        roomDTO.setNumber(101);
        Room existingRoom = new Room();

        when(roomRepository.findByHotelIdAndNumber(roomDTO.getHotelId(), roomDTO.getNumber())).thenReturn(existingRoom);

        assertThrows(RoomException.class, () -> roomService.addRoom(roomDTO));
        verify(roomRepository, times(1)).findByHotelIdAndNumber(roomDTO.getHotelId(), roomDTO.getNumber());
        verify(roomFactory, never()).createRoom(anyString(), anyLong(), anyInt(), anyDouble());
        verify(roomRepository, never()).save(any(Room.class));
        verify(roomMapper, never()).mapToDto(any(Room.class));
    }

    @Test
    void testUpdateRoom_Success() {
        Long roomId = 1L;
        RoomDTO roomDTO = new RoomDTO();
        Room existingRoom = new Room();

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(existingRoom));
        when(roomMapper.mapFromDto(existingRoom, roomDTO)).thenReturn(existingRoom);
        when(roomRepository.save(existingRoom)).thenReturn(existingRoom);
        when(roomMapper.mapToDto(existingRoom)).thenReturn(roomDTO);

        RoomDTO result = roomService.updateRoom(roomId, roomDTO);

        assertEquals(roomDTO, result);
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomMapper, times(1)).mapFromDto(existingRoom, roomDTO);
        verify(roomRepository, times(1)).save(existingRoom);
        verify(roomMapper, times(1)).mapToDto(existingRoom);
    }

    @Test
    void testUpdateRoom_NotFound() {
        Long roomId = 1L;
        RoomDTO roomDTO = new RoomDTO();

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(RoomException.class, () -> roomService.updateRoom(roomId, roomDTO));
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomMapper, never()).mapFromDto(any(Room.class), any(RoomDTO.class));
        verify(roomRepository, never()).save(any(Room.class));
        verify(roomMapper, never()).mapToDto(any(Room.class));
    }

    @Test
    void testGetRoomPrice_Success() {
        Long roomId = 1L;
        Room room = new Room();
        room.setPrice(100.0);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        double result = roomService.getRoomPrice(roomId);

        assertEquals(100.0, result);
        verify(roomRepository, times(1)).findById(roomId);
    }

    @Test
    void testGetRoomPrice_NotFound() {
        Long roomId = 1L;

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(RoomException.class, () -> roomService.getRoomPrice(roomId));
        verify(roomRepository, times(1)).findById(roomId);
    }

    @Test
    void testDeleteRoom() {
        Long roomId = 1L;

        roomService.deleteRoom(roomId);

        verify(roomRepository, times(1)).deleteById(roomId);
    }

    @Test
    void testToggleCleanStatus_Success() {
        Long roomId = 1L;
        Room room = new Room();
        room.setClean(false);
        RoomDTO roomDTO = new RoomDTO();

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(roomRepository.save(room)).thenReturn(room);
        when(roomMapper.mapToDto(room)).thenReturn(roomDTO);

        RoomDTO result = roomService.toggleCleanStatus(roomId);

        assertEquals(roomDTO, result);
        assertTrue(room.isClean());
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomRepository, times(1)).save(room);
        verify(roomMapper, times(1)).mapToDto(room);
    }

    @Test
    void testToggleCleanStatus_NotFound() {
        Long roomId = 1L;

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(RoomException.class, () -> roomService.toggleCleanStatus(roomId));
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomRepository, never()).save(any(Room.class));
        verify(roomMapper, never()).mapToDto(any(Room.class));
    }
}
