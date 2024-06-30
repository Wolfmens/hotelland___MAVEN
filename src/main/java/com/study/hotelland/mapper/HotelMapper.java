package com.study.hotelland.mapper;

import com.study.hotelland.entity.Hotel;
import com.study.hotelland.web.dto.hotel.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    Hotel hotelRequestToHotelEntity(HotelRequest request);

    @Mapping(source = "hotelId", target = "id")
    Hotel hotelRequestToHotelEntity(Long hotelId, HotelRequest request);

    HotelResponse hotelEntityToHotelResponse(Hotel hotel);

    HotelResponseWithRatingAndNumberRatings hotelEntityToHotelResponseWithRatingAndNumberRatings(Hotel hotel);

    default HotelResponseList listHotelEntityToHotelResponseLIst(List<Hotel> hotelList) {
        HotelResponseList hotelResponseLIst = new HotelResponseList();
        hotelResponseLIst.setHotelResponses(hotelList
                .stream()
                .map(this::hotelEntityToHotelResponse).toList());

        return hotelResponseLIst;
    }

    default HotelResponseWithRatingAndNumberRatingsList listHotelEntityToHotelResponseWithRatingAndNumberRatingsList
            (List<Hotel> hotelList) {
        HotelResponseWithRatingAndNumberRatingsList hotelResponseWithRatingAndNumberRatingsList
                = new HotelResponseWithRatingAndNumberRatingsList();
        hotelResponseWithRatingAndNumberRatingsList.setHotelResponseWithRatingAndNumberRatingsList(hotelList
                .stream()
                .map(this::hotelEntityToHotelResponseWithRatingAndNumberRatings).toList());

        return hotelResponseWithRatingAndNumberRatingsList;
    }




}
