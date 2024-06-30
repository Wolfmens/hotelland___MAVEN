package com.study.hotelland.web.dto.filter;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelFilter extends Pagination {

    private Long id;

    private String name;

    private String headline;

    private String city;

    private String address;

    private Long distanceToCenter;

    private Double hotelRating;

    private Long numberRatings;
}
