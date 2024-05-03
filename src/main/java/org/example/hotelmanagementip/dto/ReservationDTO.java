package org.example.hotelmanagementip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;
    private Long customerId;

    private String customerEmail;
    private Long roomId;
    private Long hotelId;
    private Date startDate;
    private Date endDate;
    private String status;
}
