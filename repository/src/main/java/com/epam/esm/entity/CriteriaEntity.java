package com.epam.esm.entity;

import lombok.Data;

import java.util.Map;

@Data
public class CriteriaEntity {
    private Integer page;
    private Integer size;
    private String sorting;
    private Long totalSize;
    private Map<String, String> searchParam;
}
