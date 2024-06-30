package com.study.hotelland.web.controller;

import com.study.hotelland.entity.RoleType;
import com.study.hotelland.mapper.VisitorMapper;
import com.study.hotelland.service.VisitorService;
import com.study.hotelland.web.dto.visitor.VisitorRequest;
import com.study.hotelland.web.dto.visitor.VisitorResponse;
import com.study.hotelland.web.dto.visitor.VisitorResponseList;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotelland/visitor")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @GetMapping
    public VisitorResponseList findAll() {
        return visitorService.findAll();
    }

    @GetMapping("/{id}")
    public VisitorResponse findById(@PathVariable("id") @NotNull Long visitorId) {
        return visitorService.findById(visitorId);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public VisitorResponse create(@RequestBody VisitorRequest request,
                                  @RequestParam @NotNull RoleType type) {
        return visitorService.create(request, type);
    }

    @PutMapping("/{id}")
    public VisitorResponse update(@PathVariable("id") @NotNull Long visitorId,
                                  @RequestBody VisitorRequest request) {
        return visitorService.update(visitorId, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @NotNull Long visitorId) {
        visitorService.delete(visitorId);
    }


}
