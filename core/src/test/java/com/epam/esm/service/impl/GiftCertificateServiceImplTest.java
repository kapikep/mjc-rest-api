package com.epam.esm.service.impl;

import com.epam.esm.config.CoreConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.interf.GiftCertificateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreConfig.class)
class GiftCertificateServiceImplTest {

    @Autowired
    private GiftCertificateService giftCertificateService;

    @Test
    void readAll() {
        List<GiftCertificate>giftCertificates = giftCertificateService.readAll();
        System.out.println("service ->" + giftCertificates);
    }

    @Test
    void readGiftCertificate() {
        GiftCertificate giftCertificate = giftCertificateService.readGiftCertificate("2");
        System.out.println("service ->" + giftCertificate);
    }
}