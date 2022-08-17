package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.List;

import static com.epam.esm.service.constant.Constant.MINUS;
import static com.epam.esm.service.constant.Constant.PLUS;
import static com.epam.esm.service.constant.Constant.SPACE;
import static com.epam.esm.service.constant.ExceptionMes.CRITERIA_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.CRITERIA_ENTITY_MUST_NOT_BE_NULL;
import static org.springframework.util.Assert.notNull;

public class CriteriaUtil {
    public static CriteriaEntity criteriaDtoToEntityConverting(CriteriaDto dto) {
        notNull(dto, CRITERIA_DTO_MUST_NOT_BE_NULL);
        CriteriaEntity entity = new CriteriaEntity();
        entity.setPage(dto.getPage());
        entity.setSize(dto.getSize());
        entity.setSorting(dto.getSorting());
        entity.setTotalSize(dto.getTotalSize());
        entity.setSearchParam(dto.getSearchParam());

        return entity;
    }

    public static CriteriaDto criteriaEntityToDtoConverting(CriteriaEntity entity) {
        notNull(entity, CRITERIA_ENTITY_MUST_NOT_BE_NULL);
        CriteriaDto dto = new CriteriaDto();
        dto.setPage(entity.getPage());
        dto.setSize(entity.getSize());
        dto.setSorting(entity.getSorting());
        dto.setTotalSize(entity.getTotalSize());
        dto.setSearchParam(entity.getSearchParam());

        return dto;
    }

    public static void setDefaultPageValIfEmpty(CriteriaDto crDto) {
        notNull(crDto, CRITERIA_DTO_MUST_NOT_BE_NULL);

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

    public static void sortingValidation(CriteriaDto crDto, List<String> sortParam) throws ValidateException {
        String sorting = crDto.getSorting();

        if (sorting != null) {
            if (sorting.startsWith(MINUS) || sorting.startsWith(PLUS) || sorting.startsWith(SPACE)) {
                sorting = sorting.substring(1);
            }
            if (!sortParam.contains(sorting)) {
                throw new ValidateException("incorrect.param.sorting", sortParam);
            }
        }
    }
}
