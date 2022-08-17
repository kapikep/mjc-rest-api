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
import com.epam.esm.service.util.ServiceUtil;
import com.epam.esm.service.util.TagUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.service.impl.GiftCertificateServiceImpl.RESOURCE_NOT_FOUND;
import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.TagUtil.tagDtoToEntityConverting;
import static com.epam.esm.service.util.TagUtil.tagEntityListToDtoConverting;
import static com.epam.esm.service.util.TagUtil.tagEntityToDtoConverting;
import static com.epam.esm.service.util.TagUtil.updateFieldsInDtoFromEntity;
import static com.epam.esm.service.util.TagUtil.updateFieldsInEntityFromDto;

/**
 * Service for tags
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<TagDto> readTagsPaginated(CriteriaDto crDto) throws ServiceException, ValidateException {
        List<TagDto> tags;
        CriteriaUtil.setDefaultPageValIfEmpty(crDto);
        TagUtil.sortingValidation(crDto);
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
     * Validates id and reads tag by id from repository
     *
     * @return tag from repository
     */
    @Override
    public TagDto readTagById(long id) throws ServiceException {
        TagDto tag;
        try {
            tag = tagEntityToDtoConverting(tagRepository.readById(id));
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e, "error.resource.not.found", id);
        }
        return tag;
    }

    /**
     * Validates name and reads tag by name from repository
     *
     * @return tag from repository
     */
    @Override
    public TagDto readTagByName(String name) throws ServiceException {
        TagDto tag;
        try {
            tag = tagEntityToDtoConverting(tagRepository.readByName(name));
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e, "tag.name.not.exist", name);
        }
        return tag;
    }

    @Override
    public List<TagDto> getMostWidelyTag() {
        return tagEntityListToDtoConverting(tagRepository.findMostWidelyTag());
    }

    /**
     * Validates tags. Update tags id in list from db, if tag not exist - creates new, and
     * write in list
     *
     * @param tags list for get ids
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
     * Validates tag fields and creates tag in repository
     */
    @Override
    public void createTag(TagDto tag) throws ServiceException {
        tag.setId(0);
        try {
            TagEntity entity = tagDtoToEntityConverting(tag);
            tagRepository.create(entity);
            TagUtil.updateFieldsInDtoFromEntity(entity, tag);
        } catch (RepositoryException | DataAccessException e) {
            String mes = e.getMessage();
            if (mes != null && mes.contains("name_UNIQUE")) {
                throw new ServiceException(e.getMessage(), e, "tag.existing", tag.getName());
            }
            throw new ServiceException(mes, e);
        }
    }

    /**
     * Validates tag fields and updates tag in repository
     */
    @Override
    @Transactional
    public void updateTag(TagDto tagDto) throws ServiceException {
        try {
            TagEntity entityFromDb = tagRepository.readById(tagDto.getId());
            updateFieldsInEntityFromDto(tagDto, entityFromDb);
            //TODO close tr
        } catch (RepositoryException | DataAccessException e) {
            String mes = e.getMessage();
            if (mes != null && mes.contains("Incorrect result size: expected 1, actual 0")) {
                throw new ServiceException(e.getMessage(), e, RESOURCE_NOT_FOUND, tagDto.getId());
            }
            if (mes != null && mes.contains("name_UNIQUE")) {
                throw new ServiceException(e.getMessage(), e, "tag.existing", tagDto.getName());
            }
            if (mes != null && mes.contains("0 updated rows")) {
                throw new ServiceException(e.getMessage(), e, "incorrect.search.id", tagDto.getId());
            }
            throw new ServiceException(mes, e);
        }
    }

    /**
     * Validates id and deletes tag by id in repository
     */
    @Override
    public void deleteTagById(long id) throws ServiceException {
        try {
            tagRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, "error.resource.not.found", id);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException(e.getMessage(), e, "error.tag.linked");
        }
    }
}
