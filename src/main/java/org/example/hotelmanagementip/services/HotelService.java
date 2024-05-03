package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.HotelDTO;

import java.util.List;

public interface HotelService {


    List<HotelDTO> getAllHotels();


    HotelDTO getHotelById(Long id);


    HotelDTO saveHotel(HotelDTO hotelDTO);

    HotelDTO updateHotel(Long hotelId, HotelDTO hotelDTO);

    void deleteHotelById(Long id);
}