package com.epam.esm.controller.impl;

import com.epam.esm.controller.util.PaginationUtil;
import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.repository.constant.SearchParam;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import com.epam.esm.service.validator.groups.OnCreate;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.controller.util.PaginationUtil.addGiftCertificateLink;
import static com.epam.esm.controller.util.PaginationUtil.getSelfLink;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
     * Finds gift certificate by parameters
     */
    @GetMapping
    public PagedModel<GiftCertificateDto> findGiftCertificateByParams1(
            @RequestParam(required = false, name = "tag") String tagName,
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "description") String description,
            @RequestParam(required = false, name = "page") Integer page,
            @RequestParam(required = false, name = "size") Integer size,
            @RequestParam(required = false, name = "sort") String sort) throws ValidateException, ServiceException, InterruptedException {
        Map<String, String> searchParam = new HashMap<>();
        CriteriaDto cr = new CriteriaDto();
        List<GiftCertificateDto> gifts;
        cr.setPage(page);
        cr.setSize(size);
        cr.setSorting(sort);

        if (tagName != null) {
            searchParam.put(SearchParam.GIFT_SEARCH_BY_TAG_NAME, tagName);
        }
        if (name != null) {
            searchParam.put(SearchParam.GIFT_SEARCH_NAME, name);
        }
        if (description != null) {
            searchParam.put(SearchParam.GIFT_SEARCH_DESCRIPTION, description);
        }

        long a = System.currentTimeMillis();

        if (searchParam.isEmpty()) {
            gifts = service.readPage(cr);
        } else {
            cr.setSearchParam(searchParam);
            gifts = service.find(cr);
        }
        long b = System.currentTimeMillis();

        System.out.println("total time -> " + (b - a) + " ms");
        System.out.println("total size -> " + cr.getTotalSize());
        System.out.println("list size -> " + gifts.size());

        PagedModel<GiftCertificateDto> pagedModel = PaginationUtil.createPagedModel(gifts, cr);
        PaginationUtil.addPaginationLinks(pagedModel);
        return pagedModel;
    }

    /**
     * Gets gift certificate by id
     *
     * @param id id for search
     * @return gift certificate by id
     */
    @GetMapping("/{id}")
    public GiftCertificateDto readGiftCertificate(@PathVariable long id) throws ValidateException, ServiceException {
        GiftCertificateDto dto = service.readOne(id);
//        addGiftCertificateLink(dto);
        return dto;
    }

    /**
     * Creates gift certificate
     *
     * @param dto gift certificate to create
     * @return id created gift certificate
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GiftCertificateDto createGiftCertificate(@RequestBody GiftCertificateDto dto) throws ValidateException, ServiceException {
        service.create(dto);
        addGiftCertificateLink(dto);
        return dto;
    }

    @PostMapping("/v2")
    @ResponseStatus(code = HttpStatus.CREATED)
//    @Validated(OnCreate.class)
    public GiftCertificateDto createGiftCertificate1(@RequestBody @Valid GiftCertificateDto dto) throws ValidateException, ServiceException {
        service.create(dto);
        addGiftCertificateLink(dto);
        return dto;
    }

    /**
     * Updates gift certificate
     *
     * @param dto gift certificate to update
     * @return id created gift certificate
     */
    @PatchMapping("/{id}")
    public GiftCertificateDto updateGiftCertificate(@PathVariable long id,
                                                    @RequestBody GiftCertificateDto dto) throws ValidateException, ServiceException {
        dto.setId(id);
        service.update(dto);
        addGiftCertificateLink(dto);
        return dto;
    }

    /**
     * Deletes gift certificate
     *
     * @param id id gift certificate to delete
     * @return id deleted gift certificate
     */
    @DeleteMapping(value = "/{id}", produces = "text/plain;charset=UTF-8")
    public String deleteGiftCertificate(@PathVariable long id) throws ValidateException, ServiceException {
        service.delete(id);
        return source.getMessage("gift.certificate.deleted", new Object[]{id}, LocaleContextHolder.getLocale());
    }
}
