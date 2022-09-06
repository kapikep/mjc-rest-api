package com.epam.esm.repository.interf;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.exception.RepositoryException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for TagEntity.
 *
 * @author Artsemi Kapitula.
 * @version 2.0
 */
@Repository
public interface TagRepository {
    /**
     * Read all TagEntities from database.
     *
     * @return list with all TagEntities.
     */
    List<TagEntity> readAll();

    /**
     * Read TagEntities paginated from db.
     *
     * @param cr CriteriaEntity with params for pagination.
     * @return List with TagEntities.
     * @throws RepositoryException if page or size is null or less 1.
     *                             If the page is larger than the total size of the pages.
     */
    List<TagEntity> readPaginated(CriteriaEntity cr) throws RepositoryException;

    /**
     * Read TagEntity by id from database.
     *
     * @param id unique identifier of the TagEntity to search for.
     * @return TagEntity by id.
     * @throws RepositoryException if TagEntity with id does not exist.
     */
    TagEntity readById(long id) throws RepositoryException;

    /**
     * Find TagEntity by name from database.
     *
     * @param name TagEntity name.
     * @return TagEntity.
     */
    TagEntity readByName(String name) throws RepositoryException;

    /**
     * Find the most widely used tag of a user with the highest cost of all orders.
     * If there are several users or tags match to the condition, all matching tags are returned.
     *
     * @return List with TagEntities.
     */
    List<TagEntity> findMostWidelyTag();

    /**
     * Create new TagEntity in database.
     *
     * @param tag TagEntity to create in db.
     * @throws IllegalArgumentException if instance is not an entity.
     */
    void create(TagEntity tag) throws RepositoryException;

    /**
     * Merge TagEntity in database.
     *
     * @param tag TagEntity to merge in db.
     * @return the managed instance that the state was merged to.
     * @throws IllegalArgumentException if instance is not an entity or is a removed entity.
     */
    TagEntity merge(TagEntity tag) throws RepositoryException;

    /**
     * Delete TagEntity by id in db.
     *
     * @param id unique identifier to delete from db.
     * @throws IllegalArgumentException if there is no entity with this id  in db.
     *                                  If tag is linked to any gift certificate
     */
    void deleteById(long id) throws RepositoryException;
}
