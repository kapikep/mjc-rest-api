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
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.sortingValidation;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.updateFieldsInDtoFromEntity;

@Service
@RequiredArgsConstructor
public class OrderForGiftCertificateServiceImpl implements OrderForGiftCertificateService {
    public static final String ERROR_USER_NOT_FOUND = "error.user.not.found";
    public static final String ERROR_GIFT_NOT_FOUND = "error.gift.not.found";
    private final OrderForGiftCertificateRepository orderRepository;
    private final UserService userService;
    private final GiftCertificateService giftService;

    @Override
    public List<OrderForGiftCertificateDto> readUserOrdersForGiftCertificatePaginated(long userId, CriteriaDto crDto)
            throws ValidateException, ServiceException {
        try {
            userService.readUserById(userId);
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage(), e, ERROR_USER_NOT_FOUND, userId);
        }
        setDefaultPageValIfEmpty(crDto);
        sortingValidation(crDto);
        CriteriaEntity cr = criteriaDtoToEntityConverting(crDto);
        List<OrderForGiftCertificateDto> orders;
        try {
            orders = OrderForGiftCertificateUtil
                    .orderForGiftCertificateEntityListToDtoConverting(orderRepository.getUserOrdersPaginated(userId, cr));
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        crDto.setTotalSize(cr.getTotalSize());
        return orders;
    }

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
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return orderDto;
    }
}
