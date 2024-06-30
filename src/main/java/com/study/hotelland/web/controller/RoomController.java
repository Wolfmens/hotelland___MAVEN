package com.study.hotelland.web.controller;

import com.study.hotelland.service.RoomService;
import com.study.hotelland.web.dto.filter.RoomFilter;
import com.study.hotelland.web.dto.room.RoomRequest;
import com.study.hotelland.web.dto.room.RoomResponse;
import com.study.hotelland.web.dto.room.RoomResponseList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotelland/room")
@RequiredArgsConstructor
@Validated
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public RoomResponseList findAll() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    public RoomResponse findById(@PathVariable @NotNull Long id) {
        return roomService.findByIdForResponse(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public RoomResponse create(@RequestBody RoomRequest request) {
        return roomService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RoomResponse update(@PathVariable(name = "id") @NotNull Long id, @RequestBody RoomRequest request) {
        return roomService.update(id, request);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull Long id) {
        roomService.delete(id);
    }

    @GetMapping("/filter-by")
    public RoomResponseList filterBy(@Valid RoomFilter filter) {
        return roomService.filterBy(filter);
    }
}
