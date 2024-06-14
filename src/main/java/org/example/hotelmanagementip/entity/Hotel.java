package org.example.hotelmanagementip.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Entity
@Table(name = "hotel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hotel_id")
    private Long id;

    @Column(name = "hotel_name")
    private String name;

    @Column(name = "hotel_address")
    private String address;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();


    public static class HotelBuilder {
        private Long id;
        private String name;
        private String address;
        private List<Room> rooms = new ArrayList<>();

        public HotelBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public HotelBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public HotelBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public HotelBuilder setRooms(List<Room> rooms) {
            this.rooms = rooms;
            return this;
        }

        public Hotel build() {
            Hotel hotel = new Hotel();
            hotel.setId(this.id);
            hotel.setName(this.name);
            hotel.setAddress(this.address);
            hotel.setRooms(this.rooms);
            return hotel;
        }
    }


    public static HotelBuilder builder() {
        return new HotelBuilder();
    }
}
