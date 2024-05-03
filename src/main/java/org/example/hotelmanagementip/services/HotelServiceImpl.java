package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.HotelDTO;
import org.example.hotelmanagementip.entity.Hotel;
import org.example.hotelmanagementip.exception.HotelException;
import org.example.hotelmanagementip.mapper.HotelMapper;
import org.example.hotelmanagementip.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HotelServiceImpl implements HotelService {



    private final HotelRepository hotelRepository;

    private final HotelMapper hotelMapper;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
    }


    @Override
    public List<HotelDTO> getAllHotels() {
        return hotelMapper.mapToDto(hotelRepository.findAll());
    }

    @Override
    public HotelDTO getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return hotelMapper.mapToDTO(hotel);
    }

    @Override
    public HotelDTO saveHotel(HotelDTO hotelDTO) {

        Hotel hotel = hotelMapper.mapFromDTO(hotelDTO);
        hotel = hotelRepository.save(hotel);
        return hotelMapper.mapToDTO(hotel);
    }

    @Override
    public void deleteHotelById(Long id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public HotelDTO updateHotel(Long hotelId, HotelDTO hotelDTO) {
        Hotel existingHotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelException("Hotel with ID " + hotelId + " not found."));

        existingHotel = hotelMapper.mapFromDTO(existingHotel, hotelDTO);
        hotelRepository.save(existingHotel);
        return hotelMapper.mapToDTO(existingHotel);
    }
}