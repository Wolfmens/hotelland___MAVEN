package com.study.hotelland.statistic.service;

import com.study.hotelland.statistic.entity.RegistrationVisitor;
import com.study.hotelland.statistic.entity.ReservationRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticCSVService {

    private final RegistrationVisitorService registrationVisitorService;

    private final ReservationRecordService reservationRecordService;

    public byte[] getStatisticsInCSV() {
        StringBuilder csvStatistic = new StringBuilder();
        List<RegistrationVisitor> registrationVisitors = registrationVisitorService.findAll();
        List<ReservationRecord> reservationRecords = reservationRecordService.findAll();

        csvStatistic.append("registration_visitors:\n").append("visitor_id\n");
        for (RegistrationVisitor visitor : registrationVisitors) {
            csvStatistic.append(visitor.getVisitorId()).append("\n");
        }
        csvStatistic.append("reservation_records:\n").append("visitor_id,arrival_date,departure_date\n");
        for (ReservationRecord record: reservationRecords) {
            csvStatistic
                    .append(record.getVisitorId()).append(",")
                    .append(record.getArrivalDate()).append(",")
                    .append(record.getDepartureDate()).append("\n");
        }

        return csvStatistic.toString().getBytes(StandardCharsets.UTF_8);
    }

}
