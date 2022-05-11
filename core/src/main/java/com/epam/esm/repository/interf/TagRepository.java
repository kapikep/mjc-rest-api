package com.epam.esm.repository.interf;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;
/**
 * Repository for tags
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public interface TagRepository {

    /**
     * Reads all tags from database
     *
     * @return list with all tagsEntities from database
     */
    List<Tag> readAllTags() throws RepositoryException;

    /**
     * Reads tag by id from database
     *
     * @return tagEntity  from database
     */
    Tag readTag(int id) throws RepositoryException;

    /**
     * Reads tag by name from database
     *
     * @return tagEntity from database
     */
    Tag readTagByName(String name) throws RepositoryException;

    /**
     * Creates tag in database
     *
     * @param tag tagEntity to create in db
     * @return id for created tag
     */
    int createTag(Tag tag) throws RepositoryException;

    /**
     * Updates tag in database
     *
     * @param tag tag to create in db
     */
    void updateTag(Tag tag) throws RepositoryException;

    /**
     * Deletes tag in database
     *
     * @param id  id to create in db
     */
    void deleteTag(int id) throws RepositoryException;
}
