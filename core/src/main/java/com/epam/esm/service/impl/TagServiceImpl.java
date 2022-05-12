package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.TagService;
import com.epam.esm.service.utils.ServiceUtil;
import com.epam.esm.service.validator.TagValidator;
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
    public List<Tag> readAllTags() throws ServiceException{
        List<Tag> tags;
        try {
            tags = repository.readAllTags();
        } catch (RepositoryException e) {
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
    public Tag readTag(String idStr) throws ServiceException, ValidateException {
        int id = ServiceUtil.parseInt(idStr);
        Tag tag;
        try {
            tag = repository.readTag(id);
        } catch (RepositoryException e) {
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
    public Tag readTag(int id) throws ServiceException, ValidateException {
        if (!TagValidator.idValidation(id)) {
            throw new ValidateException("incorrect.id", id);
        }
        Tag tag;
        try {
            tag = repository.readTag(id);
        } catch (RepositoryException e) {
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
    public Tag readTagByName(String name) throws ServiceException, ValidateException {
        if (!TagValidator.nameValidation(name)) {
            throw new ValidateException("incorrect.name");
        }
        Tag tag;
        try {
            tag = repository.readTagByName(name);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
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
    public void getIdOrCreateTagsInList(List<Tag> tags) throws ServiceException, ValidateException {
        if(tags != null) {
            for (Tag tag : tags) {
                TagValidator.tagFieldValidator(tag);
                int id;
                try {
                    id = repository.readTagByName(tag.getName()).getId();
                } catch (RepositoryException e) {
                    try {
                        id = repository.createTag(tag);
                    } catch (RepositoryException ex) {
                        throw new ServiceException(e.getMessage(), e);
                    }
                }
                tag.setId(id);
            }
        }
    }

    /**
     * Validates tag fields and creates tag in repository
     *
     * @return id for created tag
     */
    @Override
    public int createTag(Tag tag) throws ServiceException, ValidateException {
        int id;
        TagValidator.tagFieldValidator(tag);
        try {
            id = repository.createTag(tag);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return id;
    }

    /**
     * Validates tag fields and updates tag in repository
     */
    @Override
    public void updateTag(Tag tag) throws ServiceException, ValidateException {
        TagValidator.tagFieldValidator(tag);
        try {
            if (tag.getId() == 0) {
                throw new ValidateException("incorrect.id", tag.getId());
            }
            repository.updateTag(tag);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Validates id and deletes tag by id in repository
     */
    @Override
    public void deleteTag(String idStr) throws ServiceException, ValidateException {
        int id = ServiceUtil.parseInt(idStr);
        try {
            repository.deleteTag(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, "error.resource.not.found", id);
        }
    }
}
