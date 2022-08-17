package com.epam.esm.repository.impl.EntityFactory;

import com.epam.esm.entity.CriteriaEntity;

public class EntityFactory {
    public static CriteriaEntity getNewCriteriaWithDefaultVal() {
        return new CriteriaEntity(1, 20, "id");
    }
}
