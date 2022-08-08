package com.epam.esm.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@Data
public class CriteriaDto {

    @Positive
    private Integer page;
    @Min(1)
    @Max(30)
    private Integer size;
    @Size(min=2, max=15)
    private String sorting;
    private Long totalSize;
    private Map<@Size(min=2, max=40)String, @Size(min=2, max=40) String> searchParam;

    public void addSearchParam(String fieldName, String fieldValue){
        if(searchParam == null){
            searchParam = new HashMap<>();
        }
        searchParam.put(fieldName, fieldValue);
    }
}
