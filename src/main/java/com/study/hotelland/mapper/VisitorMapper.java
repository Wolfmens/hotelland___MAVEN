package com.study.hotelland.mapper;

import com.study.hotelland.entity.Visitor;
import com.study.hotelland.web.dto.visitor.VisitorRequest;
import com.study.hotelland.web.dto.visitor.VisitorResponse;
import com.study.hotelland.web.dto.visitor.VisitorResponseList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitorMapper {


    Visitor visitorRequestToVisitorEntity(VisitorRequest request);

    @Mapping(source = "visitorId", target = "id")
    Visitor visitorRequestToVisitorEntity(Long visitorId, VisitorRequest request);

    @Mapping(target = "authority", expression = "java(visitor.getAuthority().name())")
    VisitorResponse visitorEntityToVisitorResponse(Visitor visitor);

    default VisitorResponseList visitorListToVisitorResponseList(List<Visitor> visitorList) {
        VisitorResponseList visitorResponseList = new VisitorResponseList();
        visitorResponseList
                .setVisitorResponsesList(visitorList
                        .stream()
                        .map(this::visitorEntityToVisitorResponse)
                        .toList());

        return visitorResponseList;
    }

}
