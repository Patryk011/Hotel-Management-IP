package org.example.hotelmanagementip.mapper;

import org.example.hotelmanagementip.dto.HotelDTO;
import org.example.hotelmanagementip.entity.Hotel;
import org.example.hotelmanagementip.entity.Room;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HotelMapper {
    private final RoomMapper roomMapper;

    public HotelMapper(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }





    public HotelDTO mapToDTO(Hotel hotel) {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setId(hotel.getId());
        hotelDTO.setName(hotel.getName());
        hotelDTO.setAddress(hotel.getAddress());
        hotelDTO.setRooms(roomMapper.mapToDto(hotel.getRooms()));
        return hotelDTO;
    }

    public List<HotelDTO> mapToDto(Collection<Hotel> hotels) {
        return hotels.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Hotel mapFromDTO(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelDTO.getName());
        hotel.setAddress(hotelDTO.getAddress());

        List<Room> rooms = hotelDTO.getRooms().stream()
                .map(roomDTO -> roomMapper.mapFromDto(roomDTO))
                .collect(Collectors.toList());

        hotel.setRooms(rooms);
        return hotel;
    }
    public Hotel mapFromDTO(Hotel hotel, HotelDTO hotelDTO) {

        hotel.setName(hotelDTO.getName());
        hotel.setAddress(hotelDTO.getAddress());

        List<Room> rooms = hotelDTO.getRooms().stream()
                .map(roomDTO -> roomMapper.mapFromDto(roomDTO))
                .collect(Collectors.toList());

        hotel.setRooms(rooms);
        return hotel;
    }

}

