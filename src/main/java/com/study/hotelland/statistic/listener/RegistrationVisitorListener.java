package com.study.hotelland.statistic.listener;

import com.study.hotelland.statistic.entity.RegistrationVisitor;
import com.study.hotelland.statistic.event.RegistrationVisitorEvent;
import com.study.hotelland.statistic.service.RegistrationVisitorService;
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
public class RegistrationVisitorListener {

    private final RegistrationVisitorService registrationVisitorService;

    @KafkaListener(
            topics = "${app.kafka.property.registrationVisitorTopic}",
            groupId = "${app.kafka.property.groupId}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenRegistrationVisitorEvent(@Payload ConsumerRecord<String, Object> event,
                                               @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) Long key,
                                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                               @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
                                               @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received message: {}", event);
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);

        RegistrationVisitorEvent listenersEvent = (RegistrationVisitorEvent) event.value();

        RegistrationVisitor registrationVisitor = new RegistrationVisitor();
        registrationVisitor.setVisitorId(listenersEvent.getVisitorId());
        registrationVisitorService.create(registrationVisitor);

        log.info("RegistrationVisitor {} create in DB", registrationVisitor);
    }
}
