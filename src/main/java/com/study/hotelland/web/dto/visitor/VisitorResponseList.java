package com.study.hotelland.web.dto.visitor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorResponseList {

    private List<VisitorResponse> visitorResponsesList = new ArrayList<>();

}
