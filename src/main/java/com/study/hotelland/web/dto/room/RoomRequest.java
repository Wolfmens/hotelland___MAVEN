package com.study.hotelland.web.dto.room;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    private String name;

    private String description;

    private Long number;

    private Long price;

    private Long maxPeople;

    private Long hotelId;

}
