package com.epam.esm.controller.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.repository.constant.GiftCertificateSearchParam;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import com.epam.esm.service.validator.groups.OnCreate;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.yaml.snakeyaml.tokens.Token.ID.Tag;

/**
 * Handles requests to /gift-certificates url
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@RestController
@RequestMapping("/gift-certificates")
@Validated
public class GiftCertificateRestController {
    private final GiftCertificateService service;
    private final MessageSource source;


    public GiftCertificateRestController(GiftCertificateService service, MessageSource source) {
        this.service = service;
        this.source = source;
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
     * Finds gift certificate by parameters
     */
    @GetMapping("/v1")
    public String findGiftCertificateByParams(
            @RequestParam(required = false, name = "tag") Integer tagName,
            @RequestParam(required = false, name = "name") @Min(value = 3) Integer name,
            @RequestParam(required = false, name = "description") Integer description,
            @RequestParam(required = false, name = "sort") String sort) throws ValidateException, ServiceException {

//
//        if (tagName != null) {
//            criteriaMap.put(GiftCertificateSearchParam.SEARCH_TAG_NAME, tagName);
//        }
//        if (name != null) {
//            criteriaMap.put(GiftCertificateSearchParam.SEARCH_NAME, name);
//        }
//        if (description != null) {
//            criteriaMap.put(GiftCertificateSearchParam.SEARCH_DESCRIPTION, description);
//        }
//        if (criteriaMap.isEmpty() && sort == null) {
//            gifts = service.readAllGiftCertificates();
//        } else {
//            gifts = service.findGiftCertificates(criteriaMap, sort);
//        }
//
//        for (GiftCertificateDto gift : gifts) {
//            gift.add(linkTo(GiftCertificateRestController.class).slash(gift.getId()).withSelfRel());
//            if(!gift.getTags().isEmpty()){
//                for (TagDto tag : gift.getTags()) {
//                    tag.add(linkTo(TagController.class).slash(tag.getId()).withRel("tag"));
//                }
//            }
//        }
        return "ss";
    }

    @GetMapping("/v2")
    public CollectionModel<GiftCertificateDto> findGiftCertificateByParams1(
            @RequestParam(required = false, name = "tag") String tagName,
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "description") String description,
            @RequestParam(required = false, name = "sort") String sort) throws ValidateException, ServiceException {
        Map<String, String> criteriaMap = new HashMap<>();
        List<GiftCertificateDto> gifts;

        if (tagName != null) {
            criteriaMap.put(GiftCertificateSearchParam.SEARCH_TAG_NAME, tagName);
        }
        if (name != null) {
            criteriaMap.put(GiftCertificateSearchParam.SEARCH_NAME, name);
        }
        if (description != null) {
            criteriaMap.put(GiftCertificateSearchParam.SEARCH_DESCRIPTION, description);
        }
        if (criteriaMap.isEmpty() && sort == null) {
            gifts = service.readAllGiftCertificates();
        } else {
            gifts = service.findGiftCertificates(criteriaMap, sort);
        }

        for (GiftCertificateDto gift : gifts) {
            gift.add(linkTo(GiftCertificateRestController.class).slash(gift.getId()).withSelfRel());
            if(!gift.getTags().isEmpty()){
                for (TagDto tag : gift.getTags()) {
                    tag.add(linkTo(TagController.class).slash(tag.getId()).withRel("tag"));
                }
            }
        }

        Link selfRel = linkTo(GiftCertificateRestController.class).withSelfRel();
        CollectionModel<GiftCertificateDto> res = CollectionModel.of(gifts, selfRel);
        return res;
    }

    /**
     * Creates gift certificate
     *
     * @param giftCertificateDto gift certificate to create
     * @return id created gift certificate
     */
    @PostMapping("/v1")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GiftCertificateDto createGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) throws ValidateException, ServiceException {
        service.createGiftCertificate(giftCertificateDto);
        return giftCertificateDto;
    }

    @PostMapping("/v2")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public GiftCertificateDto createGiftCertificate1(@RequestBody @Valid GiftCertificateDto giftCertificateDto) throws ValidateException, ServiceException {
        service.createGiftCertificate(giftCertificateDto);
        return giftCertificateDto;
    }

    /**
     * Updates gift certificate
     *
     * @param giftCertificateDto gift certificate to update
     * @return id created gift certificate
     */
    @PatchMapping
    public GiftCertificateDto updateGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) throws ValidateException, ServiceException {
        service.updateGiftCertificate(giftCertificateDto);
        return giftCertificateDto;
    }

    /**
     * Deletes gift certificate
     *
     * @param id id gift certificate to delete
     * @return id deleted gift certificate
     */
    @DeleteMapping(value = "/{id}", produces = "text/plain;charset=UTF-8")
    public String deleteGiftCertificate(@PathVariable String id) throws ValidateException, ServiceException {
        service.deleteGiftCertificate(id);
        return source.getMessage("gift.certificate.deleted", new Object[]{id}, LocaleContextHolder.getLocale());
    }
}
