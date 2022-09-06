package com.epam.esm.repository.interf;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;

/**
 * Repository interface for GiftCertificateEntity
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
public interface GiftCertificateRepository {
    /**
     * Read all GiftCertificateEntities from db.
     *
     * @return list with all GiftCertificateEntities from db.
     */
    List<GiftCertificateEntity> readAll();

    /**
     * Read GiftCertificateEntities paginated from db
     *
     * @param cr CriteriaEntity with params for pagination
     * @return List with GiftCertificateEntities
     * @throws RepositoryException if page or size is null or less 1
     */
    List<GiftCertificateEntity> readPaginated(CriteriaEntity cr) throws RepositoryException;

    /**
     * Read GiftCertificateEntity by id from db.
     *
     * @param id unique identifier of the entity to search for.
     * @return GiftCertificateEntity by id.
     * @throws RepositoryException if GiftCertificateEntity with id does not exist.
     */
    GiftCertificateEntity readById(long id) throws RepositoryException;

    /**
     * Find GiftCertificateEntity by criteria map and sort by sorting param from database.
     *
     * @param cr CriteriaEntity with criteria map and sorting param.
     * @return list with GiftCertificateEntities.
     * @throws RepositoryException if sorting field does not match GIFT_CERTIFICATE_SORT_PARAM.
     *                             If page or size is null or less 1.
     *                             If the page is larger than the total size of the pages.
     */
    List<GiftCertificateEntity> findByCriteria(CriteriaEntity cr) throws RepositoryException;

    /**
     * Create new GiftCertificateEntity in database.
     *
     * @param giftCertificate GiftCertificateEntity to create in db.
     * @throws IllegalArgumentException if instance is not an entity.
     */
    void create(GiftCertificateEntity giftCertificate);

    /**
     * Merge GiftCertificateEntity in database.
     *
     * @param giftCertificate GiftCertificateEntity to merge in db.
     * @return the managed instance that the state was merged to.
     * @throws IllegalArgumentException if instance is not an
     *                                  entity or is a removed entity.
     */
    GiftCertificateEntity merge(GiftCertificateEntity giftCertificate);

    /**
     * Delete GiftCertificateEntity by id in db.
     *
     * @param id unique identifier of the gift certificate to delete.
     * @throws IllegalArgumentException if there is no entity with this id in db.
     */
    void deleteById(long id) throws RepositoryException;
}
