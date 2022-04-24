package com.epam.esm.service.interf;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {

    String testStr();
    List<GiftCertificate> readAll();

    GiftCertificate readGiftCertificate(String id);
}
