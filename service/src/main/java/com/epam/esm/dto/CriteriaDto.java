package com.epam.esm.dto;

import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.*;
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
    private Map<String, @Size(min=2, max=20) String> searchParam;

    public void addSearchParam(String fieldName, String fieldValue){
        if(searchParam == null){
            searchParam = new HashMap<>();
        }
        searchParam.put(fieldName, fieldValue);
    }
}
