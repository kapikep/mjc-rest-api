package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.service.exception.ValidateException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.epam.esm.service.constant.ExceptionMes.TAG_DTO_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.TAG_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.TAG_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.TAG_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getTagDtoId1;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getTagDtoId2;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getTagDtoId3;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getTagDtoList;
import static com.epam.esm.service.entityFactory.TagEntityFactory.getTagEntityId1;
import static com.epam.esm.service.entityFactory.TagEntityFactory.getTagEntityId2;
import static com.epam.esm.service.entityFactory.TagEntityFactory.getTagEntityId3;
import static com.epam.esm.service.entityFactory.TagEntityFactory.getTagEntityList;
import static com.epam.esm.service.util.TagUtil.sortingValidation;
import static com.epam.esm.service.util.TagUtil.tagDtoListToEntityConverting;
import static com.epam.esm.service.util.TagUtil.tagDtoToEntityConverting;
import static com.epam.esm.service.util.TagUtil.tagEntityListToDtoConverting;
import static com.epam.esm.service.util.TagUtil.tagEntityToDtoConverting;
import static com.epam.esm.service.util.TagUtil.updateFieldsInDtoFromEntity;
import static com.epam.esm.service.util.TagUtil.updateFieldsInEntityFromDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TagUtilTest {
    @Test
    void tagEntityListToDtoConvertingTest() {
        List<TagDto> actualDto = tagEntityListToDtoConverting(getTagEntityList());

        assertEquals(getTagDtoList(), actualDto);
    }

    @Test
    void tagEntityListToDtoConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> tagEntityListToDtoConverting(null));

        assertEquals(TAG_ENTITY_LIST_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void tagDtoListToEntityConvertingTest() {
        List<TagEntity> actualEntity = tagDtoListToEntityConverting(getTagDtoList());

        assertEquals(getTagEntityList(), actualEntity);
    }

    @Test
    void tagDtoListToEntityConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> tagDtoListToEntityConverting(null));

        assertEquals(TAG_DTO_LIST_MUST_NOT_BE_NULL, e.getMessage());
    }


    @Test
    void tagDtoToEntityConvertingTest() {
        TagEntity actualEntity = tagDtoToEntityConverting(getTagDtoId1());

        assertEquals(getTagEntityId1(), actualEntity);
    }

    @Test
    void tagDtoToEntityConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> tagDtoToEntityConverting(null));

        assertEquals(TAG_DTO_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void tagEntityToDtoConvertingTest() {
        TagDto actualTagDto = tagEntityToDtoConverting(getTagEntityId2());

        assertEquals(getTagDtoId2(), actualTagDto);
    }

    @Test
    void tagEntityToDtoConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> tagEntityToDtoConverting(null));

        assertEquals(TAG_ENTITY_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void updateFieldsInDtoFromEntityTest() {
        TagDto dto = new TagDto();
        updateFieldsInDtoFromEntity(getTagEntityId3(), dto);

        assertEquals(getTagDtoId3(), dto);
    }

    @Test
    void updateFieldsInDtoFromEntityNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> updateFieldsInDtoFromEntity(null, new TagDto()));

        assertEquals(TAG_ENTITY_MUST_NOT_BE_NULL, e.getMessage());

        e = assertThrows(IllegalArgumentException.class,
                () -> updateFieldsInDtoFromEntity(new TagEntity(), null));

        assertEquals(TAG_DTO_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void updateFieldsInEntityFromDtoTest() {
        TagEntity actualEntity = new TagEntity();
        TagEntity expectedEntity = getTagEntityId3();
        updateFieldsInEntityFromDto(getTagDtoId3(), actualEntity);

        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    void updateFieldsInEntityFromDtoNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> updateFieldsInEntityFromDto(null, new TagEntity()));

        assertEquals(TAG_DTO_MUST_NOT_BE_NULL, e.getMessage());

        e = assertThrows(IllegalArgumentException.class,
                () -> updateFieldsInEntityFromDto(new TagDto(), null));

        assertEquals(TAG_ENTITY_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void sortingValidationTest() throws ValidateException {
        CriteriaDto cr = new CriteriaDto();
        cr.setSorting("id");
        sortingValidation(cr);

        cr.setSorting("-id");
        sortingValidation(cr);

        cr.setSorting("duration");
        assertThrows(ValidateException.class,
                () -> sortingValidation(cr));
    }
}