package com.epam.esm.repository.interf;

import com.epam.esm.entity.CriteriaEntity;
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
    List<TagEntity> readAll() throws RepositoryException;

    List<TagEntity> readAllPaginated(CriteriaEntity criteria) throws RepositoryException;

    /**
     * Reads tag by id from database
     *
     * @return tagEntity  from database
     */

    TagEntity readById(long id) throws RepositoryException;
    /**
     * Reads tag by name from database
     *
     * @return tagEntity from database
     */
    TagEntity readByName(String name) throws RepositoryException;

    /**
     * Creates tag in database
     *
     * @param tag tagEntity to create in db
     */
    void create(TagEntity tag) throws RepositoryException;

    /**
     * Updates tag in database
     *
     * @param tag tag to create in db
     */
    TagEntity merge(TagEntity tag) throws RepositoryException;

    /**
     * Deletes tag in database
     *
     * @param id  id to create in db
     */
    void deleteById(long id) throws RepositoryException;

    List<TagEntity> findMostWidelyTag();
}
