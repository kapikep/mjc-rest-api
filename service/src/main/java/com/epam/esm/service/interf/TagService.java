package com.epam.esm.service.interf;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;

import java.util.List;
/**
 * Service for tags
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public interface TagService {

    /**
     * Reads all tags from repository
     *
     * @return list with all tags from repository
     */
    List<TagDto> readAllTags() throws ServiceException, ValidateException;

    /**
     * Validates id and reads tag by id from repository
     *
     * @return tag from repository
     */
    TagDto readTag(String id) throws ServiceException, ValidateException;

    /**
     * Validates id and reads tag by id from repository
     *
     * @return tag from repository
     */
    TagDto readTag(long id) throws ServiceException, ValidateException;

    /**
     * Validates name and reads tag by name from repository
     *
     * @return tag from repository
     */
    TagDto readTagByName(String name) throws ServiceException, ValidateException;

    /**
     * Validates tags. Update tags id in list from db, if tag not exist - creates new, and
     * write id in list
     *
     * @param tags list for get ids
     */
    void getIdOrCreateTagsInList(List<TagDto> tags) throws ServiceException, ValidateException;

    /**
     * Validates tag fields and creates tag in repository
     *
     * @return id for created tag
     */
    void createTag(TagDto tag) throws ServiceException, ValidateException;

    /**
     * Updates tag in repository
     *
     * @param tag tag to update
     */
    void updateTag(TagDto tag) throws ServiceException, ValidateException;

    /**
     * Validates id and deletes tag by id in repository
     */
    void deleteTag(String id) throws ServiceException, ValidateException;
}
