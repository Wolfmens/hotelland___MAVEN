package com.study.hotelland.web.dto.visitor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorResponse {

    private Long id;

    private String name;

    private String password;

    private String email;

    private String authority;

}
