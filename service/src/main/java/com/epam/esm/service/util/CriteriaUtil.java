package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.List;

import static com.epam.esm.repository.constant.SearchParam.GIFT_CERTIFICATE_SORT_PARAM;
import static com.epam.esm.repository.constant.SearchParam.TAG_SORT_PARAM;

public class CriteriaUtil {

    public static CriteriaEntity criteriaDtoToEntityConverting(CriteriaDto dto) throws ValidateException {
        if(dto == null){
            throw new ValidateException("CriteriaDto is null");
        }
        CriteriaEntity entity = new CriteriaEntity();

        entity.setPage(dto.getPage());
        entity.setSize(dto.getSize());
        entity.setSorting(dto.getSorting());
        entity.setSearchParam(dto.getSearchParam());

        return entity;
    }

    public static CriteriaDto criteriaEntityToDtoConverting(CriteriaEntity entity) throws ValidateException {
        if(entity == null){
            throw new ValidateException("CriteriaEntity is null");
        }
        CriteriaDto dto = new CriteriaDto();

        dto.setPage(entity.getPage());
        dto.setSize(entity.getSize());
        dto.setSorting(entity.getSorting());
        dto.setSearchParam(entity.getSearchParam());

        return dto;
    }

    public static void setDefaultPageValIfEmpty(CriteriaDto crDto) throws ValidateException {
        if(crDto == null){
            throw new ValidateException("CriteriaDto is null");
        }
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
            if (sorting.startsWith("-") || sorting.startsWith("+") || sorting.startsWith(" ")) {
                sorting = sorting.substring(1);
            }

            if (!sortParam.contains(sorting)) {
                throw new ValidateException("incorrect.param.sorting", sortParam);
            }
        }
    }
}
