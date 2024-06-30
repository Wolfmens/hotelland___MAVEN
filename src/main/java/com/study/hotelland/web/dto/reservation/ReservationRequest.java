package com.study.hotelland.web.dto.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    @NotNull
    private String arrival;

    @NotNull
    private String departure;

    @NotNull
    private Long visitorId;

    @NotNull
    private Long roomId;
}
