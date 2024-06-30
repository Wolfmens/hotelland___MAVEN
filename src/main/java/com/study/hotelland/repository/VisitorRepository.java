package com.study.hotelland.repository;

import com.study.hotelland.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    Optional<Visitor> findByName(String name);

    Boolean existsByNameAndEmail(String name, String email);

    Boolean existsByName(String name);

}
