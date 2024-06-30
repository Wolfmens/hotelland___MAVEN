package com.study.hotelland.web.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequest {

    private String name;

    private String headline;

    private String city;

    private String address;

    private Long distanceToCenter;

}
