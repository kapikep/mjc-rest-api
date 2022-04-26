package com.epam.esm.repository.impl;

import com.epam.esm.config.CoreConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreConfig.class)
class MySQLGiftCertificateRepositoryTest {
    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Test
    void readAll() throws RepositoryException {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.readAllGiftCertificates();
        giftCertificates.forEach(System.out::println);
    }

    @Test
    void readGiftCertificate() throws RepositoryException {
        System.out.println(giftCertificateRepository.readGiftCertificate(2));
    }

    @Test
    void createGiftCertificate() throws RepositoryException {
        GiftCertificate gs = new GiftCertificate();
        gs.setId(4);
        gs.setName("Боулинг для компании");
        gs.setDescription("Игра в боулинг станет отличным вариантом активного отдыха для большой компании.");
        gs.setPrice(45.0);
        gs.setDuration(60);
        //giftCertificateRepository.CreateGiftCertificate(gs);
    }

    @Test
    void updateGiftCertificate() throws RepositoryException {
        GiftCertificate gs = new GiftCertificate();
        gs.setId(4);
        gs.setName("Боулинг для компании");
        gs.setDescription("Игра в боулинг станет отличным вариантом активного отдыха для большой компании.");
        //gs.setPrice(45.0);
        gs.setDuration(60);
        //giftCertificateRepository.updateGiftCertificate(gs);
    }
}