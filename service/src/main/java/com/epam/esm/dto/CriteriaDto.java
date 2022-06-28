package com.epam.esm.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class CriteriaDto {

    @Min(value = 0)
    private String page;

    @Min(value = 0)
    private String size;
    private String sorting;

}
