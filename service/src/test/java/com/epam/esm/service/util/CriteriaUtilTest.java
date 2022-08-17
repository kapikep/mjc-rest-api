package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.service.exception.ValidateException;
import org.junit.jupiter.api.Test;

import static com.epam.esm.repository.constant.SearchParam.TAG_SORT_PARAM;
import static com.epam.esm.service.constant.ExceptionMes.CRITERIA_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.CRITERIA_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.service.dtoFactory.DtoFactory.getCriteriaDto1;
import static com.epam.esm.service.dtoFactory.DtoFactory.getCriteriaDto2;
import static com.epam.esm.service.dtoFactory.DtoFactory.getNewCriteriaDto2;
import static com.epam.esm.service.entityFactory.EntityFactory.getCriteriaEntity1;
import static com.epam.esm.service.entityFactory.EntityFactory.getCriteriaEntity2;
import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.CriteriaUtil.criteriaEntityToDtoConverting;
import static com.epam.esm.service.util.CriteriaUtil.setDefaultPageValIfEmpty;
import static com.epam.esm.service.util.CriteriaUtil.sortingValidation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CriteriaUtilTest {
    @Test
    void criteriaDtoToEntityConvertingTest() {
        CriteriaEntity actualEntity = criteriaDtoToEntityConverting(getCriteriaDto1());

        assertEquals(getCriteriaEntity1(), actualEntity);
    }

    @Test
    void criteriaDtoToEntityConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> criteriaDtoToEntityConverting(null));

        assertEquals(CRITERIA_DTO_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void criteriaEntityToDtoConvertingTest() {
        CriteriaDto actualDto = criteriaEntityToDtoConverting(getCriteriaEntity2());

        assertEquals(getCriteriaDto2(), actualDto);
    }

    @Test
    void criteriaEntityToDtoConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> criteriaEntityToDtoConverting(null));

        assertEquals(CRITERIA_ENTITY_MUST_NOT_BE_NULL, e.getMessage());
    }


    @Test
    void setDefaultPageValIfEmptyTest() {
        CriteriaDto expectedDto = new CriteriaDto(1, 20, null);
        expectedDto.setTotalSize(0L);
        CriteriaDto cr = getNewCriteriaDto2();
        setDefaultPageValIfEmpty(cr);

        assertEquals(getCriteriaDto2(), cr);

        cr = new CriteriaDto();
        setDefaultPageValIfEmpty(cr);

        assertEquals(expectedDto, cr);
    }

    @Test
    void setDefaultPageValIfEmptyNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> setDefaultPageValIfEmpty(null));

        assertEquals(CRITERIA_DTO_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void sortingValidationTest() throws ValidateException {
        CriteriaDto cr = new CriteriaDto();

        cr.setSorting("id");
        sortingValidation(cr, TAG_SORT_PARAM);

        cr.setSorting("-id");
        sortingValidation(cr, TAG_SORT_PARAM);

        cr.setSorting(" id");
        sortingValidation(cr, TAG_SORT_PARAM);

        cr.setSorting("duration");
        assertThrows(ValidateException.class,
                () -> sortingValidation(cr, TAG_SORT_PARAM));
    }
}