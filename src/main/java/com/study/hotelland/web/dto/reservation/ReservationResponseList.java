package com.study.hotelland.web.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseList {

    private List<ReservationResponse> reservationResponsesList = new ArrayList<>();
}
