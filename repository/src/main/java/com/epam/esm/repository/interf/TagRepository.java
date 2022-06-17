package com.epam.esm.repository.interf;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.exception.RepositoryException;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Repository for tags
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Repository
public interface TagRepository {

    /**
     * Reads all tags from database
     *
     * @return list with all tagsEntities from database
     */
    List<TagEntity> readAllTags() throws RepositoryException;

    /**
     * Reads tag by id from database
     *
     * @return tagEntity  from database
     */
    TagEntity readTag(int id) throws RepositoryException;

    /**
     * Reads tag by name from database
     *
     * @return tagEntity from database
     */
    TagEntity readTagByName(String name) throws RepositoryException;

    /**
     * Creates tag in database
     *
     * @param tag tagEntity to create in db
     * @return id for created tag
     */
    int createTag(TagEntity tag) throws RepositoryException;

    /**
     * Updates tag in database
     *
     * @param tag tag to create in db
     */
    void updateTag(TagEntity tag) throws RepositoryException;

    /**
     * Deletes tag in database
     *
     * @param id  id to create in db
     */
    void deleteTag(int id) throws RepositoryException;
}
