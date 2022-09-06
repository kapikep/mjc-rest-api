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

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.epam.esm.controller.util.PaginationUtil.getSelfLink;

/**
 * Handles requests to /users url
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRestController {
    private final UserService userService;
    private final OrderForGiftCertificateService orderService;

    /**
     * Read UserDto paginated.
     * Add self links to UserDto.
     * Add pagination information.
     *
     * @param page page to read. The parameter is optional. Default value 1.
     * @param size size of page. The parameter is optional. Default value 20.
     * @param sort sorting field. The parameter is optional.
     * @return UserDto list
     * @throws ServiceException  if page or size is null or less 1.
     *                           If the page is larger than the total size of the pages.
     *                           If any RepositoryException or DataAccessException has occurred.
     * @throws ValidateException if sorting field does not match USER_SORT_PARAM.
     */
    @GetMapping
    public PagedModel<UserDto> readUsersPaginated(@RequestParam(required = false, name = "page") Integer page,
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

    /**
     * Read gift certificate order list from repository by customer id.
     * Add pagination information.
     *
     * @param customerId user id to search for OrdersForGiftCertificate.
     * @param page       page to read.
     * @param size       size of page.
     * @param sort       sorting field.
     * @return OrderForGiftCertificateDto by customer id.
     * @throws ValidateException            if page or size is null or less 1.
     *                                      If any RepositoryException or DataAccessException has occurred.
     * @throws ServiceException             if sorting field does not match ORDER_SORT_PARAM.
     * @throws ConstraintViolationException if customerId is not positive.
     */
    @GetMapping(value = "/{customerId}/orders")
    public PagedModel<OrderForGiftCertificateDto> readUserOrdersForGiftCertificatePaginated(@PathVariable long customerId,
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

    /**
     * Read UserDto by id.
     * Add self links to the user.
     *
     * @param id id of user to search for.
     * @return UserDto by id.
     * @throws ServiceException             if GiftCertificateDto with this id does not exist.
     *                                      If any RepositoryException or DataAccessException has occurred.
     * @throws ConstraintViolationException if id is not positive.
     */
    @GetMapping("/{id}")
    public UserDto readUser(@PathVariable long id) throws ServiceException {
        UserDto dto = userService.readUserById(id);
        dto.add(getSelfLink(UserRestController.class, dto.getId()));
        return dto;
    }

    /**
     * Create new gift certificate order with items for user with customerId.
     *
     * @param customerId customer id for the OrderForGiftCertificate.
     * @param items      order items for the OrderForGiftCertificate.
     * @return new OrderForGiftCertificateDto with items for user with customerId.
     * @throws ServiceException             if user with this customerId does not exist in repository.
     *                                      If gift certificate does not exist in repository.
     *                                      If any IllegalArgumentException or has occurred.
     * @throws ConstraintViolationException if customerId is not positive.
     */
    @PostMapping(value = "/{customerId}/orders")
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrderForGiftCertificateDto createOrder(@PathVariable long customerId,
                                                  @RequestBody List<OrderItemDto> items) throws ServiceException {

        return orderService.createOrderForGiftCertificate(customerId, items);
    }
}
