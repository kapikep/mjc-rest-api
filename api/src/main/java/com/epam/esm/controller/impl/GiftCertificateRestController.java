package com.epam.esm.controller.impl;

import com.epam.esm.controller.util.PaginationUtil;
import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.repository.constant.SearchParam;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.epam.esm.controller.util.PaginationUtil.addGiftCertificateLink;

/**
 * Handles requests to /gift-certificates url
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/gift-certificates")
public class GiftCertificateRestController {
    private final GiftCertificateService giftCertificateService;
    private final MessageSource messageSource;

    /**
     * Finds gift certificate by parameters
     */
    @GetMapping
    public PagedModel<GiftCertificateDto> findGiftCertificateByParams(
            @RequestParam(required = false, name = "tag") String tagName,
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "description") String description,
            @RequestParam(required = false, name = "page") Integer page,
            @RequestParam(required = false, name = "size") Integer size,
            @RequestParam(required = false, name = "sort") String sort) throws ValidateException, ServiceException {
        CriteriaDto cr = new CriteriaDto();
        List<GiftCertificateDto> gifts;
        cr.setPage(page);
        cr.setSize(size);
        cr.setSorting(sort);

        if (tagName != null) {
            cr.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, tagName);
        }
        if (name != null) {
            cr.addSearchParam(SearchParam.GIFT_SEARCH_NAME, name);
        }
        if (description != null) {
            cr.addSearchParam(SearchParam.GIFT_SEARCH_DESCRIPTION, description);
        }

        long a = System.currentTimeMillis();

        gifts = cr.getSearchParam() == null ? giftCertificateService.readGiftCertificatesPaginated(cr)
                : giftCertificateService.findGiftCertificatesByCriteria(cr);

        long b = System.currentTimeMillis();

        System.out.println("total time -> " + (b - a) + " ms");
        System.out.println("total size -> " + cr.getTotalSize());
        System.out.println("list size -> " + gifts.size());

        gifts.forEach(PaginationUtil::addGiftCertificateLink);

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
    public GiftCertificateDto readGiftCertificate(@PathVariable long id) throws ServiceException {
        GiftCertificateDto gift = giftCertificateService.readGiftCertificateById(id);
        addGiftCertificateLink(gift);
        return gift;
    }

    /**
     * Creates gift certificate
     *
     * @param gift gift certificate to create
     * @return id created gift certificate
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GiftCertificateDto createGiftCertificate(@RequestBody GiftCertificateDto gift) throws ValidateException, ServiceException {
        giftCertificateService.createGiftCertificate(gift);
        addGiftCertificateLink(gift);
        return gift;
    }

    /**
     * Updates gift certificate
     *
     * @param gift gift certificate to update
     * @return id created gift certificate
     */
    @PatchMapping("/{id}")
    public GiftCertificateDto updateGiftCertificate(@PathVariable long id,
                                                    @RequestBody GiftCertificateDto gift) throws ValidateException, ServiceException {
        gift.setId(id);
        giftCertificateService.updateGiftCertificate(gift);
        addGiftCertificateLink(gift);
        return gift;
    }

    /**
     * Deletes gift certificate
     *
     * @param id id gift certificate to delete
     * @return id deleted gift certificate
     */
    @DeleteMapping(value = "/{id}", produces = "text/plain;charset=UTF-8")
    public String deleteGiftCertificate(@PathVariable long id) throws ServiceException {
        giftCertificateService.deleteGiftCertificateById(id);
        return messageSource.getMessage("gift.certificate.deleted", new Object[]{id}, LocaleContextHolder.getLocale());
    }
}
