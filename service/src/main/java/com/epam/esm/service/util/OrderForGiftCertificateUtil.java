package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.constant.SearchParam.*;

public class OrderForGiftCertificateUtil {
    public static List<OrderForGiftCertificateDto> OrderForGiftCertificateEntityListToDtoConverting(List<OrderForGiftCertificateEntity> entities) throws ValidateException {
        if(entities == null){
            throw new ValidateException("orderForGiftCertificateEntity list is null");
        }
        List<OrderForGiftCertificateDto> dtoList = new ArrayList<>();
        for (OrderForGiftCertificateEntity entity : entities) {
            dtoList.add(orderForGiftCertificateEntityToDtoTransfer(entity));
        }
        return dtoList;
    }

    public static List<OrderForGiftCertificateEntity> OrderForGiftCertificateDtoListToEntityConverting(List<OrderForGiftCertificateDto> dtoList) throws ValidateException {
        if(dtoList == null){
            throw new ValidateException("orderForGiftCertificateDto list is null");
        }
        List<OrderForGiftCertificateEntity> entities = new ArrayList<>();
        for (OrderForGiftCertificateDto dto : dtoList) {
            entities.add(orderForGiftCertificateDtoToEntityTransfer(dto));
        }
        return entities;
    }

    public static OrderForGiftCertificateEntity orderForGiftCertificateDtoToEntityTransfer(OrderForGiftCertificateDto dto) throws ValidateException {
        if(dto == null){
            throw new ValidateException("orderForGiftCertificateDto is null");
        }
        OrderForGiftCertificateEntity entity = new OrderForGiftCertificateEntity();
        entity.setId(dto.getId());
        entity.setOrderTime(dto.getOrderTime());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setUser(UserUtil.userDtoToEntityTransfer(dto.getUser()));
        entity.setGifts(GiftCertificateUtil.giftCertificateDtoListToEntityConverting(dto.getGifts()));
        return entity;
    }

    public static OrderForGiftCertificateDto orderForGiftCertificateEntityToDtoTransfer(OrderForGiftCertificateEntity entity) throws ValidateException {
        OrderForGiftCertificateDto dto = new OrderForGiftCertificateDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    public static void updateFieldsInDtoFromEntity(OrderForGiftCertificateEntity entity,
                                                   OrderForGiftCertificateDto dto) throws ValidateException {
        if(dto == null){
            throw new ValidateException("orderForGiftCertificateDto is null");
        }
        if(entity == null){
            throw new ValidateException("orderForGiftCertificateEntity is null");
        }
        dto.setId(entity.getId());
        dto.setOrderTime(entity.getOrderTime());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setUser(UserUtil.userEntityToDtoTransfer(entity.getUser()));
        entity.getGifts().forEach(g -> g.setTags(new ArrayList<>()));
        dto.setGifts(GiftCertificateUtil.giftCertificateEntityListToDtoConverting(entity.getGifts()));
    }

    public static void sortingValidation(CriteriaDto crDto) throws ValidateException {
        CriteriaUtil.sortingValidation(crDto, ORDER_SORT_PARAM);
    }
}
