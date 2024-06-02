package org.example.hotelmanagementip.services;

import org.example.hotelmanagementip.dto.RoomDTO;
import org.example.hotelmanagementip.entity.Room;
import org.example.hotelmanagementip.exception.RoomException;
import org.example.hotelmanagementip.factory.RoomFactory;
import org.example.hotelmanagementip.mapper.RoomMapper;
import org.example.hotelmanagementip.repository.HotelRepository;
import org.example.hotelmanagementip.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;
    private final RoomFactory roomFactory;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository, RoomMapper roomMapper, RoomFactory roomFactory) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.roomMapper = roomMapper;
        this.roomFactory = roomFactory;
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomMapper.mapToDto(roomRepository.findAll());
    }

    @Override
    public List<RoomDTO> getAvailableRooms() {
        return roomMapper.mapToDto(roomRepository.findByIsFree(true));
    }

    @Override
    public List<RoomDTO> findByHotelId(Long hotelId) {
        List<Room> rooms = roomRepository.findByHotelId(hotelId);
        return roomMapper.mapToDto(rooms);
    }

    @Override
    public RoomDTO getById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return roomMapper.mapToDto(room);
    }

    @Override
    public RoomDTO addRoom(RoomDTO roomDTO) {
        Room existingRoom = roomRepository.findByHotelIdAndNumber(roomDTO.getHotelId(), roomDTO.getNumber());
        if (existingRoom != null) {
            throw new RoomException("Room with this number " + roomDTO.getNumber() + " exists in this Hotel.");
        }

        Room room = roomFactory.createRoom(roomDTO.getType(), roomDTO.getHotelId(), roomDTO.getNumber(), roomDTO.getPrice());
        room = roomRepository.save(room);

        return roomMapper.mapToDto(room);
    }

    @Override
    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RoomException("Room with ID " + id + " not found."));

        existingRoom = roomMapper.mapFromDto(existingRoom, roomDTO);
        roomRepository.save(existingRoom);
        return roomMapper.mapToDto(existingRoom);
    }

    @Override
    public double getRoomPrice(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RoomException("Room with ID " + id + " not found."));
        return room.getPrice();
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
