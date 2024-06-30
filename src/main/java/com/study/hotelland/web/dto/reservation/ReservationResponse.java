package com.study.hotelland.web.dto.reservation;

import com.study.hotelland.web.dto.room.RoomResponse;
import com.study.hotelland.web.dto.visitor.VisitorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {

    private Long id;

    private String arrival;

    private String departure;

    private VisitorResponse visitor;

    private RoomResponse room;
}
