package com.study.hotelland.mapper;

import com.study.hotelland.entity.Reservation;
import com.study.hotelland.web.dto.reservation.ReservationRequest;
import com.study.hotelland.web.dto.reservation.ReservationResponse;
import com.study.hotelland.web.dto.reservation.ReservationResponseList;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {RoomMapper.class,
                VisitorMapper.class}
)
public interface ReservationMapper {


    Reservation reservationRequestToReservationEntity(ReservationRequest request);

    @Mapping(source = "reservationId", target = "id")
    Reservation reservationRequestToReservationEntity(Long reservationId, ReservationRequest request);

    ReservationResponse reservationEntityToReservationResponse(Reservation reservation);

    default ReservationResponseList reservationListToReservationResponseList(List<Reservation> reservationList) {
        ReservationResponseList reservationResponseList = new ReservationResponseList();
        reservationResponseList.setReservationResponsesList(
                reservationList
                        .stream()
                        .map(this::reservationEntityToReservationResponse)
                        .toList());

        return reservationResponseList;
    }

}

