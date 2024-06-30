package com.study.hotelland.service;

import com.study.hotelland.entity.Reservation;
import com.study.hotelland.web.dto.reservation.ReservationRequest;
import com.study.hotelland.web.dto.reservation.ReservationResponse;
import com.study.hotelland.web.dto.reservation.ReservationResponseList;

import java.util.List;

public interface ReservationService {

    Reservation findById(Long id);

    ReservationResponseList findAll();

    ReservationResponse create(ReservationRequest request);

    Reservation update(Reservation reservation);

    void delete(Long id);

}
