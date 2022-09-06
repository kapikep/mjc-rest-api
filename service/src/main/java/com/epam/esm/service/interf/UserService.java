package com.epam.esm.service.interf;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Service interface for users
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
@Validated
public interface UserService {
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
    List<UserDto> readUsersPaginated(@Valid CriteriaDto crDto) throws ValidateException, ServiceException;

    /**
     * Validate id. It must be positive.
     * Read UserEntity by id from repository and convert it to UserDto.
     *
     * @param id unique identifier of the user to search for.
     * @return UserDto by id.
     * @throws ServiceException if UserDto with this id does not exist.
     */
    UserDto readUserById(@Positive long id) throws ServiceException;
}
