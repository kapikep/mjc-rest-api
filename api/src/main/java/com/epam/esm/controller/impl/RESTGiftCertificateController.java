package com.epam.esm.controller.impl;

import com.epam.esm.controller.interf.GiftCertificateController;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.interf.GiftCertificateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class RESTGiftCertificateController implements GiftCertificateController {
    private final GiftCertificateService service;

    public RESTGiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping("/gift")
    public List<GiftCertificate> readAll() {
        return service.readAll();
    }

}
