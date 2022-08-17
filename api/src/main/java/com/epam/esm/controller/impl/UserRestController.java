package com.epam.esm.controller.impl;

import com.epam.esm.controller.util.PaginationUtil;
import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.OrderForGiftCertificateService;
import com.epam.esm.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.epam.esm.controller.util.PaginationUtil.getSelfLink;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRestController {
    private final UserService userService;
    private final OrderForGiftCertificateService orderService;

    @GetMapping
    public PagedModel<UserDto> rearPage(@RequestParam(required = false, name = "page") Integer page,
                                        @RequestParam(required = false, name = "size") Integer size,
                                        @RequestParam(required = false, name = "sort") String sort) throws ValidateException, ServiceException {
        CriteriaDto cr = new CriteriaDto();
        cr.setPage(page);
        cr.setSize(size);
        cr.setSorting(sort);

        List<UserDto> dtoList = userService.readUsersPaginated(cr);

        dtoList.forEach(dto -> dto.add(getSelfLink(UserRestController.class, dto.getId())));

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
        List<OrderForGiftCertificateDto> order = orderService.readUserOrdersForGiftCertificatePaginated(customerId, cr);

        PagedModel<OrderForGiftCertificateDto> pagedModel = PaginationUtil.createPagedModel(order, cr);
        PaginationUtil.addPaginationLinks(pagedModel);
        return pagedModel;
    }

    @GetMapping("/{id}")
    public UserDto readUser(@PathVariable long id) throws ServiceException {
        UserDto dto = userService.readUserById(id);
        dto.add(getSelfLink(UserRestController.class, dto.getId()));
        return dto;
    }

    @PostMapping(value = "/{customerId}/orders")
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrderForGiftCertificateDto createOrder(@PathVariable long customerId,
                                                  @RequestBody List<OrderItemDto> items) throws ValidateException, ServiceException {

        return orderService.createOrderForGiftCertificate(customerId, items);
    }
}
