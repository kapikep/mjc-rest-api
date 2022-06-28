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
import com.epam.esm.service.utils.CriteriaUtil;
import com.epam.esm.service.utils.ServiceUtil;
import com.epam.esm.service.utils.TagUtil;
import com.epam.esm.service.validator.TagValidator;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    /**
     * Reads all tags from repository
     *
     * @return list with all tags from repository
     */
    @Override
    public List<TagDto> readAllTags() throws ServiceException{
        List<TagDto> tags;
        try {
            List<TagEntity> tagEntities = repository.readAll();
            tags = TagUtil.tagEntityListToDtoConverting(tagEntities);
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return tags;
    }

    @Override
    public List<TagDto> readPage(CriteriaDto crDto) throws ServiceException, ValidateException {
        //TODO validate cr
        CriteriaEntity cr = CriteriaUtil.criteriaDtoToEntityConverting(crDto);
        List<TagDto> tags;
        try {
            List<TagEntity> tagEntities = repository.readPage(cr);
            tags = TagUtil.tagEntityListToDtoConverting(tagEntities);
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
    public TagDto readTag(String idStr) throws ServiceException, ValidateException {
        long id = ServiceUtil.parseLong(idStr);
        TagDto tag;
        try {
            tag = TagUtil.tagEntityToDtoTransfer(repository.readOne(id));
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
    public TagDto readTag(long id) throws ServiceException, ValidateException {
        if (!TagValidator.idValidation(id)) {
            throw new ValidateException("incorrect.search.id", id);
        }
        TagDto tag;
        try {
            tag =  TagUtil.tagEntityToDtoTransfer(repository.readOne(id));
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
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
        if (!TagValidator.nameValidation(name)) {
            throw new ValidateException("incorrect.search.name", name);
        }
        TagDto tag;
        try {
            tag =  TagUtil.tagEntityToDtoTransfer(repository.readByName(name));
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
    public void getIdOrCreateTagsInList(List<TagDto> tags) throws ServiceException, ValidateException {
        if(tags != null) {
            for (TagDto tag : tags) {
                TagValidator.tagFieldValidator(tag);
                long id;
                try {
                    id = repository.readByName(tag.getName()).getId();
                    tag.setId(id);
                } catch (RepositoryException | DataAccessException e) {
                    try {
                        tag.setId(0);
//                        id = repository.create(TagUtil.tagDtoToEntityTransfer(tag));
                        repository.create(TagUtil.tagDtoToEntityTransfer(tag));
                    } catch (RepositoryException | DataAccessException ex) {
                        throw new ServiceException(e.getMessage(), e);
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
    public void createTag(TagDto tag) throws ServiceException, ValidateException {
        tag.setId(0);
        TagValidator.tagFieldValidator(tag);
        try {
            TagEntity entity = TagUtil.tagDtoToEntityTransfer(tag);
            repository.create(entity);
            TagUtil.updateFieldsInDtoFromEntity(entity, tag);
        } catch (RepositoryException | DataAccessException e) {
            String mes = e.getMessage();
            if (mes != null && mes.contains("[name_UNIQUE]")){
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
        TagValidator.tagFieldValidator(tag);
        try {
            if (tag.getId() == 0) {
                throw new ValidateException("incorrect.search.id", tag.getId());
            }
            TagEntity entity = repository.update(TagUtil.tagDtoToEntityTransfer(tag));
            TagUtil.updateFieldsInDtoFromEntity(entity, tag);
        } catch (RepositoryException e) {
            String mes = e.getMessage();
            if (mes != null && mes.contains("Duplicate entry")){
                throw new ServiceException(e.getMessage(), e, "tag.existing", tag.getName());
            }
            if (mes != null && mes.contains("0 updated rows")){
                throw new ServiceException(e.getMessage(), e, "incorrect.search.id", tag.getId());
            }
            throw new ServiceException(mes, e);
        }
    }

    /**
     * Validates id and deletes tag by id in repository
     */
    @Override
    public void deleteTag(String idStr) throws ServiceException, ValidateException {
        int id = ServiceUtil.parseInt(idStr);
        try {
            repository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, "error.resource.not.found", id);
        }
    }
}
