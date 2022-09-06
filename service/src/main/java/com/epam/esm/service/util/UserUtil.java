package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.constant.ExceptionMes.USER_DTO_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.USER_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.USER_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.USER_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.SearchParam.USER_SORT_PARAM;
import static org.springframework.util.Assert.notNull;

/**
 * Utils for user
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class UserUtil {
    /**
     * Converting UserEntity list to UserDto list
     *
     * @param entities UserEntity list
     * @return UserDto list
     */
    public static List<UserDto> userEntityListToDtoConverting(List<UserEntity> entities) {
        notNull(entities, USER_ENTITY_LIST_MUST_NOT_BE_NULL);

        List<UserDto> dtoList = new ArrayList<>();
        for (UserEntity entity : entities) {
            dtoList.add(userEntityToDtoConverting(entity));
        }
        return dtoList;
    }

    /**
     * Converting  UserDto list to UserEntity list
     *
     * @param dtoList UserDto list
     * @return UserEntity list
     */
    public static List<UserEntity> userDtoListToEntityConverting(List<UserDto> dtoList) {
        notNull(dtoList, USER_DTO_LIST_MUST_NOT_BE_NULL);

        List<UserEntity> entities = new ArrayList<>();
        for (UserDto dto : dtoList) {
            entities.add(userDtoToEntityConverting(dto));
        }
        return entities;
    }

    /**
     * Converting UserDto to UserEntity
     *
     * @param dto UserDto
     * @return new UserEntity with fields values from UserDto
     */
    public static UserEntity userDtoToEntityConverting(UserDto dto) {
        notNull(dto, USER_DTO_MUST_NOT_BE_NULL);

        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setSecondName(dto.getSecondName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }

    /**
     * Converting UserEntity to UserDto
     *
     * @param entity UserEntity
     * @return new UserDto with fields values from UserEntity
     */
    public static UserDto userEntityToDtoConverting(UserEntity entity) {
        UserDto dto = new UserDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    /**
     * Update fields in UserDto from UserEntity
     *
     * @param entity UserEntity
     * @param dto    UserDto
     */
    public static void updateFieldsInDtoFromEntity(UserEntity entity, UserDto dto) {
        notNull(dto, USER_DTO_MUST_NOT_BE_NULL);
        notNull(entity, USER_ENTITY_MUST_NOT_BE_NULL);

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setSecondName(entity.getSecondName());
        dto.setPhoneNumber(entity.getPhoneNumber());
    }

    /**
     * Validate CriteriaDto sorting field for UserEntity
     *
     * @param crDto CriteriaDto
     * @throws ValidateException if sorting field does not match USER_SORT_PARAM
     */
    public static void userSortingValidation(CriteriaDto crDto) throws ValidateException {
        CriteriaUtil.sortingValidation(crDto, USER_SORT_PARAM);
    }
}
