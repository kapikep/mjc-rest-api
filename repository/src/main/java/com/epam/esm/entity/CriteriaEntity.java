package com.epam.esm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class CriteriaEntity {
    private Integer page;
    private Integer size;
    private String sorting;
    private Long totalSize;
    private Map<String, String> searchParam;

    public CriteriaEntity(Integer page, Integer size, String sorting) {
        this.page = page;
        this.size = size;
        this.sorting = sorting;
    }

    public void addSearchParam(String fieldName, String fieldValue){
        if(searchParam == null){
            searchParam = new HashMap<>();
        }
        searchParam.put(fieldName, fieldValue);
    }
}
