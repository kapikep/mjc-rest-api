package com.epam.esm.entity;

import lombok.Data;

@Data
public class CriteriaEntity {
    int page;
    int limit;
    String sorting;

}
