package com.epam.esm.dto;

import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.Map;

@Data
public class CriteriaDto {

    @Positive
    private Integer page;
    @Min(1)
    @Max(30)
    private Integer size;
    private String sorting;
    private Long totalSize;
    private Map<String, String> searchParam;

    public void addSearchParam(String fieldName, String fieldValue){
        if(searchParam == null){
            searchParam = new HashMap<>();
        }
        searchParam.put(fieldName, fieldValue);
    }
}
