package com.epam.esm.repository.interf;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;

public interface GiftCertificateRepository {
    List<GiftCertificate> readAll() throws RepositoryException;

    List<GiftCertificate> readGiftCertificate(int id) throws RepositoryException;

    void createGiftCertificate(GiftCertificate giftCertificate)  throws RepositoryException;

    void updateGiftCertificate(GiftCertificate giftCertificate)  throws RepositoryException;

    void deleteGiftCertificate(int id)  throws RepositoryException;
}
