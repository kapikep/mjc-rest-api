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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Handles requests to /gift-certificates url
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateRestController implements GiftCertificateController {
    private final GiftCertificateService service;


    public GiftCertificateRestController(GiftCertificateService service) {
        this.service = service;
    }

    /**
     * Gets gift certificate by id
     *
     * @param id id for search
     * @return gift certificate by id
     */
    @GetMapping("/{id}")
    public GiftCertificateDto readGiftCertificate(@PathVariable String id) throws ValidateException, ServiceException {
            return service.readGiftCertificate(id);
    }

    /**
     * Creates gift certificate
     *
     * @param giftCertificateDto gift certificate to create
     * @return id created gift certificate
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GiftCertificateDto createGiftCertificate (@RequestBody GiftCertificateDto giftCertificateDto) throws ValidateException, ServiceException {
        int id = service.createGiftCertificate(giftCertificateDto);
        giftCertificateDto.setId(id);
        return giftCertificateDto;
    }

    /**
     * Updates gift certificate
     *
     * @param giftCertificateDto gift certificate to update
     * @return id created gift certificate
     */
    @PutMapping
    public GiftCertificateDto updateGiftCertificate (@RequestBody GiftCertificateDto giftCertificateDto) throws ValidateException, ServiceException {
        service.updateGiftCertificate(giftCertificateDto);
        return giftCertificateDto;
    }

    /**
     * Deletes gift certificate
     *
     * @param id id gift certificate to delete
     * @return id deleted gift certificate
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public String deleteGiftCertificate(@PathVariable String id) throws ValidateException, ServiceException {
        service.deleteGiftCertificate(id);
        return id;
    }


    /**
     * Finds gift certificate by parameters
     */
    @GetMapping
    public List<GiftCertificateDto> findGiftCertificateByParams(
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
