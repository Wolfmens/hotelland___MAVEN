package com.study.hotelland.statistic.repository;

import com.study.hotelland.statistic.entity.RegistrationVisitor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistrationVisitorRepository extends MongoRepository<RegistrationVisitor, String> {
}
