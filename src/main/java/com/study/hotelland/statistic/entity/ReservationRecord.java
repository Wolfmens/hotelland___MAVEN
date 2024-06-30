package com.study.hotelland.statistic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reservation_records")
public class ReservationRecord {

    @Id
    private String id;

    private Long visitorId;

    private String arrivalDate;

    private String departureDate;

}
