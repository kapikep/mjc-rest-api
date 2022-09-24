package com.epam.esm.service.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.OrderForGiftCertificateRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.GiftCertificateService;
import com.epam.esm.service.interf.OrderForGiftCertificateService;
import com.epam.esm.service.interf.UserService;
import com.epam.esm.service.util.OrderForGiftCertificateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.CriteriaUtil.setDefaultPageValIfEmpty;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.orderForGiftCertificateDtoToEntityConverting;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.orderForGiftCertificateEntityToDtoConverting;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.orderForGiftCertificateSortingValidation;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.updateFieldsInDtoFromEntity;

/**
 * Service for OrderForGiftCertificate.
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class OrderForGiftCertificateServiceImpl implements OrderForGiftCertificateService {
    private static final String ERROR_USER_NOT_FOUND = "error.user.not.found";
    private static final String ERROR_GIFT_NOT_FOUND = "error.gift.not.found";
    private static final String ERROR_RESOURCE_NOT_FOUND = "error.resource.not.found";

    private final OrderForGiftCertificateRepository orderRepository;
    private final UserService userService;
    private final GiftCertificateService giftService;

    /**
     * Validate CriteriaDto.
     * Set default value in CriteriaDto.
     * Read OrderForGiftCertificateEntities from repository by user id.
     * and converting it to OrderForGiftCertificateDto list.
     * Set total size.
     *
     * @param userId user id to search for OrdersForGiftCertificate.
     * @param crDto  CriteriaEntity with params for pagination.
     * @return OrderForGiftCertificateDto list.
     * @throws ServiceException  if page or size is null or less 1.
     *                           If any RepositoryException or DataAccessException has occurred.
     * @throws ValidateException if sorting field does not match ORDER_SORT_PARAM.
     */
    @Override
    public List<OrderForGiftCertificateDto> readUserOrdersForGiftCertificatePaginated(long userId, CriteriaDto crDto)
            throws ValidateException, ServiceException {
        try {
            userService.readUserById(userId);
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage(), e, ERROR_USER_NOT_FOUND, userId);
        }
        setDefaultPageValIfEmpty(crDto);
        orderForGiftCertificateSortingValidation(crDto);
        CriteriaEntity cr = criteriaDtoToEntityConverting(crDto);
        List<OrderForGiftCertificateDto> orders;
        try {
            orders = OrderForGiftCertificateUtil
                    .orderForGiftCertificateEntityListToDtoConverting(orderRepository.readUserOrdersPaginated(userId, cr));
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        crDto.setTotalSize(cr.getTotalSize());
        return orders;
    }

    /**
     * Validate id.
     * Read TagEntity by id from repository and convert it to TagDto.
     *
     * @param id unique identifier of the tag to search for.
     * @return TagDto by id.
     * @throws ServiceException if TagEntity with id does not exist.
     *                          If any RepositoryException or DataAccessException has occurred.
     */
    @Override
    public OrderForGiftCertificateDto readOrderForGiftCertificateById(long id) throws ServiceException {
        OrderForGiftCertificateDto orderDto;
        try {
            orderDto = orderForGiftCertificateEntityToDtoConverting(orderRepository.readById(id));
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e, ERROR_RESOURCE_NOT_FOUND, id);
        } catch (DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return orderDto;
    }

    /**
     * Validate OrderItemDto list fields OnCreate group.
     * Validate customerId. It must be positive.
     * Read user by customerId.
     * Read gift certificate by OrderItemDto giftCertificate id field.
     * Set giftCertificate with full data from repository to OrderItemDto.
     * Calculate total price.
     * Convert OrderForGiftCertificateDto to OrderForGiftCertificateEntity.
     * Create new OrderForGiftCertificateEntity in repository.
     *
     * @param customerId customer id for the OrderForGiftCertificate.
     * @param orderItems order items for the OrderForGiftCertificate.
     * @throws ServiceException if user with this customerId does not exist in repository.
     *                          If gift certificate does not exist in repository.
     *                          If any IllegalArgumentException or  has occurred.
     */
    @Override
    public OrderForGiftCertificateDto createOrderForGiftCertificate(long customerId, List<OrderItemDto> orderItems) throws ServiceException {
        OrderForGiftCertificateDto orderDto = new OrderForGiftCertificateDto();
        BigDecimal totalAmount = new BigDecimal(0);
        orderDto.setId(0);
        try {
            orderDto.setUser(userService.readUserById(customerId));
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage(), e, ERROR_USER_NOT_FOUND, customerId);
        }

        for (OrderItemDto item : orderItems) {
            try {
                GiftCertificateDto gift = giftService.readGiftCertificateById(item.getGiftCertificate().getId());
                item.setGiftCertificate(gift);
                BigDecimal price = BigDecimal.valueOf(gift.getPrice());
                BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
                totalAmount = totalAmount.add(price.multiply(quantity));
            } catch (ServiceException e) {
                throw new ServiceException(e.getMessage(), e, ERROR_GIFT_NOT_FOUND, item.getGiftCertificate().getId());
            }
        }
        orderDto.setTotalAmount(totalAmount);
        orderDto.setOrderItems(orderItems);
        orderDto.setOrderTime(LocalDateTime.now());
        try {
            OrderForGiftCertificateEntity orderEntity = orderForGiftCertificateDtoToEntityConverting(orderDto);
            orderRepository.create(orderEntity);
            updateFieldsInDtoFromEntity(orderEntity, orderDto);
        } catch (IllegalArgumentException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return orderDto;
    }
}
