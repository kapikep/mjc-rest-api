package com.epam.esm.controller.impl;

import com.epam.esm.config.CoreConfig;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreConfig.class)
class RESTGiftCertificateControllerTest {

    @Autowired
    private GiftCertificateService service;

    @Test
    void readAll() throws ValidateException, ServiceException {
        System.out.println("service -> " + service.readAllGiftCertificates());
    }
}