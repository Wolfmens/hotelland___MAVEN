package com.study.hotelland.service.impl;

import com.study.hotelland.entity.Reservation;
import com.study.hotelland.entity.Room;
import com.study.hotelland.exception.NotFoundEntityException;
import com.study.hotelland.exception.NotPossibleReservationRoom;
import com.study.hotelland.mapper.ReservationMapper;
import com.study.hotelland.repository.ReservationRepository;
import com.study.hotelland.service.ReservationService;
import com.study.hotelland.service.RoomService;
import com.study.hotelland.service.VisitorService;
import com.study.hotelland.statistic.event.ReservationRecordEvent;
import com.study.hotelland.util.model.ActionListBlokedDates;
import com.study.hotelland.web.dto.reservation.ReservationRequest;
import com.study.hotelland.web.dto.reservation.ReservationResponse;
import com.study.hotelland.web.dto.reservation.ReservationResponseList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;

    private final RoomService roomService;

    private final VisitorService visitorService;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ReservationMapper reservationMapper;

    @Value("${app.kafka.property.reservationRecordTopic}")
    private String reservationRecordTopic;

    @Override
    public ReservationResponseList findAll() {
        return reservationMapper.reservationListToReservationResponseList(repository.findAll());
    }

    @Override
    public Reservation findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundEntityException(MessageFormat.format("Reservation with id {0} not found", id)));
    }

    @Override
    @Transactional
    public ReservationResponse create(ReservationRequest request) {
        Reservation reservationFromRequest = reservationMapper.reservationRequestToReservationEntity(request);
        reservationFromRequest.setVisitor(visitorService.findVisitorByIdFromBD(request.getVisitorId()));
        reservationFromRequest.setRoom(roomService.findRoomByIdFromBD(request.getRoomId()));

        Room reservationRoom = roomService.findRoomByIdFromBD(reservationFromRequest.getRoom().getId());
        Room reservationRoomAfterUpdatesBlockDates = possibleReservationRoomAndUpdateRoomsBlockDates(
                reservationRoom,
                getListDatesWhichNeedToReservation(
                        reservationFromRequest.getArrival(),
                        reservationFromRequest.getDeparture()));

        reservationFromRequest.setRoom(reservationRoomAfterUpdatesBlockDates);

        ReservationRecordEvent event = new ReservationRecordEvent();
        event.setVisitorId(reservationFromRequest.getVisitor().getId());
        event.setArrivalDate(reservationFromRequest.getArrival().toString());
        event.setDepartureDate(reservationFromRequest.getDeparture().toString());
        kafkaTemplate.send(reservationRecordTopic, event);

        return reservationMapper.reservationEntityToReservationResponse(repository.save(reservationFromRequest));
    }

    @Override
    @Transactional
    public Reservation update(Reservation reservation) {
        Reservation reservationFromBd = findById(reservation.getId());

        if (Objects.nonNull(reservation.getRoom()) && !reservation.getRoom().equals(reservationFromBd.getRoom())) {
            reservationFromBd.setRoom(reservation.getRoom());
        }

        if (StringUtils.hasText(reservation.getArrival().toString()) &&
                StringUtils.hasText(reservation.getDeparture().toString())) {

            Room reservationRoomAfterUpdatesBlockDates = possibleReservationRoomAndUpdateRoomsBlockDates(
                    reservation.getRoom(),
                    getListDatesWhichNeedToReservation(
                            reservationFromBd.getArrival(),
                            reservationFromBd.getDeparture()));
            reservationFromBd.setRoom(reservationRoomAfterUpdatesBlockDates);

            reservationFromBd.setArrival(reservation.getArrival());
            reservationFromBd.setDeparture(reservation.getDeparture());

        }
        if (Objects.nonNull(reservation.getVisitor())) {
            reservationFromBd.setVisitor(reservation.getVisitor());
        }
        return repository.save(reservationFromBd);
    }

    @Override
    public void delete(Long id) {
        Reservation reservationFromBd = findById(id);

        roomService.updateRoomsBlockDates(
                getListDatesWhichNeedToReservation(reservationFromBd.getArrival(), reservationFromBd.getDeparture()),
                reservationFromBd.getRoom(),
                ActionListBlokedDates.REMOVE);

        repository.deleteById(id);
    }

    private Room possibleReservationRoomAndUpdateRoomsBlockDates(Room roomForReservation,
                                                                 List<LocalDate> listDatesWhichNeedToReservation) {

        boolean hasReservationDatedFromChooses =
                roomForReservation.getBlockDates()
                        .stream()
                        .anyMatch(listDatesWhichNeedToReservation::contains);

        if (hasReservationDatedFromChooses) {
            throw new NotPossibleReservationRoom
                    ("Block reservation a room on these dates. " +
                            "Already booked!!! Please, choose another dates arrive and departure");
        } else {
            roomService.updateRoomsBlockDates(
                    listDatesWhichNeedToReservation,
                    roomForReservation,
                    ActionListBlokedDates.ADD);
        }

        return roomForReservation;
    }

    private List<LocalDate> getListDatesWhichNeedToReservation(LocalDate arrival, LocalDate departure) {
        return arrival.datesUntil(departure.plusDays(1)).toList();
    }


}
