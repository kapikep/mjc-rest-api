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
            throw new ServiceException(e.getMessage(), e);
        }
        return tag;
    }

    @Override
    public Tag readTag(int id) throws ServiceException, ValidateException {
        if(!TagValidator.idValidation(id)){
            throw new ValidateException("Wrong tag id");
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
            throw new ValidateException("Wrong tag name");
        }
        Tag tag;
        try{
            tag = repository.readTag(name);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return tag;
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
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
