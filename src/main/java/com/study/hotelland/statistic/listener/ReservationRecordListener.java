package com.study.hotelland.statistic.listener;

import com.study.hotelland.statistic.entity.ReservationRecord;
import com.study.hotelland.statistic.event.ReservationRecordEvent;
import com.study.hotelland.statistic.service.ReservationRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationRecordListener {

    private final ReservationRecordService reservationRecordService;

    @KafkaListener(
            topics = "${app.kafka.property.reservationRecordTopic}",
            groupId = "${app.kafka.property.groupId}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenRegistrationVisitorEvent(@Payload ConsumerRecord<String, Object> event,
                                               @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) Long key,
                                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                               @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
                                               @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received message: {}", event);
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);

        ReservationRecordEvent listenersEvent = (ReservationRecordEvent) event.value();

        ReservationRecord reservationRecord = new ReservationRecord();
        reservationRecord.setVisitorId(listenersEvent.getVisitorId());
        reservationRecord.setArrivalDate(listenersEvent.getArrivalDate());
        reservationRecord.setDepartureDate(listenersEvent.getDepartureDate());

        reservationRecordService.create(reservationRecord);

        log.info("ReservationRecord {} create in DB", reservationRecord);
    }

}
