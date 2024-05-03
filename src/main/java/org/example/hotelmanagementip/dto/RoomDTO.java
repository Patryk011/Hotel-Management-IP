package org.example.hotelmanagementip.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {

    private Long id;
    private Long hotelId;
    private int number;
    private String type;
    private double price;

    private boolean isFree;
}