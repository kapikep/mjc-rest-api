package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.service.exception.ValidateException;

public class CriteriaUtil {

    public static CriteriaEntity criteriaDtoToEntityConverting(CriteriaDto dto) throws ValidateException {
        CriteriaEntity entity = new CriteriaEntity();

        entity.setPage(dto.getPage());
        entity.setSize(dto.getSize());
        entity.setSorting(dto.getSorting());

        return entity;
    }

    public static CriteriaDto criteriaEntityToDtoConverting(CriteriaEntity entity) throws ValidateException {
        CriteriaDto dto = new CriteriaDto();

        dto.setPage(dto.getPage());
        dto.setSize(dto.getSize());
        dto.setSorting(dto.getSorting());

        return dto;
    }

    public static void setDefaultPageValIfEmpty(CriteriaDto crDto) {
        if (crDto.getPage() == null) {
            crDto.setPage(1);
        }

        if (crDto.getSize() == null) {
            crDto.setSize(20);
        }
    }
}
