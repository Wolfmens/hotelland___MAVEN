package com.study.hotelland.service;

import com.study.hotelland.entity.Room;
import com.study.hotelland.util.model.ActionListBlokedDates;
import com.study.hotelland.web.dto.filter.RoomFilter;
import com.study.hotelland.web.dto.room.RoomRequest;
import com.study.hotelland.web.dto.room.RoomResponse;
import com.study.hotelland.web.dto.room.RoomResponseList;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    RoomResponseList findAll();

    RoomResponse findByIdForResponse(Long id);

    RoomResponse create(RoomRequest request);

    RoomResponse update(Long roomId, RoomRequest request);

    void delete(Long id);

    RoomResponseList filterBy(RoomFilter filter);

    Room findRoomByIdFromBD(Long id);

    void updateRoomsBlockDates(List<LocalDate> datesListForUpdate, Room roomForUpdate, ActionListBlokedDates action);

}
