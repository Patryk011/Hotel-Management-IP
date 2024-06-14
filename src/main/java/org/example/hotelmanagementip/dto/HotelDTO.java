package org.example.hotelmanagementip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {

    private Long id;
    private String name;
    private String address;
    private List<RoomDTO> rooms = new ArrayList<>();


    public static class HotelDTOBuilder {
        private Long id;
        private String name;
        private String address;
        private List<RoomDTO> rooms = new ArrayList<>();

        public HotelDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public HotelDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public HotelDTOBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public HotelDTOBuilder setRooms(List<RoomDTO> rooms) {
            this.rooms = rooms;
            return this;
        }

        public HotelDTO build() {
            HotelDTO hotelDTO = new HotelDTO();
            hotelDTO.setId(this.id);
            hotelDTO.setName(this.name);
            hotelDTO.setAddress(this.address);
            hotelDTO.setRooms(this.rooms);
            return hotelDTO;
        }
    }


    public static HotelDTOBuilder builder() {
        return new HotelDTOBuilder();
    }
}