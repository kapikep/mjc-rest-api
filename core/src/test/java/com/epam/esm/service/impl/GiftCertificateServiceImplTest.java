package com.epam.esm.service.impl;

import com.epam.esm.config.CoreConfig;
import com.epam.esm.config.DevConfig;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DevConfig.class)
class GiftCertificateServiceImplTest {

    @Autowired
    private GiftCertificateService giftCertificateService;

    @Test
    void readAll() throws ValidateException, ServiceException {
//        List<GiftCertificateDto> giftCertificates = giftCertificateService.readAllGiftCertificates();
//        System.out.println("service ->" + giftCertificates);
    }

    @Test
    void readGiftCertificate() {
//        GiftCertificateDto giftCertificate = null;
//        try {
//            giftCertificate = giftCertificateService.readGiftCertificate("2");
//        } catch (com.epam.esm.service.exception.ServiceException e) {
//            throw new RuntimeException(e);
//        } catch (com.epam.esm.service.exception.ValidateException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("service ->" + giftCertificate);
    }
}