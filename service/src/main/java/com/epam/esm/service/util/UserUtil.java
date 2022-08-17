package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.constant.SearchParam.USER_SORT_PARAM;
import static com.epam.esm.service.constant.ExceptionMes.USER_DTO_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.USER_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.USER_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.USER_ENTITY_MUST_NOT_BE_NULL;
import static org.springframework.util.Assert.notNull;

public class UserUtil {
    public static List<UserDto> userEntityListToDtoConverting(List<UserEntity> entities) {
        notNull(entities, USER_ENTITY_LIST_MUST_NOT_BE_NULL);

        List<UserDto> dtoList = new ArrayList<>();
        for (UserEntity entity : entities) {
            dtoList.add(userEntityToDtoConverting(entity));
        }
        return dtoList;
    }

    public static List<UserEntity> userDtoListToEntityConverting(List<UserDto> dtoList) {
        notNull(dtoList, USER_DTO_LIST_MUST_NOT_BE_NULL);

        List<UserEntity> entities = new ArrayList<>();
        for (UserDto dto : dtoList) {
            entities.add(userDtoToEntityConverting(dto));
        }
        return entities;
    }

    public static UserEntity userDtoToEntityConverting(UserDto dto) {
        notNull(dto, USER_DTO_MUST_NOT_BE_NULL);

        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setSecondName(dto.getSecondName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }

    public static UserDto userEntityToDtoConverting(UserEntity entity) {
        UserDto dto = new UserDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    public static void updateFieldsInDtoFromEntity(UserEntity entity, UserDto dto) {
        notNull(dto, USER_DTO_MUST_NOT_BE_NULL);
        notNull(entity, USER_ENTITY_MUST_NOT_BE_NULL);

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setSecondName(entity.getSecondName());
        dto.setPhoneNumber(entity.getPhoneNumber());
    }

    public static void sortingValidation(CriteriaDto crDto) throws ValidateException {
        CriteriaUtil.sortingValidation(crDto, USER_SORT_PARAM);
    }
}
