package com.epam.esm.service.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderForGiftCertificateDto;
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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.CriteriaUtil.setDefaultPageValIfEmpty;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.orderForGiftCertificateDtoToEntityTransfer;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.sortingValidation;
import static com.epam.esm.service.util.ServiceUtil.parseLong;

@Service
public class OrderForGiftCertificateServiceImpl implements OrderForGiftCertificateService {
    private final OrderForGiftCertificateRepository repository;
    private final UserService userService;
    private final GiftCertificateService giftService;

    public OrderForGiftCertificateServiceImpl(OrderForGiftCertificateRepository repository,
                                              UserService userService, GiftCertificateService giftService) {
        this.repository = repository;
        this.userService = userService;
        this.giftService = giftService;
    }

    @Override
    public List<OrderForGiftCertificateDto> getUserOrders(long userId, CriteriaDto crDto) throws ValidateException, ServiceException {
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
    public OrderForGiftCertificateDto create(long customerId, String orderIds) throws ValidateException, ServiceException {
        OrderForGiftCertificateDto orderDto = new OrderForGiftCertificateDto();
        List<GiftCertificateDto> gifts = new ArrayList<>();
        BigDecimal totalAmount = null;
        orderDto.setId(0);
        try{
            orderDto.setUser(userService.readOne(customerId));
        }catch (ServiceException e){
            throw new ServiceException(e.getMessage(), e, "error.user.not.found", customerId);
        }

        if(orderIds != null){
            if(orderIds.contains(",")){
                String ids [] = orderIds.split(",");
                totalAmount = new BigDecimal(0);
                for (int i = 0; i < ids.length; i++) {
                    GiftCertificateDto giftDto = giftService.readOne(parseLong(ids[i]));
                    totalAmount = totalAmount.add(BigDecimal.valueOf(giftDto.getPrice()));
                    gifts.add(giftDto);
                }
            }else {
                GiftCertificateDto giftDto = giftService.readOne(parseLong(orderIds));
                totalAmount = new BigDecimal(giftDto.getPrice());
                gifts.add(giftDto);
            }
        }
        orderDto.setTotalAmount(totalAmount);
        orderDto.setGifts(gifts);
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
