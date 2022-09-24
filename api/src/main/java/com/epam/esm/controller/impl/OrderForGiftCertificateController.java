package com.epam.esm.controller.impl;

import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.interf.OrderForGiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

import static com.epam.esm.controller.constant.EndPoints.ID;
import static com.epam.esm.controller.constant.EndPoints.ORDERS;
import static com.epam.esm.controller.util.PaginationUtil.getSelfLink;

/**
 * Handles requests to /orders url
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ORDERS)
public class OrderForGiftCertificateController {
    private final OrderForGiftCertificateService orderService;

    /**
     * Read OrderForGiftCertificateDto by id.
     * Add self links to the OrderForGiftCertificateDto.
     *
     * @param id id of gift certificate order to search.
     * @return OrderForGiftCertificateDto by id.
     * @throws ServiceException             if GiftCertificateDto with this id does not exist.
     * @throws ConstraintViolationException if id is not positive.
     */
    @GetMapping(ID)
    public OrderForGiftCertificateDto readOrderForGiftCertificateById(@PathVariable long id) throws ServiceException {
        OrderForGiftCertificateDto order = orderService.readOrderForGiftCertificateById(id);
        order.add(getSelfLink(OrderForGiftCertificateController.class, order.getId()));

        order.getOrderItems().forEach(items -> items.getGiftCertificate()
                .add(getSelfLink(GiftCertificateRestController.class, items.getGiftCertificate().getId())));
        return order;
    }
}
