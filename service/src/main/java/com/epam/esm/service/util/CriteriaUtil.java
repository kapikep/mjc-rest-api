package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.List;
import java.util.Map;

import static com.epam.esm.repository.constant.Constant.MINUS;
import static com.epam.esm.repository.constant.Constant.PLUS;
import static com.epam.esm.repository.constant.Constant.SPACE;
import static com.epam.esm.repository.constant.ExceptionMes.CRITERIA_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.CRITERIA_ENTITY_MUST_NOT_BE_NULL;
import static org.springframework.util.Assert.notNull;

/**
 * Utils for criteria
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class CriteriaUtil {
    private static final String INCORRECT_PARAM_SORTING = "incorrect.param.sorting";
    public static final String INCORRECT_PARAM_SEARCH = "incorrect.param.search";

    /**
     * Converting CriteriaDto to CriteriaEntity
     *
     * @param dto CriteriaDto
     * @return CriteriaEntity
     */
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

    /**
     * Converting CriteriaEntity to CriteriaDto
     *
     * @param entity CriteriaEntity
     * @return CriteriaDto
     */
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

    /**
     * Set page field to 1, size field to 20, totalSize field to 0 in CriteriaDto
     * if it is null
     *
     * @param crDto CriteriaDto
     */
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

    /**
     * Validate CriteriaDto sorting field for sorting parameter list
     *
     * @param crDto     CriteriaDto.
     * @param sortParam list with allowed parameters.
     * @throws ValidateException if sorting field does not match parameter list
     */
    public static void sortingValidation(CriteriaDto crDto, List<String> sortParam) throws ValidateException {
        String sorting = crDto.getSorting();

        if (sorting != null) {
            if (sorting.startsWith(MINUS) || sorting.startsWith(PLUS) || sorting.startsWith(SPACE)) {
                sorting = sorting.substring(1);
            }
            if (!sortParam.contains(sorting)) {
                throw new ValidateException(INCORRECT_PARAM_SORTING, sortParam);
            }
        }
    }

    /**
     * Validate CriteriaDto searchParam map keys for search parameter list
     *
     * @param crDto       CriteriaDto.
     * @param searchParam list with allowed parameters.
     * @throws ValidateException if sorting field does not match parameter list
     */
    public static void searchParamKeyValidation(CriteriaDto crDto, List<String> searchParam) throws ValidateException {
        Map<String, String> criteriaSearchParam = crDto.getSearchParam();

        if (!criteriaSearchParam.keySet().stream().allMatch(searchParam::contains)) {
            throw new ValidateException(INCORRECT_PARAM_SEARCH, searchParam);
        }
    }
}

