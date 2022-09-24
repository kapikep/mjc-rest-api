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

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.epam.esm.controller.constant.Constant.DESCRIPTION;
import static com.epam.esm.controller.constant.Constant.NAME;
import static com.epam.esm.controller.constant.Constant.PAGE;
import static com.epam.esm.controller.constant.Constant.SIZE;
import static com.epam.esm.controller.constant.Constant.SORT;
import static com.epam.esm.controller.constant.Constant.TAG;
import static com.epam.esm.controller.constant.EndPoints.GIFT_CERTIFICATES;
import static com.epam.esm.controller.constant.EndPoints.ID;
import static com.epam.esm.controller.util.ControllerUtil.idInBodyValidation;
import static com.epam.esm.controller.util.PaginationUtil.addGiftCertificateLink;

/**
 * Handles requests to /gift-certificates url
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(GIFT_CERTIFICATES)
public class GiftCertificateRestController {
    private final GiftCertificateService giftCertificateService;
    private final MessageSource messageSource;

    /**
     * Find GiftCertificateDto by tag name, name, description and sort by sorting param paginated.
     * If optional search parameters not present read gift certificates paginated.
     * Add pagination information.
     *
     * @param tagName     read gift certificates with tag name or several tags. The parameter is optional.
     * @param name        read gift certificates with name. The parameter is optional.
     * @param description read gift certificates with description. The parameter is optional.
     * @param page        page to read. The parameter is optional. Default value 1.
     * @param size        size of page. The parameter is optional. Default value 20.
     * @param sort        sorting field. The parameter is optional.
     * @return GiftCertificateDto list.
     * @throws ValidateException if page or size is null or less 1.
     *                           If the page is larger than the total size of the pages.
     * @throws ServiceException  if sorting field does not match GIFT_CERTIFICATE_SORT_PARAM.
     */
    @GetMapping
    public PagedModel<GiftCertificateDto> findGiftCertificateByParams(
            @RequestParam(required = false, name = TAG) String tagName,
            @RequestParam(required = false, name = NAME) String name,
            @RequestParam(required = false, name = DESCRIPTION) String description,
            @RequestParam(required = false, name = PAGE) Integer page,
            @RequestParam(required = false, name = SIZE) Integer size,
            @RequestParam(required = false, name = SORT) String sort) throws ValidateException, ServiceException {
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

        gifts = cr.getSearchParam() == null ? giftCertificateService.readGiftCertificatesPaginated(cr)
                : giftCertificateService.findGiftCertificatesByCriteria(cr);

        gifts.forEach(PaginationUtil::addGiftCertificateLink);

        PagedModel<GiftCertificateDto> pagedModel = PaginationUtil.createPagedModel(gifts, cr);
        PaginationUtil.addPaginationLinks(pagedModel);
        return pagedModel;
    }

    /**
     * Read GiftCertificateDto by id.
     * Add self links to GiftCertificateDto.
     *
     * @param id id for GiftCertificateDto search.
     * @return GiftCertificateDto by id.
     * @throws ServiceException             if TagDto with id does not exist.
     * @throws ConstraintViolationException if id is not positive.
     */
    @GetMapping(ID)
    public GiftCertificateDto readGiftCertificate(@PathVariable long id) throws ServiceException {
        GiftCertificateDto gift = giftCertificateService.readGiftCertificateById(id);
        addGiftCertificateLink(gift);
        return gift;
    }

    /**
     * Create new gift certificate.
     * Add self links to GiftCertificateDto.
     *
     * @param gift GiftCertificateDto to create.
     * @return Created GiftCertificateDto.
     * @throws ConstraintViolationException if GiftCertificateDto fields is constraint violation.
     * @throws ValidateException            if id is in request body.
     * @throws ServiceException             if there is problem with creating gift certificate.
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GiftCertificateDto createGiftCertificate(@RequestBody GiftCertificateDto gift) throws ServiceException, ValidateException {
        idInBodyValidation(gift.getId());
        giftCertificateService.createGiftCertificate(gift);
        addGiftCertificateLink(gift);
        return gift;
    }

    /**
     * Update gift certificate
     * Add self links to GiftCertificateDto.
     *
     * @param id   GiftCertificateDto id.
     * @param gift GiftCertificateDto to update.
     * @return Updated GiftCertificateDto.
     * @throws ConstraintViolationException if GiftCertificateDto fields is constraint violation
     * @throws ValidateException            if id is in request body.
     * @throws ServiceException             if entity with this id does not exist.
     *                                      If there is a problem with updating gift certificate.
     */
    @PatchMapping(ID)
    public GiftCertificateDto updateGiftCertificate(@PathVariable long id,
                                                    @RequestBody GiftCertificateDto gift) throws ServiceException, ValidateException {
        idInBodyValidation(gift.getId());
        gift.setId(id);
        giftCertificateService.updateGiftCertificate(gift);
        addGiftCertificateLink(gift);
        return gift;
    }

    /**
     * Deletes gift certificate by id
     *
     * @param id id gift certificate to delete
     * @return informational message about success
     * @throws ServiceException if gift certificate with this id does not exist in repository.
     */
    @DeleteMapping(value = ID, produces = "text/plain;charset=UTF-8")
    public String deleteGiftCertificate(@PathVariable long id) throws ServiceException {
        giftCertificateService.deleteGiftCertificateById(id);
        return messageSource.getMessage("gift.certificate.deleted", new Object[]{id}, LocaleContextHolder.getLocale());
    }
}
