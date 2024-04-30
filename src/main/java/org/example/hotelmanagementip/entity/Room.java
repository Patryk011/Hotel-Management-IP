package org.example.hotelmanagementip.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name="Room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Column(name = "room_number")
    private int number;

    @Column(name = "room_type")
    private String type;

    @Column(name = "room_price")
    private double price;

    @Column(name = "isFree")
    private boolean isFree;
}
