package com.study.hotelland.statistic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "registration_visitors")
public class RegistrationVisitor {

    @Id
    private String id;

    private Long visitorId;

    @ReadOnlyProperty
    private String visitorName;
}
