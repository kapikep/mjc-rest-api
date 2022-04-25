package com.epam.esm.controller.impl;

import com.epam.esm.controller.interf.GiftCertificateController;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class GiftCertificateRestController implements GiftCertificateController {
    private final GiftCertificateService service;

    public GiftCertificateRestController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping("/gift-certificates")
    public List<GiftCertificate> readAllGiftCertificates() throws ValidateException, ServiceException {
        return service.readAllGiftCertificates();
    }

    @GetMapping("/gift-certificate/{id}")
    public GiftCertificate readGiftCertificate(@PathVariable String id) throws ValidateException, ServiceException {
            return service.readGiftCertificate(id);
    }

    @PostMapping("/gift-certificates")
    public GiftCertificate createGiftCertificate (@RequestBody GiftCertificate giftCertificate) throws ValidateException, ServiceException {
        service.createGiftCertificate(giftCertificate);
        return giftCertificate;
    }
}
