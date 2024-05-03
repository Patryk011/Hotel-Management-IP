package org.example.hotelmanagementip.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {


    private Long id;
    private Long reservationId;

    private Long customerId;
    private double amount;

    private String status;
    private boolean isPaid;
}
