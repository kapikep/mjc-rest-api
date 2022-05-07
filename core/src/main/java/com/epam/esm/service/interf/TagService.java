package com.epam.esm.service.interf;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;

import java.util.List;

public interface TagService {
    List<Tag> readAllTags() throws ServiceException, ValidateException;

    Tag readTag(String id) throws ServiceException, ValidateException;

    Tag readTag(int id) throws ServiceException, ValidateException;

    Tag readTagByName(String name) throws ServiceException, ValidateException;

    int getTagIdOrCreateNewTag(Tag tag) throws ServiceException, ValidateException;

    void createTag(Tag tag) throws ServiceException, ValidateException;

    void updateTag(Tag tag) throws ServiceException, ValidateException;

    void deleteTag(String id) throws ServiceException, ValidateException;

}
