package com.epam.esm.service.dtoFactory;

import com.epam.esm.dto.CriteriaDto;

import java.util.HashMap;

import static com.epam.esm.repository.constant.Constant.ID;

public class DtoFactory {
    private static final CriteriaDto criteriaDto1 = getNewCriteriaDto1();
    private static final CriteriaDto criteriaDto2 = getNewCriteriaDto2();

    public static CriteriaDto getNewCriteriaDtoWithDefaultVal() {
        return new CriteriaDto(1, 20, "id");
    }

    public static CriteriaDto getNewCriteriaDto1() {
        return new CriteriaDto(3, 6, ID);
    }

    public static CriteriaDto getNewCriteriaDto2() {
        CriteriaDto cr = new CriteriaDto(9, 15, ID);
        HashMap<String, String> searchParam = new HashMap<>();
        searchParam.put("name", "Victor");
        cr.setSearchParam(searchParam);
        cr.setTotalSize(0L);
        return cr;
    }

    public static CriteriaDto getCriteriaDto1() {
        return criteriaDto1;
    }

    public static CriteriaDto getCriteriaDto2() {
        return criteriaDto2;
    }
}
