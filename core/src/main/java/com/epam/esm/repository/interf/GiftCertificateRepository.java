package com.epam.esm.repository.interf;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;
import java.util.Map;

public interface GiftCertificateRepository {
    List<GiftCertificate> readAllGiftCertificates() throws RepositoryException;

    List<GiftCertificate> readGiftCertificate(int id) throws RepositoryException;

    List<GiftCertificate> findGiftCertificate(Map<String, String> criteriaMap, String sorting) throws RepositoryException;

    void createGiftCertificate(GiftCertificate giftCertificate, List<Tag> tags) throws RepositoryException;

    void updateGiftCertificate(GiftCertificate giftCertificate, List<Tag> tags)  throws RepositoryException;

    void deleteGiftCertificate(int id)  throws RepositoryException;
}
