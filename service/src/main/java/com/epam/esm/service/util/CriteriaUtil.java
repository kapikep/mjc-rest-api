package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.service.exception.ValidateException;

import static com.epam.esm.repository.constant.SearchParam.GIFT_CERTIFICATE_SORT_PARAM;

public class CriteriaUtil {

    public static CriteriaEntity criteriaDtoToEntityConverting(CriteriaDto dto) throws ValidateException {
        CriteriaEntity entity = new CriteriaEntity();

        entity.setPage(dto.getPage());
        entity.setSize(dto.getSize());
        entity.setSorting(dto.getSorting());
        entity.setSearchParam(dto.getSearchParam());

        return entity;
    }

    public static CriteriaDto criteriaEntityToDtoConverting(CriteriaEntity entity) throws ValidateException {
        CriteriaDto dto = new CriteriaDto();

        dto.setPage(entity.getPage());
        dto.setSize(entity.getSize());
        dto.setSorting(entity.getSorting());
        dto.setSearchParam(entity.getSearchParam());

        return dto;
    }

    public static void setDefaultPageValIfEmpty(CriteriaDto crDto) {
        if (crDto.getPage() == null) {
            crDto.setPage(1);
        }

        if (crDto.getSize() == null) {
            crDto.setSize(20);
        }

        if (crDto.getTotalSize() == null) {
            crDto.setTotalSize(0L);
        }
    }
}
