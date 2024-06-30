package com.study.hotelland.service;

import com.study.hotelland.entity.Hotel;
import com.study.hotelland.web.dto.filter.HotelFilter;
import com.study.hotelland.web.dto.hotel.HotelRequest;
import com.study.hotelland.web.dto.hotel.HotelResponse;
import com.study.hotelland.web.dto.hotel.HotelResponseList;
import com.study.hotelland.web.dto.hotel.HotelResponseWithRatingAndNumberRatings;

import java.util.List;

public interface HotelService {

    HotelResponseList findAll(Integer pageSize, Integer pageNumber);

    HotelResponse findById(Long id);

    HotelResponse create(HotelRequest request);

    HotelResponse update(Long hotelId, HotelRequest request);

    void deleteById(Long id);

    HotelResponseWithRatingAndNumberRatings addHotelRating(Long newMark, Long hotelId);

    HotelResponseList filterBy(HotelFilter filter);

    Hotel findHotelByIdFromBD(Long id);

}
