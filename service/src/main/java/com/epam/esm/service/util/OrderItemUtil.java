package com.epam.esm.service.util;

import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.entity.OrderItemEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;

public class OrderItemUtil {
    public static List<OrderItemDto> orderItemEntityListToDtoConverting(List<OrderItemEntity> entities) throws ValidateException {
        if(entities == null){
            throw new ValidateException("OrderItem entities list is null");
        }
        List<OrderItemDto> dtoList = new ArrayList<>();
        for (OrderItemEntity entity : entities) {
            dtoList.add(orderItemEntityToDtoTransfer(entity));
        }
        return dtoList;
    }

    public static List<OrderItemEntity> orderItemDtoListToEntityConverting(List<OrderItemDto> dtoList, OrderForGiftCertificateEntity order) throws ValidateException {
        if(dtoList == null){
            throw new ValidateException("OrderItem dto list is null");
        }
        List<OrderItemEntity> entities = new ArrayList<>();
        for (OrderItemDto dto : dtoList) {
            entities.add(orderItemDtoToEntityTransfer(dto, order));
        }
        return entities;
    }

    public static OrderItemEntity orderItemDtoToEntityTransfer(OrderItemDto dto, OrderForGiftCertificateEntity order) throws ValidateException {
        if(dto == null){
            throw new ValidateException("OrderItem is null");
        }
        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(dto.getId());
        entity.setOrder(order);
//        entity.setOrder(OrderForGiftCertificateUtil.orderForGiftCertificateDtoToEntityTransfer(dto.getOrder()));
        dto.getGiftCertificate().setTags(new ArrayList<>());
        entity.setGiftCertificate(GiftCertificateUtil.giftCertificateDtoToEntityTransfer(dto.getGiftCertificate()));
        entity.setQuantity(dto.getQuantity());
        return entity;
    }

    public static OrderItemDto orderItemEntityToDtoTransfer(OrderItemEntity entity) throws ValidateException {
        OrderItemDto dto = new OrderItemDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    public static void updateFieldsInDtoFromEntity(OrderItemEntity entity, OrderItemDto dto) throws ValidateException {
        if(dto == null){
            throw new ValidateException("OrderItemDto is null");
        }
        if(entity == null){
            throw new ValidateException("OrderItemEntity is null");
        }
        dto.setId(entity.getId());
//        dto.setOrder(OrderForGiftCertificateUtil.orderForGiftCertificateEntityToDtoTransfer(entity.getOrder()));
        entity.getGiftCertificate().setTags(new ArrayList<>());
        dto.setGiftCertificate(GiftCertificateUtil.giftCertificateEntityToDtoTransfer(entity.getGiftCertificate()));
        dto.setQuantity(entity.getQuantity());
    }
}
