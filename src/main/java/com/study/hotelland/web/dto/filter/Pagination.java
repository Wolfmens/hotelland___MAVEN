package com.study.hotelland.web.dto.filter;

import com.study.hotelland.validator.CheckPaginationFields;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@CheckPaginationFields
public abstract class Pagination {

    private Integer pageSize;

    private Integer pageNumber;

}
