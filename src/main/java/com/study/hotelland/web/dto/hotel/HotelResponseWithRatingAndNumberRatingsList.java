package com.study.hotelland.web.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponseWithRatingAndNumberRatingsList {

    private List<HotelResponseWithRatingAndNumberRatings> hotelResponseWithRatingAndNumberRatingsList =
            new ArrayList<>();

}
