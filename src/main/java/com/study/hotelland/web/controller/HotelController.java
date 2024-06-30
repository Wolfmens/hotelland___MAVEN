package com.study.hotelland.web.controller;

import com.study.hotelland.service.HotelService;
import com.study.hotelland.web.dto.filter.HotelFilter;
import com.study.hotelland.web.dto.hotel.HotelRequest;
import com.study.hotelland.web.dto.hotel.HotelResponse;
import com.study.hotelland.web.dto.hotel.HotelResponseList;
import com.study.hotelland.web.dto.hotel.HotelResponseWithRatingAndNumberRatings;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotelland/hotel")
@Validated
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public HotelResponseList findAll(@RequestParam @NotNull Integer pageSize,
                                     @RequestParam @NotNull Integer pageNumber) {
        return hotelService.findAll(pageSize, pageNumber);
    }

    @GetMapping("/{id}")
    public HotelResponse findById(@PathVariable(name = "id") @NotNull Long hotelId) {
        return hotelService.findById(hotelId);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public HotelResponse create(@RequestBody HotelRequest request) {
        return hotelService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HotelResponse update(@PathVariable(name = "id") @NotNull Long hotelId, @RequestBody HotelRequest request) {
        return hotelService.update(hotelId, request);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteById(@PathVariable(name = "id") @NotNull Long hotelId) {
        hotelService.deleteById(hotelId);
    }

    @PatchMapping("/rating/{id}")
    public HotelResponseWithRatingAndNumberRatings addRatingToHotel(@RequestParam
                                                                    @Range(min = 1L, max = 5L, message = "The rating " +
                                                                            "provided is outside the required range." +
                                                                            "Must be from 1 to 5.")
                                                                    Long newMark,
                                                                    @PathVariable(value = "id") @NotNull Long hotelId) {

        return hotelService.addHotelRating(newMark, hotelId);
    }

    @GetMapping("/filter-by")
    public HotelResponseList filterBy(@Valid HotelFilter filter) {
        return hotelService.filterBy(filter);
    }
}
