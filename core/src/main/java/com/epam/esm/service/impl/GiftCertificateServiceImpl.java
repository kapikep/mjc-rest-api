package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.impl.MySQLGiftCertificateRepository;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import com.epam.esm.service.interf.GiftCertificateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;

    public GiftCertificateServiceImpl(GiftCertificateRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GiftCertificate> readAll() {
        List<GiftCertificate> giftCertificates = repository.readAll();
        return giftCertificates;
    }

    @Override
    public GiftCertificate readGiftCertificate(String idStr) {
        int id = Integer.parseInt(idStr);
        GiftCertificate giftCertificate = repository.readGiftCertificate(id);
        return giftCertificate;
    }
}
