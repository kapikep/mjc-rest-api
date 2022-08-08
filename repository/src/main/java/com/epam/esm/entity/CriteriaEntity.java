package com.epam.esm.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CriteriaEntity {
    private Integer page;
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
