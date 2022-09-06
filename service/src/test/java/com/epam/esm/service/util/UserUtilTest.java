package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.service.exception.ValidateException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.epam.esm.service.constant.ExceptionMes.USER_DTO_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.USER_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.USER_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.USER_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.service.dtoFactory.UserDtoFactory.getNewUserDtoId3;
import static com.epam.esm.service.dtoFactory.UserDtoFactory.getUserDtoId1;
import static com.epam.esm.service.dtoFactory.UserDtoFactory.getUserDtoId4;
import static com.epam.esm.service.dtoFactory.UserDtoFactory.getUserDtoList;
import static com.epam.esm.service.entityFactory.UserEntityFactory.getNewUserEntityId1;
import static com.epam.esm.service.entityFactory.UserEntityFactory.getUserEntityId1;
import static com.epam.esm.service.entityFactory.UserEntityFactory.getUserEntityId3;
import static com.epam.esm.service.entityFactory.UserEntityFactory.getUserEntityId4;
import static com.epam.esm.service.entityFactory.UserEntityFactory.getUserEntityList;
import static com.epam.esm.service.util.UserUtil.userSortingValidation;
import static com.epam.esm.service.util.UserUtil.updateFieldsInDtoFromEntity;
import static com.epam.esm.service.util.UserUtil.userDtoListToEntityConverting;
import static com.epam.esm.service.util.UserUtil.userDtoToEntityConverting;
import static com.epam.esm.service.util.UserUtil.userEntityListToDtoConverting;
import static com.epam.esm.service.util.UserUtil.userEntityToDtoConverting;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserUtilTest {

    @Test
    void userEntityListToDtoConvertingTest() {
        List<UserDto> actualDto = userEntityListToDtoConverting(getUserEntityList());

        assertEquals(getUserDtoList(), actualDto);
    }

    @Test
    void userEntityListToDtoConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> userEntityListToDtoConverting(null));

        assertEquals(USER_ENTITY_LIST_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void userDtoListToEntityConvertingTest() {
        List<UserEntity> actualDto = userDtoListToEntityConverting(getUserDtoList());

        assertEquals(5, actualDto.size());
    }

    @Test
    void userDtoListToEntityConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> userDtoListToEntityConverting(null));

        assertEquals(USER_DTO_LIST_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void userDtoToEntityConvertingTest() {
        UserEntity actualEntity = userDtoToEntityConverting(getNewUserDtoId3());
        UserEntity expectedEntity = getUserEntityId3();
        expectedEntity.setLogin(null);
        expectedEntity.setPassword(null);

        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    void userDtoToEntityConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> userDtoToEntityConverting(null));

        assertEquals(USER_DTO_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void userEntityToDtoConvertingTest() {
        UserDto actualDto = userEntityToDtoConverting(getUserEntityId4());

        assertEquals(getUserDtoId4(), actualDto);
    }

    @Test
    void userEntityToDtoConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> userEntityToDtoConverting(null));

        assertEquals(USER_ENTITY_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void updateFieldsInDtoFromEntityTest() {
        UserEntity entity = getNewUserEntityId1();
        UserDto dto = new UserDto();

        updateFieldsInDtoFromEntity(entity, dto);

        assertEquals(getUserDtoId1(), dto);
        assertEquals(getUserEntityId1(), entity);
    }

    @Test
    void sortingValidationTest() throws ValidateException {
        CriteriaDto cr = new CriteriaDto();
        cr.setSorting("id");
        userSortingValidation(cr);

        cr.setSorting("-id");
        userSortingValidation(cr);

        cr.setSorting("criteria");
        assertThrows(ValidateException.class,
                () -> userSortingValidation(cr));
    }
}