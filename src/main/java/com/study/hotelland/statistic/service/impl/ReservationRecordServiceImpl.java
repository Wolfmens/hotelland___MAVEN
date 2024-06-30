package com.study.hotelland.statistic.service.impl;

import com.study.hotelland.exception.NotFoundEntityException;
import com.study.hotelland.statistic.entity.ReservationRecord;
import com.study.hotelland.statistic.repository.ReservationRecordRepository;
import com.study.hotelland.statistic.service.ReservationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationRecordServiceImpl implements ReservationRecordService {

    private final ReservationRecordRepository reservationRecordRepository;

    @Override
    public ReservationRecord findById(String id) {
        return reservationRecordRepository.findById(id).orElseThrow((() ->
                new NotFoundEntityException(MessageFormat.format("ReservationRecord with id {0} not found", id))));
    }

    @Override
    public List<ReservationRecord> findAll() {
        return reservationRecordRepository.findAll();
    }

    @Override
    public ReservationRecord create(ReservationRecord reservationRecord) {
        return reservationRecordRepository.save(reservationRecord);
    }

    @Override
    public ReservationRecord update(String id, ReservationRecord reservationRecord) {
        ReservationRecord reservationRecordFromBD = findById(id);
        if (Objects.nonNull(reservationRecord.getVisitorId())) {
            reservationRecordFromBD.setVisitorId(reservationRecord.getVisitorId());
        }
        if (StringUtils.hasText(reservationRecord.getArrivalDate())) {
            reservationRecordFromBD.setArrivalDate(reservationRecord.getArrivalDate());
        }
        if (StringUtils.hasText(reservationRecord.getDepartureDate())) {
            reservationRecordFromBD.setDepartureDate(reservationRecord.getDepartureDate());
        }
        return reservationRecordRepository.save(reservationRecordFromBD);
    }

    @Override
    public void delete(String id) {
        reservationRecordRepository.deleteById(id);
    }
}
