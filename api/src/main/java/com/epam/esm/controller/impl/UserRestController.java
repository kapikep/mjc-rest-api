package com.epam.esm.controller.impl;

import com.epam.esm.controller.util.PaginationUtil;
import com.epam.esm.dto.*;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.OrderForGiftCertificateService;
import com.epam.esm.service.interf.UserService;
import com.epam.esm.service.util.ServiceUtil;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.controller.util.PaginationUtil.getSelfLink;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private final UserService service;
    private final OrderForGiftCertificateService orderService;
    private final MessageSource source;

    public UserRestController(UserService service, OrderForGiftCertificateService orderService, MessageSource source) {
        this.service = service;
        this.orderService = orderService;
        this.source = source;
    }

    @GetMapping
    public PagedModel<UserDto> rearPage(@RequestParam(required = false, name = "page") Integer page,
                                        @RequestParam(required = false, name = "size") Integer size,
                                        @RequestParam(required = false, name = "sort") String sort) throws ValidateException, ServiceException {
        CriteriaDto cr = new CriteriaDto();
        cr.setPage(page);
        cr.setSize(size);
        cr.setSorting(sort);

        List<UserDto> dtoList = service.readPage(cr);

        for (UserDto dto : dtoList) {
            dto.add(getSelfLink(UserEntity.class, dto.getId()));
        }

        PagedModel<UserDto> pagedModel = PaginationUtil.createPagedModel(dtoList, cr);
        PaginationUtil.addPaginationLinks(pagedModel);
        return pagedModel;
    }

    @GetMapping(value = "/{customerId}/orders")
    public PagedModel<OrderForGiftCertificateDto> getUserOrders(@PathVariable long customerId,
                                                                @RequestParam(required = false, name = "page") Integer page,
                                                                @RequestParam(required = false, name = "size") Integer size,
                                                                @RequestParam(required = false, name = "sort") String sort) throws ValidateException, ServiceException {
        CriteriaDto cr = new CriteriaDto();
        cr.setPage(page);
        cr.setSize(size);
        cr.setSorting(sort);
        List<OrderForGiftCertificateDto> order = orderService.getUserOrders(customerId, cr);

        PagedModel<OrderForGiftCertificateDto> pagedModel = PaginationUtil.createPagedModel(order, cr);
        PaginationUtil.addPaginationLinks(pagedModel);
        return pagedModel;
    }

    @GetMapping("/{id}")
    public UserDto readUser(@PathVariable long id) throws ValidateException, ServiceException {
        UserDto dto = service.readOne(id);
        dto.add(getSelfLink(UserEntity.class, dto.getId()));
        return dto;
    }

    @PostMapping(value = "/{customerId}/orders")
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrderForGiftCertificateDto createOrder(@PathVariable long customerId,
                                                  @RequestBody List<OrderItemDto> items) throws ValidateException, ServiceException {

        OrderForGiftCertificateDto order = orderService.create(customerId, items);
        return order;
    }
}
