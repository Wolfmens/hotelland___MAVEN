package com.study.hotelland.statistic.service;

import com.study.hotelland.statistic.entity.RegistrationVisitor;

import java.util.List;

public interface RegistrationVisitorService {

    RegistrationVisitor findById(String id);

    List<RegistrationVisitor> findAll();

    RegistrationVisitor create(RegistrationVisitor registrationVisitor);

    RegistrationVisitor update(String id, RegistrationVisitor registrationVisitor);

    void delete(String id);
}
