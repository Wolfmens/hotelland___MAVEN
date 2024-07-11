package com.study.hotelland.web.controller;

import com.study.hotelland.service.ReservationService;
import com.study.hotelland.web.dto.reservation.ReservationRequest;
import com.study.hotelland.web.dto.reservation.ReservationResponse;
import com.study.hotelland.web.dto.reservation.ReservationResponseList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotelland/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ReservationResponseList findAll() {
        return reservationService.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ReservationResponse create(@RequestBody @Valid ReservationRequest request) {
        return reservationService.create(request);
    }
}
