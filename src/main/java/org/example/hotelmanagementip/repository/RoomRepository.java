package org.example.hotelmanagementip.repository;

import org.example.hotelmanagementip.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelId(Long hotelId);

    //    Room findByRoomNumber(int number);
    Room findByHotelIdAndNumber(Long hotelId, int number);

    List<Room> findByIsFree(boolean isFree);
    Room findByType(String type);
}

