package com.study.hotelland.statistic.repository;

import com.study.hotelland.statistic.entity.ReservationRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationRecordRepository extends MongoRepository<ReservationRecord, String> {
}
