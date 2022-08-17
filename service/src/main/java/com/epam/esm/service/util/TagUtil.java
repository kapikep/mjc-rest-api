package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.constant.SearchParam.TAG_SORT_PARAM;
import static com.epam.esm.service.constant.ExceptionMes.TAG_DTO_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.TAG_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.TAG_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.TAG_ENTITY_MUST_NOT_BE_NULL;
import static org.springframework.util.Assert.notNull;

/**
 * Utils for tag
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class TagUtil {
    public static List<TagDto> tagEntityListToDtoConverting(List<TagEntity> entities) {
        notNull(entities, TAG_ENTITY_LIST_MUST_NOT_BE_NULL);

        List<TagDto> dtoList = new ArrayList<>();
        for (TagEntity entity : entities) {
            dtoList.add(tagEntityToDtoConverting(entity));
        }
        return dtoList;
    }

    public static List<TagEntity> tagDtoListToEntityConverting(List<TagDto> dtoList) {
        notNull(dtoList, TAG_DTO_LIST_MUST_NOT_BE_NULL);

        List<TagEntity> entities = new ArrayList<>();
        for (TagDto dto : dtoList) {
            entities.add(tagDtoToEntityConverting(dto));
        }
        return entities;
    }
    
    public static TagEntity tagDtoToEntityConverting(TagDto dto) {
        notNull(dto, TAG_DTO_MUST_NOT_BE_NULL);

        TagEntity entity = new TagEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }
    
    public static TagDto tagEntityToDtoConverting(TagEntity entity) {
        TagDto dto = new TagDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    public static void updateFieldsInDtoFromEntity(TagEntity entity, TagDto dto) {
        notNull(dto, TAG_DTO_MUST_NOT_BE_NULL);
        notNull(entity, TAG_ENTITY_MUST_NOT_BE_NULL);

        dto.setId(entity.getId());
        dto.setName(entity.getName());
    }

    public static void updateFieldsInEntityFromDto(TagDto dto, TagEntity entity) {
        notNull(dto, TAG_DTO_MUST_NOT_BE_NULL);
        notNull(entity, TAG_ENTITY_MUST_NOT_BE_NULL);

        entity.setId(dto.getId());
        entity.setName(dto.getName());
    }

    public static void sortingValidation(CriteriaDto crDto) throws ValidateException {
        CriteriaUtil.sortingValidation(crDto, TAG_SORT_PARAM);
    }
}
