package com.study.hotelland.repository;

import com.study.hotelland.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    void deleteByRoomId(Long id);

}
