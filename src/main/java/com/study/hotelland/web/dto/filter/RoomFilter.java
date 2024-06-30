package com.study.hotelland.web.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomFilter extends Pagination {

    private Long roomId;

    private String description;

    private Long maxPrice;

    private Long minPrice;

    private Long maxPeople;

    private LocalDate arrival;

    private LocalDate departure;

    private Long hotelId;
}
