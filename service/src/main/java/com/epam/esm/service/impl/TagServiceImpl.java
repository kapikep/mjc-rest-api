package com.epam.esm.service.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.TagService;
import com.epam.esm.service.util.CriteriaUtil;
import com.epam.esm.service.util.TagUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.repository.constant.ExceptionMes.INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0;
import static com.epam.esm.repository.constant.ExceptionMes.ZERO_UPDATED_ROWS;
import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.TagUtil.tagDtoToEntityConverting;
import static com.epam.esm.service.util.TagUtil.tagEntityListToDtoConverting;
import static com.epam.esm.service.util.TagUtil.tagEntityToDtoConverting;
import static com.epam.esm.service.util.TagUtil.updateFieldsInDtoFromEntity;
import static com.epam.esm.service.util.TagUtil.updateFieldsInEntityFromDto;

/**
 * Service for tags.
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private static final String NAME_UNIQUE = "name_UNIQUE";
    private static final String RESOURCE_NOT_FOUND = "error.resource.not.found";
    private static final String TAG_EXISTING = "tag.existing";
    private static final String TAG_NAME_NOT_EXIST = "tag.name.not.exist";
    private static final String ERROR_RESOURCE_NOT_FOUND = "error.resource.not.found";
    private static final String INCORRECT_SEARCH_ID = "incorrect.search.id";
    private static final String ERROR_TAG_LINKED = "error.tag.linked";

    private final TagRepository tagRepository;

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
    @Override
    public List<TagDto> readTagsPaginated(CriteriaDto crDto) throws ServiceException, ValidateException {
        List<TagDto> tags;
        CriteriaUtil.setDefaultPageValIfEmpty(crDto);
        TagUtil.tagSortingValidation(crDto);
        CriteriaEntity cr = criteriaDtoToEntityConverting(crDto);
        try {
            tags = tagEntityListToDtoConverting(tagRepository.readPaginated(cr));
            crDto.setTotalSize(cr.getTotalSize());
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return tags;
    }

    /**
     * Validate id.
     * Read TagEntity by id from repository and convert it to TagDto.
     *
     * @param id unique identifier of the tag to search for.
     * @return TagDto by id.
     * @throws ServiceException if TagEntity with id does not exist.
     *                          If any RepositoryException or DataAccessException has occurred.
     */
    @Override
    public TagDto readTagById(long id) throws ServiceException {
        TagDto tag;
        try {
            tag = tagEntityToDtoConverting(tagRepository.readById(id));
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, ERROR_RESOURCE_NOT_FOUND, id);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return tag;
    }

    /**
     * Validate name.
     * Read TagEntity by name from repository and convert it to TagDto.
     *
     * @param name TagEntity name. Must be not empty. Min size 2, max 20.
     * @return TagDto.
     */
    @Override
    public TagDto readTagByName(String name) throws ServiceException {
        TagDto tag;
        try {
            tag = tagEntityToDtoConverting(tagRepository.readByName(name));
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e, TAG_NAME_NOT_EXIST, name);
        }
        return tag;
    }

    /**
     * Find the most widely used tag of a user with the highest cost of all orders.
     * If there are several users or tags match to the condition,
     * all matching tags are returned.
     *
     * @return List with TagEntities.
     */
    @Override
    public List<TagDto> getMostWidelyTag() {
        return tagEntityListToDtoConverting(tagRepository.findMostWidelyTag());
    }

    /**
     * Validate TagDto fields OnCreate group.
     * If tag doesn't have an id, a search by name from repository will occur,
     * if tag does not exist - create new. id field will update from repository.
     *
     * @param tags TagDto list.
     */
    @Override
    public List<TagDto> setIdOrCreateTags(List<TagDto> tags) throws ServiceException {
        if (tags != null) {
            for (TagDto tag : tags) {
                if (tag.getId() <= 0) {
                    try {
                        updateFieldsInDtoFromEntity(tagRepository.readByName(tag.getName()), tag);
                    } catch (RepositoryException | DataAccessException e) {
                        try {
                            updateFieldsInDtoFromEntity(tagRepository.merge(tagDtoToEntityConverting(tag)), tag);
                        } catch (RepositoryException ex) {
                            throw new ServiceException(e.getMessage(), e);
                        }
                    }
                }
            }
        }
        return tags;
    }

    /**
     * Validate TagDto fields OnCreate group.
     * Convert to TagEntity and create new tag in repository.
     *
     * @param tagDto TagDto to save.
     * @throws ServiceException if tag name is not unique.
     *                          If any RepositoryException or DataAccessException has occurred.
     */
    @Override
    public void createTag(TagDto tagDto) throws ServiceException {
        tagDto.setId(0);
        try {
            TagEntity entity = tagDtoToEntityConverting(tagDto);
            tagRepository.create(entity);
            TagUtil.updateFieldsInDtoFromEntity(entity, tagDto);
        } catch (RepositoryException | DataAccessException e) {
            String mes = e.getMessage();
            if (mes != null && mes.contains(NAME_UNIQUE)) {
                throw new ServiceException(e.getMessage(), e, TAG_EXISTING, tagDto.getName());
            }
            throw new ServiceException(mes, e);
        }
    }

    /**
     * Validate TagDto fields OnUpdate group.
     * Convert to TagEntity and update tag in repository.
     *
     * @param tagDto TagDto to update.
     * @throws ServiceException if tag name is not unique.
     *                          If any RepositoryException or DataAccessException has occurred.
     */
    @Override
    public void updateTag(TagDto tagDto) throws ServiceException {
        try {
            TagEntity entityFromDb = tagRepository.readById(tagDto.getId());
            updateFieldsInEntityFromDto(tagDto, entityFromDb);
            tagRepository.merge(entityFromDb);
        } catch (RepositoryException | DataAccessException e) {
            String mes = e.getMessage();
            if (mes != null && mes.contains(INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0)) {
                throw new ServiceException(e.getMessage(), e, RESOURCE_NOT_FOUND, tagDto.getId());
            }
            if (mes != null && mes.contains(NAME_UNIQUE)) {
                throw new ServiceException(e.getMessage(), e, TAG_EXISTING, tagDto.getName());
            }
            if (mes != null && mes.contains(ZERO_UPDATED_ROWS)) {
                throw new ServiceException(e.getMessage(), e, INCORRECT_SEARCH_ID, tagDto.getId());
            }
            throw new ServiceException(mes, e);
        }
    }

    /**
     * Validate id.
     * Delete tag by id in repository.
     *
     * @param id unique identifier of the tag to delete from repository.
     * @throws ServiceException if tag with this id does not exist in repository.
     *                          If tag is linked to any gift certificate.
     */
    @Override
    public void deleteTagById(long id) throws ServiceException {
        try {
            tagRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, ERROR_RESOURCE_NOT_FOUND, id);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException(e.getMessage(), e, ERROR_TAG_LINKED);
        }
    }
}
