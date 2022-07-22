package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.constant.SearchParam.*;

public class UserUtil {
    public static List<UserDto> userEntityListToDtoConverting(List<UserEntity> entities) throws ValidateException {
        if(entities == null){
            throw new ValidateException("User entities list is null");
        }
        List<UserDto> dtoList = new ArrayList<>();
        for (UserEntity entity : entities) {
            dtoList.add(userEntityToDtoTransfer(entity));
        }
        return dtoList;
    }

    public static List<UserEntity> userDtoListToEntityConverting(List<UserDto> dtoList) throws ValidateException {
        if(dtoList == null){
            throw new ValidateException("User dto list is null");
        }
        List<UserEntity> entities = new ArrayList<>();
        for (UserDto dto : dtoList) {
            entities.add(userDtoToEntityTransfer(dto));
        }
        return entities;
    }

    public static UserEntity userDtoToEntityTransfer(UserDto dto) throws ValidateException {
        if(dto == null){
            throw new ValidateException("User is null");
        }
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setSecondName(dto.getSecondName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }

    public static UserDto userEntityToDtoTransfer(UserEntity entity) throws ValidateException {
        UserDto dto = new UserDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    public static void updateFieldsInDtoFromEntity(UserEntity entity, UserDto dto) throws ValidateException {
        if(dto == null){
            throw new ValidateException("UserDto is null");
        }
        if(entity == null){
            throw new ValidateException("UserEntity is null");
        }
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setSecondName(entity.getSecondName());
        dto.setPhoneNumber(entity.getPhoneNumber());
    }

    public static void sortingValidation(CriteriaDto crDto) throws ValidateException {
        CriteriaUtil.sortingValidation(crDto, USER_SORT_PARAM);
    }
}
