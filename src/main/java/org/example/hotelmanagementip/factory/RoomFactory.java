package org.example.hotelmanagementip.factory;

import org.example.hotelmanagementip.entity.Room;

public interface RoomFactory {
    Room createRoom(String type, Long hotelId, int number, double price);
}
