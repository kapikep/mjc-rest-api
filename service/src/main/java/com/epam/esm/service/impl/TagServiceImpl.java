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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.service.util.TagUtil.*;

/**
 * Service for tags
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Service
public class TagServiceImpl implements TagService {
    private final TagRepository repository;

    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TagDto> readPage(CriteriaDto crDto) throws ServiceException, ValidateException {
        CriteriaUtil.setDefaultPageValIfEmpty(crDto);
        TagUtil.sortingValidation(crDto);
        CriteriaEntity cr = CriteriaUtil.criteriaDtoToEntityConverting(crDto);
        List<TagDto> tags;
        try {
            tags = TagUtil.tagEntityListToDtoConverting(repository.readPage(cr));
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
    public TagDto readOne(String idStr) throws ServiceException, ValidateException {
        long id = ServiceUtil.parseLong(idStr);
        TagDto tag;
        try {
            tag = tagEntityToDtoTransfer(repository.readOne(id));
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
    public TagDto readOne(long id) throws ServiceException, ValidateException {
        TagDto tag;
        try {
            tag = tagEntityToDtoTransfer(repository.readOne(id));
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
    public TagDto readByName(String name) throws ServiceException, ValidateException {
        TagDto tag;
        try {
            tag = tagEntityToDtoTransfer(repository.readByName(name));
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e, "tag.name.not.exist", name);
        }
        return tag;
    }

    /**
     * Validates tags. Update tags id in list from db, if tag not exist - creates new, and
     * write id in list
     *
     * @param tags list for get ids
     */
    @Override
    public void getIdOrCreateTags(List<TagDto> tags) throws ServiceException, ValidateException {
        if (tags != null) {
            for (TagDto tag : tags) {
                if (tag.getId() <= 0) {
                    try {
                        updateFieldsInDtoFromEntity(repository
                                .readByName(tag.getName()), tag);
                    } catch (RepositoryException | DataAccessException e) {
                        try {
                            updateFieldsInDtoFromEntity(repository
                                    .merge(tagDtoToEntityTransfer(tag)), tag);
                        } catch (RepositoryException ex) {
                            throw new ServiceException(e.getMessage(), e);
                        }
                    }
                }
            }
        }
    }

    /**
     * Validates tag fields and creates tag in repository
     *
     * @return id for created tag
     */
    @Override
    public void create(TagDto tag) throws ServiceException, ValidateException {
        tag.setId(0);
        try {
            TagEntity entity = tagDtoToEntityTransfer(tag);
            repository.create(entity);
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
    public void update(TagDto tag) throws ServiceException, ValidateException {
        try {
            if (tag.getId() == 0) {
                throw new ValidateException("incorrect.search.id", tag.getId());
            }
            TagEntity entity = repository.merge(tagDtoToEntityTransfer(tag));
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
    public void delete(long id) throws ServiceException, ValidateException {
        try {
            repository.deleteById(id);
        } catch (RepositoryException e ) {
            throw new ServiceException(e.getMessage(), e, "error.resource.not.found", id);
        } catch (DataIntegrityViolationException e){
            throw new ServiceException(e.getMessage(), e, "error.tag.linked");
        }
    }
}
