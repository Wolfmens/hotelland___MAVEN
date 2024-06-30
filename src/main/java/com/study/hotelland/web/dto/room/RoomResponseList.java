package com.study.hotelland.web.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseList {

    private List<RoomResponse> roomResponseList = new ArrayList<>();

}
