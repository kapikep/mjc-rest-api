package com.epam.esm.service.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.UserRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.CriteriaUtil.setDefaultPageValIfEmpty;
import static com.epam.esm.service.util.UserUtil.userSortingValidation;
import static com.epam.esm.service.util.UserUtil.userEntityListToDtoConverting;
import static com.epam.esm.service.util.UserUtil.userEntityToDtoConverting;

/**
 * Service for users.
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public static final String RESOURCE_NOT_FOUND = "error.resource.not.found";

    private final UserRepository userRepository;

    /**
     * Validate CriteriaDto
     * Read UserEntities paginated from repository and converting it to UserDto list.
     * Set default value in CriteriaDto.
     *
     * @param crDto CriteriaEntity with params for pagination.
     * @return UserDto list.
     * @throws ServiceException  if page or size is null or less 1.
     *                           If any RepositoryException or DataAccessException has occurred.
     * @throws ValidateException if sorting field does not match USER_SORT_PARAM.
     */
    @Override
    public List<UserDto> readUsersPaginated(CriteriaDto crDto) throws ValidateException, ServiceException {
        setDefaultPageValIfEmpty(crDto);
        userSortingValidation(crDto);
        CriteriaEntity cr = criteriaDtoToEntityConverting(crDto);
        List<UserDto> users;
        try {
            users = userEntityListToDtoConverting(userRepository.readPaginated(cr));
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        crDto.setTotalSize(cr.getTotalSize());
        return users;
    }

    /**
     * Validate id. It must be positive.
     * Read UserEntity by id from repository and convert it to UserDto.
     *
     * @param id unique identifier of the user to search for.
     * @return UserDto by id.
     * @throws ServiceException if UserDto with this id does not exist.
     */
    @Override
    public UserDto readUserById(long id) throws ServiceException {
        UserDto dto;
        try {
            dto = userEntityToDtoConverting(userRepository.readById(id));
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, RESOURCE_NOT_FOUND, id);
        }
        return dto;
    }
}
