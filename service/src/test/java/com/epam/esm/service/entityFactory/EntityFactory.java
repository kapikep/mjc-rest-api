package com.epam.esm.service.entityFactory;

import com.epam.esm.entity.CriteriaEntity;

import java.util.HashMap;

import static com.epam.esm.repository.constant.Constant.ID;

public class EntityFactory {
    private static final CriteriaEntity criteriaEntity1 = getNewCriteriaEntity1();
    private static final CriteriaEntity criteriaEntity2 = getNewCriteriaEntity2();

    public static CriteriaEntity getNewCriteriaEntityWithDefaultVal() {
        return new CriteriaEntity(1, 20, "id");
    }

    public static CriteriaEntity getNewCriteriaEntity1() {
        return new CriteriaEntity(3, 6, ID);
    }

    public static CriteriaEntity getNewCriteriaEntity2() {
        CriteriaEntity cr = new CriteriaEntity(9, 15, ID);
        cr.setTotalSize(0L);
        HashMap<String, String> searchParam = new HashMap<>();
        searchParam.put("name", "Victor");
        cr.setSearchParam(searchParam);
        return cr;
    }

    public static CriteriaEntity getCriteriaEntity1() {
        return criteriaEntity1;
    }

    public static CriteriaEntity getCriteriaEntity2() {
        return criteriaEntity2;
    }
}
