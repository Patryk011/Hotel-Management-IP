package org.example.hotelmanagementip.controller;

import org.example.hotelmanagementip.dto.RoomDTO;
import org.example.hotelmanagementip.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {


    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/all")
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }


    @GetMapping("/available")
    public List<RoomDTO> getAvailableRooms() {
        return roomService.getAvailableRooms();
    }

    @GetMapping("/{id}")
    public RoomDTO getRoomById(@PathVariable Long id) {
        return roomService.getById(id);
    }

    @PutMapping("/{id}/toggleClean")
    public RoomDTO toggleCleanStatus(@PathVariable Long id) {
        return roomService.toggleCleanStatus(id);
    }

    @PutMapping("/{id}")
    public RoomDTO updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        return roomService.updateRoom(id, roomDTO);
    }

    @PostMapping
    public RoomDTO addRoom(@RequestBody RoomDTO roomDTO) {
        return roomService.addRoom(roomDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }





}
