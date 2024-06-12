package org.example.hotelmanagementip.factory;

import org.example.hotelmanagementip.entity.Hotel;
import org.example.hotelmanagementip.entity.Room;
import org.example.hotelmanagementip.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomFactoryImpl implements RoomFactory {

    private final HotelRepository hotelRepository;

    @Autowired
    public RoomFactoryImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Room createRoom(String type, Long hotelId, int number, double price) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new IllegalArgumentException("Invalid hotel ID"));

        Room room = new Room();
        room.setHotel(hotel);
        room.setNumber(number);
        room.setPrice(price);
        room.setType(type);
        room.setFree(true);
        room.setClean(true);
        return room;
    }
}
