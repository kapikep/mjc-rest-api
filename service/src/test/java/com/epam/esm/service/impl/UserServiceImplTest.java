package com.epam.esm.service.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.UserRepository;
import com.epam.esm.service.dtoFactory.DtoFactory;
import com.epam.esm.service.entityFactory.EntityFactory;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.util.CriteriaUtil;
import com.epam.esm.service.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.epam.esm.service.dtoFactory.UserDtoFactory.getNewUserDtoId1;
import static com.epam.esm.service.dtoFactory.UserDtoFactory.getNewUserDtoList;
import static com.epam.esm.service.dtoFactory.UserDtoFactory.getUserDtoId1;
import static com.epam.esm.service.entityFactory.UserEntityFactory.getNewUserEntityId1;
import static com.epam.esm.service.entityFactory.UserEntityFactory.getNewUserEntityList;
import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.CriteriaUtil.setDefaultPageValIfEmpty;
import static com.epam.esm.service.util.UserUtil.userEntityListToDtoConverting;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    public static final String MESSAGE = "message";
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void readUsersPaginatedTest() throws ServiceException, RepositoryException, ValidateException {
        CriteriaEntity crEntity = EntityFactory.getNewCriteriaEntityWithDefaultVal();
        CriteriaDto crDto = DtoFactory.getNewCriteriaDtoWithDefaultVal();
        List<UserEntity> userEntityList = getNewUserEntityList();
        List<UserDto> userDtoList = getNewUserDtoList();
        List<UserDto> actualDtoList;
        long totalSize = userEntityList.size();

        try (MockedStatic<CriteriaUtil> crUtil = Mockito.mockStatic(CriteriaUtil.class);
             MockedStatic<UserUtil> userUtil = Mockito.mockStatic(UserUtil.class)) {

            when(userRepository.readPaginated(crEntity)).thenReturn(userEntityList);
            userUtil.when(() -> userEntityListToDtoConverting(userEntityList))
                    .thenReturn(userDtoList);
            crUtil.when(() -> criteriaDtoToEntityConverting(crDto)).thenReturn(crEntity);

            crEntity.setTotalSize(totalSize);
            actualDtoList = userService.readUsersPaginated(crDto);

            verify(userRepository).readPaginated(crEntity);
            crUtil.verify(() -> criteriaDtoToEntityConverting(crDto));
            crUtil.verify(() -> setDefaultPageValIfEmpty(crDto));
            userUtil.verify(() -> UserUtil.sortingValidation(crDto));
            assertEquals(userDtoList, actualDtoList);
            assertEquals(totalSize, crDto.getTotalSize());
        }
    }

    @Test
    void readUsersPaginatedWithExceptionTest() throws RepositoryException {
        CriteriaEntity crEntity = EntityFactory.getNewCriteriaEntityWithDefaultVal();
        CriteriaDto crDto = DtoFactory.getNewCriteriaDtoWithDefaultVal();
        List<UserEntity> userEntityList = getNewUserEntityList();
        List<UserDto> userDtoList = getNewUserDtoList();

        try (MockedStatic<CriteriaUtil> crUtil = Mockito.mockStatic(CriteriaUtil.class);
             MockedStatic<UserUtil> userUtil = Mockito.mockStatic(UserUtil.class)) {

            when(userRepository.readPaginated(crEntity)).thenThrow(new RepositoryException(MESSAGE));
            userUtil.when(() -> userEntityListToDtoConverting(userEntityList))
                    .thenReturn(userDtoList);
            crUtil.when(() -> criteriaDtoToEntityConverting(crDto)).thenReturn(crEntity);

            ServiceException e = assertThrows(ServiceException.class,
                    () -> userService.readUsersPaginated(crDto));

            assertEquals(MESSAGE, e.getMessage());
        }
    }

    @Test
    void readUserByIdTest() throws RepositoryException, ServiceException {
        UserEntity userEntity = getNewUserEntityId1();
        UserDto userDto = getNewUserDtoId1();

        when(userRepository.readById(anyLong()))
                .thenReturn(userEntity).thenThrow(new RepositoryException());

        try (MockedStatic<UserUtil> util = Mockito.mockStatic(UserUtil.class)) {
            util.when(() -> UserUtil.userEntityToDtoConverting(userEntity))
                    .thenReturn(userDto);
            UserDto actualDto = userService.readUserById(1);

            assertEquals(getUserDtoId1(), actualDto);
        }
        assertThrows(ServiceException.class, () -> userService.readUserById(222));
    }
}