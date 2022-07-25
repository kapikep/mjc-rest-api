package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.constant.SearchParam.GIFT_CERTIFICATE_SORT_PARAM;
import static com.epam.esm.repository.constant.SearchParam.TAG_SORT_PARAM;

/**
 * Utils for tag
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class TagUtil {
    public static List<TagDto> tagEntityListToDtoConverting(List<TagEntity> entities) throws ValidateException {
        if(entities == null){
            throw new ValidateException("TagItemEntity list is null");
        }
        List<TagDto> dtoList = new ArrayList<>();
        for (TagEntity entity : entities) {
            dtoList.add(tagEntityToDtoTransfer(entity));
        }
        return dtoList;
    }

    public static List<TagEntity> tagDtoListToEntityConverting(List<TagDto> dtoList) throws ValidateException {
        if(dtoList == null){
            throw new ValidateException("TagItemDto list is null");
        }
        List<TagEntity> entities = new ArrayList<>();
        for (TagDto dto : dtoList) {
            entities.add(tagDtoToEntityTransfer(dto));
        }
        return entities;
    }
    
    public static TagEntity tagDtoToEntityTransfer(TagDto dto) throws ValidateException {
        if(dto == null){
            throw new ValidateException("TagItemDto is null");
        }
        TagEntity entity = new TagEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }
    
    public static TagDto tagEntityToDtoTransfer(TagEntity entity) throws ValidateException {
        TagDto dto = new TagDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    public static void updateFieldsInDtoFromEntity(TagEntity entity, TagDto dto) throws ValidateException {
        if(dto == null){
            throw new ValidateException("TagItemDto is null");
        }
        if(entity == null){
            throw new ValidateException("TagEntity is null");
        }
        dto.setId(entity.getId());
        dto.setName(entity.getName());
    }

    public static void sortingValidation(CriteriaDto crDto) throws ValidateException {
        CriteriaUtil.sortingValidation(crDto, TAG_SORT_PARAM);
    }
}
