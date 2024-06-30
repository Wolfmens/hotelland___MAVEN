package com.study.hotelland.service;

import com.study.hotelland.entity.RoleType;
import com.study.hotelland.entity.Visitor;
import com.study.hotelland.web.dto.visitor.VisitorRequest;
import com.study.hotelland.web.dto.visitor.VisitorResponse;
import com.study.hotelland.web.dto.visitor.VisitorResponseList;

import java.util.List;

public interface VisitorService {

    VisitorResponseList findAll();

    VisitorResponse findById(Long id);

    VisitorResponse create(VisitorRequest request, RoleType type);

    VisitorResponse update(Long visitorId, VisitorRequest request);

    void delete(Long id);

    Visitor findByName(String name);

    Boolean existsByName(String name);

    Visitor findVisitorByIdFromBD(Long id);
}
