package com.study.hotelland.service.impl;

import com.study.hotelland.entity.Room;
import com.study.hotelland.exception.HotelIdNotSpecifiedException;
import com.study.hotelland.exception.ListIsEmptyByFilter;
import com.study.hotelland.exception.NotFoundEntityException;
import com.study.hotelland.mapper.RoomMapper;
import com.study.hotelland.repository.ReservationRepository;
import com.study.hotelland.repository.RoomRepository;
import com.study.hotelland.repository.specification.RoomSpecification;
import com.study.hotelland.service.HotelService;
import com.study.hotelland.service.RoomService;
import com.study.hotelland.util.NotNullCopyProperty;
import com.study.hotelland.util.model.ActionListBlokedDates;
import com.study.hotelland.web.dto.filter.RoomFilter;
import com.study.hotelland.web.dto.room.RoomRequest;
import com.study.hotelland.web.dto.room.RoomResponse;
import com.study.hotelland.web.dto.room.RoomResponseList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;

    private final ReservationRepository reservationRepository;

    private final HotelService hotelService;

    private final RoomMapper roomMapper;

    @Override
    public RoomResponseList findAll() {
        return roomMapper.roomListToRoomResponseList(repository.findAll());
    }

    @Override
    public RoomResponse findByIdForResponse(Long id) {
        return roomMapper.roomEntityToRoomResponse(findRoomByIdFromBD(id));
    }

    @Override
    public RoomResponse create(RoomRequest request) {
        Room roomFromRequest = roomMapper.roomRequestToRoomEntity(request);
        if (request.getHotelId() == null) {
            throw new HotelIdNotSpecifiedException();
        } else {
            roomFromRequest.setHotel(hotelService.findHotelByIdFromBD(request.getHotelId()));
        }
        roomFromRequest.setBlockDates(new CopyOnWriteArrayList<>());

        return roomMapper.roomEntityToRoomResponse
                (repository.save(roomFromRequest));
    }

    @Override
    public RoomResponse update(Long roomId, RoomRequest request) {
        Room roomFromRequest = roomMapper.roomRequestToRoomEntity(roomId, request);
        Room roomFromDb = findRoomByIdFromBD(roomFromRequest.getId());

        NotNullCopyProperty.copyNonNullProperties(roomFromRequest, roomFromDb, new String[]{"blockDates", "hotel"});

        if (Objects.nonNull(request.getHotelId())) {
            roomFromDb.setHotel(hotelService.findHotelByIdFromBD(request.getHotelId()));
            ;
        }

        return roomMapper.roomEntityToRoomResponse(repository.save(roomFromDb));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        reservationRepository.deleteByRoomId(id);
        repository.deleteById(id);
    }

    @Override
    public RoomResponseList filterBy(RoomFilter filter) {
        Page<Room> listByRequestFilterRoom = repository.findAll(
                RoomSpecification.filterBy(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize()));
        if (listByRequestFilterRoom.getContent().isEmpty()) {
            throw new ListIsEmptyByFilter("Rooms");
        }
        return roomMapper.roomListToRoomResponseList(listByRequestFilterRoom.getContent());
    }

    public Room findRoomByIdFromBD(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(MessageFormat
                        .format("Room with id {0} not found", id)));
    }

    @Override
    public void updateRoomsBlockDates(List<LocalDate> datesListForUpdate, Room room, ActionListBlokedDates action) {
        if (action.name().equals("ADD")) {
            datesListForUpdate.forEach(room.getBlockDates()::add);
        }
        if (action.name().equals("REMOVE")) {
            datesListForUpdate.forEach(room.getBlockDates()::remove);
        }
        repository.save(room);
    }
}
