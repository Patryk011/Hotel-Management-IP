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
public class EmailDTO {



    private int id;

    private String subject;
    private String from;
    private Date receivedDate;

    private String content;

}
