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

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Tag> readAllTags() throws ServiceException, ValidateException {
        List<Tag> tags;
        try{
            tags = repository.readAllTags();
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return tags;
    }

    @Override
    public Tag readTag(String idStr) throws ServiceException, ValidateException {
        int id = ServiceUtil.parseInt(idStr);
        Tag tag;
        try{
            tag = repository.readTag(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, "resource.not.found", id);
        }
        return tag;
    }

    @Override
    public Tag readTag(int id) throws ServiceException, ValidateException {
        if(!TagValidator.idValidation(id)){
            throw new ValidateException("incorrect.id", id);
        }
        Tag tag;
        try{
            tag = repository.readTag(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return tag;
    }

    @Override
    public Tag readTagByName(String name) throws ServiceException, ValidateException {
        if(!TagValidator.nameValidation(name)) {
            throw new ValidateException("incorrect.name");
        }
        Tag tag;
        try{
            tag = repository.readTagByName(name);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return tag;
    }

    @Override
    public int getTagIdOrCreateNewTag(Tag tag) throws ServiceException, ValidateException{
        TagValidator.tagFieldValidator(tag);
        int id;
        try{
            id = repository.readTagByName(tag.getName()).getId();
        } catch (RepositoryException e){
            try {
                repository.createTag(tag);
                id = repository.readTagByName(tag.getName()).getId();
            } catch (RepositoryException ex) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
        return id;
    }

    @Override
    public void createTag(Tag tag) throws ServiceException, ValidateException {
        TagValidator.tagFieldValidator(tag);
        try {
            repository.createTag(tag);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateTag(Tag tag) throws ServiceException, ValidateException {
        TagValidator.tagFieldValidator(tag);
        try {
            if(tag.getId() == 0){
                throw new ValidateException("incorrect.id", tag.getId());
            }
            repository.updateTag(tag);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteTag(String idStr) throws ServiceException, ValidateException {
        int id = ServiceUtil.parseInt(idStr);
        try {
            repository.deleteTag(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, "resource.not.found", id);
        }
    }
}
