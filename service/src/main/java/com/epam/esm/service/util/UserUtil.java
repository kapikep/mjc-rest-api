package com.epam.esm.service.util;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.constant.SearchParam.TAG_SORT_PARAM;

public class UserUtil {
    public static List<UserDto> userEntityListToDtoConverting(List<UserEntity> entities){
        List<UserDto> dtoList = new ArrayList<>();
        entities.forEach(entity -> dtoList.add(userEntityToDtoTransfer(entity)));
        return dtoList;
    }

    public static List<UserEntity> userDtoListToEntityConverting(List<UserDto> dtoList){
        List<UserEntity> entities = new ArrayList<>();
        dtoList.forEach(dto -> entities.add(userDtoToEntityTransfer(dto)));
        return entities;
    }

    public static UserEntity userDtoToEntityTransfer(UserDto dto){
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setSecondName(dto.getSecondName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }

    public static UserDto userEntityToDtoTransfer(UserEntity entity){
        UserDto dto = new UserDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    public static void updateFieldsInDtoFromEntity(UserEntity entity, UserDto dto){
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setSecondName(entity.getSecondName());
        dto.setPhoneNumber(entity.getPhoneNumber());
    }

    public static void sortingValidation(CriteriaEntity crDto) throws ValidateException {
        String sorting = crDto.getSorting();

        if (sorting != null) {
            if (sorting.startsWith("-") || sorting.startsWith("+") || sorting.startsWith(" ")) {
                sorting = sorting.substring(1);
            }

            if (!TAG_SORT_PARAM.contains(sorting)) {
                throw new ValidateException("incorrect.param.sorting", TAG_SORT_PARAM);
            }
        }
    }
}
