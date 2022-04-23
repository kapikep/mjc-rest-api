package com.epam.esm.controller.impl;

import com.epam.esm.config.CoreConfig;
import com.epam.esm.service.interf.GiftCertificateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreConfig.class)
class RESTGiftCertificateControllerTest {

    @Autowired
    private GiftCertificateService service;

    @Test
    void readAll() {
        System.out.println("service -> " + service.readAll());
    }
}