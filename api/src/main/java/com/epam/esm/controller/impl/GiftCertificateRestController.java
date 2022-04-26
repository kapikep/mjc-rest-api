package com.epam.esm.controller.impl;

import com.epam.esm.controller.interf.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class GiftCertificateRestController implements GiftCertificateController {
    private final GiftCertificateService service;

    public GiftCertificateRestController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping("/gift-certificates")
    public List<GiftCertificateDto> readAllGiftCertificates() throws ValidateException, ServiceException {
        return service.readAllGiftCertificates();
    }

    @GetMapping("/gift-certificates/{id}")
    public GiftCertificateDto readGiftCertificate(@PathVariable String id) throws ValidateException, ServiceException {
            return service.readGiftCertificate(id);
    }

    @PostMapping("/gift-certificates")
    public GiftCertificateDto createGiftCertificate (@RequestBody GiftCertificateDto giftCertificateDto) throws ValidateException, ServiceException {
        service.createGiftCertificate(giftCertificateDto);
        return giftCertificateDto;
    }

    @PutMapping("/gift-certificates")
    public GiftCertificateDto updateGiftCertificate (@RequestBody GiftCertificateDto giftCertificateDto) throws ValidateException, ServiceException {
        service.updateGiftCertificate(giftCertificateDto);
        return giftCertificateDto;
    }

    @DeleteMapping("/gift-certificates/{id}")
    public GiftCertificate deleteGiftCertificate(@PathVariable String id) throws ValidateException, ServiceException {
        service.deleteGiftCertificate(id);
        return new GiftCertificate();
    }
}
