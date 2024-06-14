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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void saveHotel() {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setName("Test Hotel");
        hotelDTO.setAddress("Test Address");

        Hotel hotel = Hotel.builder()
                .setName("Test Hotel")
                .setAddress("Test Address")
                .build();

        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        when(hotelMapper.mapToDTO(any(Hotel.class))).thenReturn(hotelDTO);

        HotelDTO savedHotel = hotelService.saveHotel(hotelDTO);


        assertEquals("Test Hotel", savedHotel.getName());
        assertEquals("Test Address", savedHotel.getAddress());
    }

    @Test
    void testGetAllHotels() {
        List<Hotel> hotels = Arrays.asList(
                Hotel.builder().setId(1L).setName("Hotel 1").setAddress("Address 1").build(),
                Hotel.builder().setId(2L).setName("Hotel 2").setAddress("Address 2").build()
        );
        List<HotelDTO> hotelDTOs = Arrays.asList(
                HotelDTO.builder().setId(1L).setName("HotelDTO 1").setAddress("AddressDTO 1").build(),
                HotelDTO.builder().setId(2L).setName("HotelDTO 2").setAddress("AddressDTO 2").build()
        );

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
        Hotel hotel = Hotel.builder().setId(hotelId).setName("Hotel Test").setAddress("Address Test").build();
        HotelDTO hotelDTO = HotelDTO.builder().setId(hotelId).setName("HotelDTO Test").setAddress("AddressDTO Test").build();

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
    void testDeleteHotelById() {
        Long hotelId = 1L;

        doNothing().when(hotelRepository).deleteById(hotelId);

        hotelService.deleteHotelById(hotelId);

        verify(hotelRepository, times(1)).deleteById(hotelId);
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

    @Test
    void updateHotel() {
        Long hotelId = 1L;
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setName("Updated Hotel");
        hotelDTO.setAddress("Updated Address");

        Hotel existingHotel = Hotel.builder()
                .setId(hotelId)
                .setName("Existing Hotel")
                .setAddress("Existing Address")
                .build();

        Hotel updatedHotel = Hotel.builder()
                .setId(hotelId)
                .setName("Updated Hotel")
                .setAddress("Updated Address")
                .build();

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(existingHotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(updatedHotel);
        when(hotelMapper.mapToDTO(any(Hotel.class))).thenReturn(hotelDTO);

        HotelDTO result = hotelService.updateHotel(hotelId, hotelDTO);


        assertEquals("Updated Hotel", result.getName());
        assertEquals("Updated Address", result.getAddress());
    }

    @Test
    void updateHotel_NotFound() {
        Long hotelId = 1L;
        HotelDTO hotelDTO = new HotelDTO();

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(HotelException.class, () -> {
            hotelService.updateHotel(hotelId, hotelDTO);
        });
    }
}
