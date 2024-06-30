package com.study.hotelland.statistic.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRecordEvent {

    private Long visitorId;

    private String arrivalDate;

    private String departureDate;
}
