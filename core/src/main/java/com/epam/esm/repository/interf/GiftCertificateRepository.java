package com.epam.esm.repository.interf;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;
import java.util.Map;
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
    List<GiftCertificate> readAllGiftCertificates() throws RepositoryException;

    /**
     * Reads gift certificate by id from database
     *
     * @return list with gift GiftCertificateEntity
     */
    List<GiftCertificate> readGiftCertificate(int id) throws RepositoryException;

    /**
     * Finds gift certificates by criteria map and sorted by sorting param from database
     *
     * @param criteriaMap search parameters
     * @param sorting sorting for the result
     * @return list with GiftCertificateEntity
     */
    List<GiftCertificate> findGiftCertificate(Map<String, String> criteriaMap, String sorting) throws RepositoryException;


    /**
     * Creates new gift certificate in database
     *
     * @param giftCertificate gift certificate entity to create in db, excluding tag field
     * @param tags related with gift certificate tags for recording in many to many table
     * @return id for created gift certificate
     */
    int createGiftCertificate(GiftCertificate giftCertificate, List<Tag> tags) throws RepositoryException;

    /**
     * Overwrites tags id for gift certificates id in many to many table
     *
     * @param giftId gift certificates id
     * @param tags tags id
     */
    void insertIntoGiftCertificateHasTag(Number giftId, List<Tag> tags);

    /**
     * Updates new gift certificate
     *
     * @param giftCertificate gift certificate entity to update in db, excluding tag field
     * @param tags related with gift certificate tags for recording in many to many table
     */
    void updateGiftCertificate(GiftCertificate giftCertificate, List<Tag> tags)  throws RepositoryException;

    /**
     * Delete gift certificate by id from database
     *
     */
    void deleteGiftCertificate(int id)  throws RepositoryException;
}
