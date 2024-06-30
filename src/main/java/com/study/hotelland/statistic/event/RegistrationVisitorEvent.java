package com.study.hotelland.statistic.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationVisitorEvent {

    private Long visitorId;
}
