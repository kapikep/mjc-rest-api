package com.epam.esm.controller.impl;

import com.epam.esm.controller.interf.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.constant.GiftCertificateSearchParam;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping
    public List<GiftCertificateDto> findByParams(
            @RequestParam(required = false, name = "tag") String tagName,
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "description") String description,
            @RequestParam(required = false, name = "sort") String sort) throws ValidateException, ServiceException {
        Map<String, String> criteriaMap = new HashMap<>();
        if(tagName != null){
            criteriaMap.put(GiftCertificateSearchParam.SEARCH_TAG_NAME, tagName);
        }
        if(name != null){
            criteriaMap.put(GiftCertificateSearchParam.SEARCH_NAME, name);
        }
        if(description != null){
            criteriaMap.put(GiftCertificateSearchParam.SEARCH_DESCRIPTION, description);
        }
        return service.findGiftCertificates(criteriaMap, sort);
    }
}
