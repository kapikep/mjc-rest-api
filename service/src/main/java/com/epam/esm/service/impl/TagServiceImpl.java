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

import java.util.List;

import static com.epam.esm.service.util.TagUtil.tagDtoToEntityTransfer;
import static com.epam.esm.service.util.TagUtil.tagEntityListToDtoConverting;
import static com.epam.esm.service.util.TagUtil.tagEntityToDtoTransfer;
import static com.epam.esm.service.util.TagUtil.updateFieldsInDtoFromEntity;

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
    public List<TagDto> readAllTagsPaginated(CriteriaDto crDto) throws ServiceException, ValidateException {
        CriteriaUtil.setDefaultPageValIfEmpty(crDto);
        TagUtil.sortingValidation(crDto);
        CriteriaEntity cr = CriteriaUtil.criteriaDtoToEntityConverting(crDto);
        List<TagDto> tags;
        try {
            tags = TagUtil.tagEntityListToDtoConverting(tagRepository.readAllPaginated(cr));
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
    public TagDto readTagById(String idStr) throws ServiceException, ValidateException {
        long id = ServiceUtil.parseLong(idStr);
        TagDto tag;
        try {
            tag = tagEntityToDtoTransfer(tagRepository.readById(id));
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e, "error.resource.not.found", id);
        }
        return tag;
    }

    /**
     * Validates id and reads tag by id from repository
     *
     * @return tag from repository
     */
    @Override
    public TagDto readTagById(long id) throws ServiceException, ValidateException {
        TagDto tag;
        try {
            tag = tagEntityToDtoTransfer(tagRepository.readById(id));
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
    public TagDto readTagByName(String name) throws ServiceException, ValidateException {
        TagDto tag;
        try {
            tag = tagEntityToDtoTransfer(tagRepository.readByName(name));
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e, "tag.name.not.exist", name);
        }
        return tag;
    }

    @Override
    public List<TagDto> getMostWidelyTag() throws ServiceException {
        List<TagDto> tags;
        try {
            tags = tagEntityListToDtoConverting(tagRepository.findMostWidelyTag());
        } catch (ValidateException e) {
            throw new ServiceException(e);
        }
        return tags;
    }

    /**
     * Validates tags. Update tags id in list from db, if tag not exist - creates new, and
     * write in list
     *
     * @param tags list for get ids
     */
    @Override
    public List<TagDto> setIdOrCreateTags(List<TagDto> tags) throws ServiceException, ValidateException {
        if (tags != null) {
            for (TagDto tag : tags) {
                if (tag.getId() <= 0) {
                    try {
                        updateFieldsInDtoFromEntity(tagRepository.readByName(tag.getName()), tag);
                    } catch (RepositoryException | DataAccessException e) {
                        try {
                            updateFieldsInDtoFromEntity(tagRepository.merge(tagDtoToEntityTransfer(tag)), tag);
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
     *
     */
    @Override
    public void createTag(TagDto tag) throws ServiceException, ValidateException {
        tag.setId(0);
        try {
            TagEntity entity = tagDtoToEntityTransfer(tag);
            tagRepository.create(entity);
            TagUtil.updateFieldsInDtoFromEntity(entity, tag);
        } catch (RepositoryException | DataAccessException e) {
            String mes = e.getMessage();
            if (mes != null && mes.contains("[name_UNIQUE]")) {
                throw new ServiceException(e.getMessage(), e, "tag.existing", tag.getName());
            }
            throw new ServiceException(mes, e);
        }
    }

    /**
     * Validates tag fields and updates tag in repository
     */
    @Override
    public void updateTag(TagDto tag) throws ServiceException, ValidateException {
        try {
            if (tag.getId() == 0) {
                throw new ValidateException("incorrect.search.id", tag.getId());
            }
            TagEntity entity = tagRepository.merge(tagDtoToEntityTransfer(tag));
            TagUtil.updateFieldsInDtoFromEntity(entity, tag);
        } catch (RepositoryException e) {
            String mes = e.getMessage();
            if (mes != null && mes.contains("Duplicate entry")) {
                throw new ServiceException(e.getMessage(), e, "tag.existing", tag.getName());
            }
            if (mes != null && mes.contains("0 updated rows")) {
                throw new ServiceException(e.getMessage(), e, "incorrect.search.id", tag.getId());
            }
            throw new ServiceException(mes, e);
        }
    }

    /**
     * Validates id and deletes tag by id in repository
     */
    @Override
    public void deleteTagById(long id) throws ServiceException{
        try {
            tagRepository.deleteById(id);
        } catch (RepositoryException e ) {
            throw new ServiceException(e.getMessage(), e, "error.resource.not.found", id);
        } catch (DataIntegrityViolationException e){
            throw new ServiceException(e.getMessage(), e, "error.tag.linked");
        }
    }
}
