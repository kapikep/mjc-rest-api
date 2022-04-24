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

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreConfig.class)
class MySQLGiftCertificateRepositoryTest {
    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Test
    void readAll() throws RepositoryException {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.readAll();
        System.out.println(giftCertificates);
    }

    @Test
    void readGiftCertificate() throws RepositoryException {
        System.out.println(giftCertificateRepository.readGiftCertificate(2));
    }
}