package com.epam.esm.repository.interf;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;

/**
 * Repository interface for OrderForGiftCertificateEntity
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public interface OrderForGiftCertificateRepository {
    /**
     * Read OrderForGiftCertificateEntities paginated from db.
     *
     * @param cr CriteriaEntity with params for pagination.
     * @return OrderForGiftCertificateEntity list.
     * @throws RepositoryException      if page or size null or less 1.
     *                                  If the page is larger than the total size of the pages.
     * @throws IllegalArgumentException if the CriteriaEntity or criteria totalSize field is null.
     */
    List<OrderForGiftCertificateEntity> readPaginated(CriteriaEntity cr) throws RepositoryException;

    /**
     * Read OrderForGiftCertificateEntities by userId paginated from db.
     *
     * @param userId user unique identifier for search.
     * @param cr     CriteriaEntity with params for pagination.
     * @return List with OrderForGiftCertificateEntities.
     * @throws RepositoryException      if page or size null or less 1.
     *                                  If the page is larger than the total size of the pages.
     * @throws IllegalArgumentException if the CriteriaEntity or criteria totalSize field is null.
     */
    List<OrderForGiftCertificateEntity> readUserOrdersPaginated(long userId, CriteriaEntity cr) throws RepositoryException;

    /**
     * Read OrderForGiftCertificateEntity by id from database.
     *
     * @param id unique identifier of the OrderForGiftCertificateEntity to search.
     * @return OrderForGiftCertificateEntity by id.
     * @throws RepositoryException if OrderForGiftCertificateEntity with id does not exist.
     */
    OrderForGiftCertificateEntity readById(long id) throws RepositoryException;

    /**
     * Create new OrderForGiftCertificateEntity in database.
     *
     * @param order OrderForGiftCertificateEntity to create in db.
     * @throws IllegalArgumentException if instance is not an entity.
     */
    void create(OrderForGiftCertificateEntity order);

    /**
     * Merge OrderForGiftCertificateEntity in database.
     *
     * @param order OrderForGiftCertificateEntity to merge in db.
     * @return the managed instance that the state was merged to.
     * @throws IllegalArgumentException if instance is not an entity or is a removed entity.
     */
    OrderForGiftCertificateEntity merge(OrderForGiftCertificateEntity order);

    /**
     * Delete OrderForGiftCertificateEntity by id in db
     *
     * @param id unique identifier to delete from db.
     * @throws IllegalArgumentException if the instance is not an entity or is a detached entity.
     */
    void deleteById(long id) throws RepositoryException;
}
