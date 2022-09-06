package com.epam.esm.repository.interf;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;

/**
 * Repository interface for UserEntity
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public interface UserRepository {
    /**
     * Read UserEntities paginated from db
     *
     * @param cr CriteriaEntity with params for pagination
     * @return List with UserEntities
     * @throws RepositoryException      if page or size null or less 1.
     *                                  If the page is larger than the total size of the pages.
     * @exception IllegalArgumentException if the CriteriaEntity or criteria totalSize field is null.
     */
    List<UserEntity> readPaginated(CriteriaEntity cr) throws RepositoryException;

    /**
     * Read UserEntity by id from database.
     *
     * @param id unique identifier of the UserEntity to search.
     * @return UserEntity by id.
     * @throws RepositoryException if there is no UserEntity with this id in db.
     */
    UserEntity readById(long id) throws RepositoryException;

    /**
     * Find user with the highest cost of all orders.
     * If there are several users match to the condition, all matching users are returned.
     *
     * @return List with UserEntities.
     */
    List<UserEntity> findUserWithHighestCostOfAllOrders();
}
