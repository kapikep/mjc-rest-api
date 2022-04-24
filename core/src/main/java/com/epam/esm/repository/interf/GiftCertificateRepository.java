package com.epam.esm.repository.interf;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;

public interface GiftCertificateRepository {
    List<GiftCertificate> readAll() throws RepositoryException;

    GiftCertificate readGiftCertificate(int id) throws RepositoryException;
}
