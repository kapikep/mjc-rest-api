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
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.orderForGiftCertificateDtoToEntityTransfer;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.sortingValidation;

@Service
@RequiredArgsConstructor
public class OrderForGiftCertificateServiceImpl implements OrderForGiftCertificateService {
    private final OrderForGiftCertificateRepository repository;
    private final UserService userService;
    private final GiftCertificateService giftService;

    @Override
    public List<OrderForGiftCertificateDto> getUserOrders(long userId, CriteriaDto crDto) throws ValidateException, ServiceException {
        try {
            userService.readOne(userId);
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage(), e, "error.user.not.found", userId);
        }
        setDefaultPageValIfEmpty(crDto);
        sortingValidation(crDto);
        CriteriaEntity cr = criteriaDtoToEntityConverting(crDto);
        List<OrderForGiftCertificateDto> orders;
        try {
            orders = OrderForGiftCertificateUtil
                    .OrderForGiftCertificateEntityListToDtoConverting(repository.getUserOrders(userId, cr));
        } catch (RepositoryException | DataAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        crDto.setTotalSize(cr.getTotalSize());
        return orders;
    }

    @Override
    public OrderForGiftCertificateDto create(long customerId, List<OrderItemDto> orderItems) throws ValidateException, ServiceException {
        OrderForGiftCertificateDto orderDto = new OrderForGiftCertificateDto();
        BigDecimal totalAmount = new BigDecimal(0);
        orderDto.setId(0);
        try {
            orderDto.setUser(userService.readOne(customerId));
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage(), e, "error.user.not.found", customerId);
        }

        for (OrderItemDto item : orderItems) {
            GiftCertificateDto gift = giftService.readOne(item.getGiftCertificate().getId());
            item.setGiftCertificate(gift);
            for (int i = 0; i < item.getQuantity(); i++) {
                totalAmount = totalAmount.add(BigDecimal.valueOf(gift.getPrice()));
            }
        }
        orderDto.setTotalAmount(totalAmount);
        orderDto.setOrderItems(orderItems);
        orderDto.setOrderTime(LocalDateTime.now());
        try {
            OrderForGiftCertificateEntity entity = orderForGiftCertificateDtoToEntityTransfer(orderDto);
            repository.create(entity);
            OrderForGiftCertificateUtil.updateFieldsInDtoFromEntity(entity, orderDto);
        } catch (RepositoryException | DataAccessException e) {
            String mes = e.getMessage();
            throw new ServiceException(mes, e);
        }
        return orderDto;
    }
}
