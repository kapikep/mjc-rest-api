package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.constant.ExceptionMes.TAG_DTO_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.TAG_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.TAG_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.TAG_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.SearchParam.TAG_SORT_PARAM;
import static org.springframework.util.Assert.notNull;

/**
 * Utils for tag
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
public class TagUtil {
    /**
     * Converting TagEntity list to TagDto list
     *
     * @param entities TagEntity list
     * @return TagDto list
     */
    public static List<TagDto> tagEntityListToDtoConverting(List<TagEntity> entities) {
        notNull(entities, TAG_ENTITY_LIST_MUST_NOT_BE_NULL);

        List<TagDto> dtoList = new ArrayList<>();
        for (TagEntity entity : entities) {
            dtoList.add(tagEntityToDtoConverting(entity));
        }
        return dtoList;
    }

    /**
     * Converting  TagDto list to TagEntity list
     *
     * @param dtoList TagDto list
     * @return TagEntity list
     */
    public static List<TagEntity> tagDtoListToEntityConverting(List<TagDto> dtoList) {
        notNull(dtoList, TAG_DTO_LIST_MUST_NOT_BE_NULL);

        List<TagEntity> entities = new ArrayList<>();
        for (TagDto dto : dtoList) {
            entities.add(tagDtoToEntityConverting(dto));
        }
        return entities;
    }

    /**
     * Converting TagDto to TagEntity
     *
     * @param dto TagDto
     * @return new TagEntity with fields values from TagDto
     */
    public static TagEntity tagDtoToEntityConverting(TagDto dto) {
        notNull(dto, TAG_DTO_MUST_NOT_BE_NULL);

        TagEntity entity = new TagEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    /**
     * Converting TagEntity to TagDto
     *
     * @param entity TagEntity
     * @return new TagDto with fields values from TagEntity
     */
    public static TagDto tagEntityToDtoConverting(TagEntity entity) {
        TagDto dto = new TagDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    /**
     * Update fields in TagDto from TagEntity
     *
     * @param entity TagEntity
     * @param dto    TagDto
     */
    public static void updateFieldsInDtoFromEntity(TagEntity entity, TagDto dto) {
        notNull(dto, TAG_DTO_MUST_NOT_BE_NULL);
        notNull(entity, TAG_ENTITY_MUST_NOT_BE_NULL);

        dto.setId(entity.getId());
        dto.setName(entity.getName());
    }

    /**
     * Update fields in TagEntity from TagDto
     *
     * @param entity TagEntity
     * @param dto    TagDto
     */
    public static void updateFieldsInEntityFromDto(TagDto dto, TagEntity entity) {
        notNull(dto, TAG_DTO_MUST_NOT_BE_NULL);
        notNull(entity, TAG_ENTITY_MUST_NOT_BE_NULL);

        entity.setId(dto.getId());
        entity.setName(dto.getName());
    }

    /**
     * Validate CriteriaDto sorting field for TqgEntity
     *
     * @param crDto CriteriaDto
     * @throws ValidateException if sorting field does not match TAG_SORT_PARAM
     */
    public static void tagSortingValidation(CriteriaDto crDto) throws ValidateException {
        CriteriaUtil.sortingValidation(crDto, TAG_SORT_PARAM);
    }
}
