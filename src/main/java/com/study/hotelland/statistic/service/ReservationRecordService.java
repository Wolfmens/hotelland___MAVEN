package com.study.hotelland.statistic.service;

import com.study.hotelland.statistic.entity.ReservationRecord;

import java.util.List;

public interface ReservationRecordService {

    ReservationRecord findById(String id);

    List<ReservationRecord> findAll();

    ReservationRecord create(ReservationRecord reservationRecord);

    ReservationRecord update(String id, ReservationRecord reservationRecord);

    void delete(String id);
}
