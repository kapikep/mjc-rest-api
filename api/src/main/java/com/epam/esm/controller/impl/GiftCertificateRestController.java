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
@RequestMapping("/gift-certificates")
public class GiftCertificateRestController implements GiftCertificateController {
    private final GiftCertificateService service;

    public GiftCertificateRestController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public GiftCertificateDto readGiftCertificate(@PathVariable String id) throws ValidateException, ServiceException {
            return service.readGiftCertificate(id);
    }

    @PostMapping
    public GiftCertificateDto createGiftCertificate (@RequestBody GiftCertificateDto giftCertificateDto) throws ValidateException, ServiceException {
        service.createGiftCertificate(giftCertificateDto);
        return giftCertificateDto;
    }

    @PutMapping
    public GiftCertificateDto updateGiftCertificate (@RequestBody GiftCertificateDto giftCertificateDto) throws ValidateException, ServiceException {
        service.updateGiftCertificate(giftCertificateDto);
        return giftCertificateDto;
    }

    @DeleteMapping("/{id}")
    public GiftCertificate deleteGiftCertificate(@PathVariable String id) throws ValidateException, ServiceException {
        service.deleteGiftCertificate(id);
        return new GiftCertificate();
    }

    /**
     * Searches for certificate by provided params
     * @param tagName name of the tag that certificate should be connected with
     * @param name part of the certificate name
     * @param description part of the certificate description
     * @param sortByDate is sort by date should be carried out
     * @param sortByDateType sort by date ASC or DESC
     * @param sortByName is sort by name should be carried out
     * @param sortByNameType sort by name ASC or DESC
     * @return list of the certificates that correspond to the provided params
     */
    @GetMapping
    public List<GiftCertificateDto> findByParams(
            @RequestParam(required = false, name = "tagName") String tagName,
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "description") String description,
            @RequestParam(required = false, name = "sortByDate") String sortByDate,
            @RequestParam(required = false, name = "sortByDateType") String sortByDateType,
            @RequestParam(required = false, name = "sortByName") String sortByName,
            @RequestParam(required = false, name = "sortByNameType") String sortByNameType) throws ValidateException, ServiceException {
        return service.findGiftCertificates(tagName, name, description, sortByDate, sortByDateType, sortByName, sortByNameType);
    }
}
