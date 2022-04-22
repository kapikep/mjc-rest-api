package com.epam.esm.repository.impl;

import com.epam.esm.config.CoreConfig;
import com.epam.esm.entity.GiftCertificate;
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
    void readAll() {
        List<GiftCertificate> giftCertificates;
        System.out.println(giftCertificateRepository);
        giftCertificates = giftCertificateRepository.readAll();
        System.out.println(giftCertificates);
        try {
            System.out.println(Class.forName("org.mariadb.jdbc.Driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}