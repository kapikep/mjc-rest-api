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
    public static List<TagDto> tagEntityListToDtoConverting(List<TagEntity> entities){
        List<TagDto> dtoList = new ArrayList<>();
        entities.forEach(entity -> dtoList.add(tagEntityToDtoTransfer(entity)));
        return dtoList;
    }

    public static List<TagEntity> tagDtoListToEntityConverting(List<TagDto> dtoList){
        List<TagEntity> entities = new ArrayList<>();
        dtoList.forEach(dto -> entities.add(tagDtoToEntityTransfer(dto)));
        return entities;
    }
    
    public static TagEntity tagDtoToEntityTransfer(TagDto dto){
        TagEntity entity = new TagEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }
    
    public static TagDto tagEntityToDtoTransfer(TagEntity entity){
        TagDto dto = new TagDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    public static void updateFieldsInDtoFromEntity(TagEntity entity, TagDto dto){
        dto.setId(entity.getId());
        dto.setName(entity.getName());
    }

    public static void sortingValidation(CriteriaDto crDto) throws ValidateException {
        CriteriaUtil.sortingValidation(crDto, TAG_SORT_PARAM);
    }
}
