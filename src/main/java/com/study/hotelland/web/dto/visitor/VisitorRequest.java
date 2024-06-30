package com.study.hotelland.web.dto.visitor;

import com.study.hotelland.validator.ValidVisitorName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorRequest {

    @ValidVisitorName
    private String name;

    private String password;

    private String email;

}
