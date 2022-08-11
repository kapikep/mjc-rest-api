package com.epam.esm.repository.interf;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;

/**
 * Repository for gift certificates
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public interface GiftCertificateRepository {
    /**
     * Reads all gift certificates from database
     *
     * @return list with all GiftCertificateEntity
     */
    List<GiftCertificateEntity> readAll() throws RepositoryException;

    List<GiftCertificateEntity> readAllPaginated(CriteriaEntity cr) throws RepositoryException;

    /**
     * Reads gift certificate by id from database
     *
     * @return list with gift GiftCertificateEntity
     */
    GiftCertificateEntity readById(long id) throws RepositoryException;

    /**
     * Finds gift certificates by criteria map and sorted by sorting param from database
     *
     * @param cr@return list with GiftCertificateEntity
     */
    List<GiftCertificateEntity> findByCriteria(CriteriaEntity cr) throws RepositoryException;

    /**
     * Creates new gift certificate in database
     *
     * @param giftCertificate gift certificate entity to create in db, excluding tag field
     */
    void create(GiftCertificateEntity giftCertificate) throws RepositoryException;

    /**
     * Updates new gift certificate
     *
     * @param giftCertificate gift certificate entity to update in db, excluding tag field
     */
    GiftCertificateEntity merge(GiftCertificateEntity giftCertificate)  throws RepositoryException;

    /**
     * Delete gift certificate by id from database
     *
     */
    void deleteById(long id)  throws RepositoryException;
}
