package com.epam.esm.repository.interf;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepository {
    List<GiftCertificate> readAll();
}
