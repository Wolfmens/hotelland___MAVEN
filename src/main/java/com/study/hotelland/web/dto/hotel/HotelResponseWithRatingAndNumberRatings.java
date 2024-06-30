package com.study.hotelland.web.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponseWithRatingAndNumberRatings {

    private String name;

    private String headline;

    private String city;

    private String address;

    private Long distanceToCenter;

    private Double hotelRating;

    private Long numberRatings;

}
