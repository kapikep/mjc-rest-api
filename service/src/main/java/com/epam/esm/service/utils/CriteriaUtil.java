package com.epam.esm.service.utils;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.service.exception.ValidateException;

public class CriteriaUtil {

    public static CriteriaEntity criteriaDtoToEntityConverting(CriteriaDto dto) throws ValidateException {
        CriteriaEntity entity = new CriteriaEntity();
        int size = 20;
        int page = 1;

        try {
            size = ServiceUtil.parseInt(dto.getSize());
        }catch (ValidateException ignored){}

        try {
            page = ServiceUtil.parseInt(dto.getPage());
        }catch (ValidateException ignored){}

        entity.setPage(page);

        entity.setLimit(size);
        entity.setSorting(dto.getSorting());

        return entity;
    }
}
