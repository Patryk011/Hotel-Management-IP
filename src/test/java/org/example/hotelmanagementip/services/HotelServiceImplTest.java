package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.HotelDTO;
import org.example.hotelmanagementip.entity.Hotel;
import org.example.hotelmanagementip.exception.HotelException;
import org.example.hotelmanagementip.mapper.HotelMapper;
import org.example.hotelmanagementip.repository.HotelRepository;
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

class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllHotels() {
        List<Hotel> hotels = Arrays.asList(new Hotel(), new Hotel());
        List<HotelDTO> hotelDTOs = Arrays.asList(new HotelDTO(), new HotelDTO());

        when(hotelRepository.findAll()).thenReturn(hotels);
        when(hotelMapper.mapToDto(hotels)).thenReturn(hotelDTOs);

        List<HotelDTO> result = hotelService.getAllHotels();

        assertEquals(hotelDTOs, result);
        verify(hotelRepository, times(1)).findAll();
        verify(hotelMapper, times(1)).mapToDto(hotels);
    }

    @Test
    void testGetHotelById_Success() {
        Long hotelId = 1L;
        Hotel hotel = new Hotel();
        HotelDTO hotelDTO = new HotelDTO();

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(hotelMapper.mapToDTO(hotel)).thenReturn(hotelDTO);

        HotelDTO result = hotelService.getHotelById(hotelId);

        assertEquals(hotelDTO, result);
        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelMapper, times(1)).mapToDTO(hotel);
    }

    @Test
    void testGetHotelById_NotFound() {
        Long hotelId = 1L;

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> hotelService.getHotelById(hotelId));
        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelMapper, never()).mapToDTO(any());
    }

    @Test
    void testSaveHotel() {
        HotelDTO hotelDTO = new HotelDTO();
        Hotel hotel = new Hotel();
        Hotel savedHotel = new Hotel();

        when(hotelMapper.mapFromDTO(hotelDTO)).thenReturn(hotel);
        when(hotelRepository.save(hotel)).thenReturn(savedHotel);
        when(hotelMapper.mapToDTO(savedHotel)).thenReturn(hotelDTO);

        HotelDTO result = hotelService.saveHotel(hotelDTO);

        assertEquals(hotelDTO, result);
        verify(hotelMapper, times(1)).mapFromDTO(hotelDTO);
        verify(hotelRepository, times(1)).save(hotel);
        verify(hotelMapper, times(1)).mapToDTO(savedHotel);
    }

    @Test
    void testDeleteHotelById() {
        Long hotelId = 1L;

        doNothing().when(hotelRepository).deleteById(hotelId);

        hotelService.deleteHotelById(hotelId);

        verify(hotelRepository, times(1)).deleteById(hotelId);
    }

    @Test
    void testUpdateHotel_Success() {
        Long hotelId = 1L;
        HotelDTO hotelDTO = new HotelDTO();
        Hotel existingHotel = new Hotel();
        Hotel updatedHotel = new Hotel();

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(existingHotel));
        when(hotelMapper.mapFromDTO(existingHotel, hotelDTO)).thenReturn(updatedHotel);
        when(hotelRepository.save(updatedHotel)).thenReturn(updatedHotel);
        when(hotelMapper.mapToDTO(updatedHotel)).thenReturn(hotelDTO);

        HotelDTO result = hotelService.updateHotel(hotelId, hotelDTO);

        assertEquals(hotelDTO, result);
        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelMapper, times(1)).mapFromDTO(existingHotel, hotelDTO);
        verify(hotelRepository, times(1)).save(updatedHotel);
        verify(hotelMapper, times(1)).mapToDTO(updatedHotel);
    }

    @Test
    void testUpdateHotel_NotFound() {
        Long hotelId = 1L;
        HotelDTO hotelDTO = new HotelDTO();

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        assertThrows(HotelException.class, () -> hotelService.updateHotel(hotelId, hotelDTO));
        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelMapper, never()).mapFromDTO(any(), any());
        verify(hotelRepository, never()).save(any());
        verify(hotelMapper, never()).mapToDTO(any());
    }
}
