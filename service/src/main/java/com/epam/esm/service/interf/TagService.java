package com.epam.esm.service.interf;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.validator.groups.OnCreate;
import com.epam.esm.service.validator.groups.OnUpdate;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Service interface for tags
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
@Validated
public interface TagService {
    /**
     * Validate CriteriaDto.
     * Set default value in CriteriaDto.
     * Read TagEntities from repository and converting it to TagDto list.
     *
     * @param crDto CriteriaEntity with params for pagination.
     * @return TagDto list
     * @throws ServiceException  if page or size is null or less 1.
     *                           If the page is larger than the total size of the pages.
     *                           If any RepositoryException or DataAccessException has occurred.
     * @throws ValidateException if sorting field does not match TAG_SORT_PARAM.
     */
    List<TagDto> readTagsPaginated(@Valid CriteriaDto crDto) throws ServiceException, ValidateException;

    /**
     * Validate id.
     * Read TagEntity by id from repository and convert it to TagDto.
     *
     * @param id unique identifier of the tag to search for.
     * @return TagDto by id.
     * @throws ServiceException if TagEntity with id does not exist.
     *                          If any RepositoryException or DataAccessException has occurred.
     */
    TagDto readTagById(@Positive long id) throws ServiceException;

    /**
     * Validate name.
     * Read TagEntity by name from repository and convert it to TagDto.
     *
     * @param name TagEntity name. Must be not empty. Min size 2, max 20.
     * @return TagDto.
     */
    TagDto readTagByName(@NotEmpty @Size(min = 2, max = 20) String name) throws ServiceException, ValidateException;

    /**
     * Find the most widely used tag of a user with the highest cost of all orders.
     * If there are several users or tags match to the condition,
     * all matching tags are returned.
     *
     * @return List with TagEntities.
     */
    List<TagDto> getMostWidelyTag() throws ServiceException;

    /**
     * Validate TagDto fields OnCreate group.
     * If tag doesn't have an id, a search by name from repository will occur,
     * if tag does not exist - create new. id field will update from repository.
     *
     * @param tags TagDto list.
     */
    @Validated(OnCreate.class)
    List<TagDto> setIdOrCreateTags(@Valid List<TagDto> tags) throws ServiceException, ValidateException;

    /**
     * Validate TagDto fields OnCreate group.
     * Convert to TagEntity and create new tag in repository.
     *
     * @param tagDto TagDto to save.
     * @throws ServiceException if tag name is not unique.
     *                          If any RepositoryException or DataAccessException has occurred.
     */
    @Validated(OnCreate.class)
    void createTag(@Valid TagDto tagDto) throws ServiceException;

    /**
     * Validate TagDto fields OnUpdate group.
     * Convert to TagEntity and update tag in repository.
     *
     * @param tagDto TagDto to update.
     * @throws ServiceException if tag name is not unique.
     *                          If any RepositoryException or DataAccessException has occurred.
     */
    @Validated(OnUpdate.class)
    void updateTag(@Valid TagDto tagDto) throws ServiceException;

    /**
     * Validate id.
     * Delete tag by id in repository.
     *
     * @param id unique identifier of the tag to delete from repository.
     * @throws ServiceException if tag with this id does not exist in repository.
     *                          If tag is linked to any gift certificate.
     */
    void deleteTagById(@Positive long id) throws ServiceException;
}
