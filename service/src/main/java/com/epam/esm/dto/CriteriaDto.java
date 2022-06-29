package com.epam.esm.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class CriteriaDto {
    @Min(1)
    private Integer page;
    @Min(1)
    @Max(30)
    private Integer size;
    private String sorting;
    private Long totalSize;
    private Map<String, String> searchParam;
}
