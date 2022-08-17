package com.epam.esm.service.util;

import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.entity.OrderItemEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.service.constant.ExceptionMes.ORDER_FOR_GIFT_CERTIFICATE_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.ORDER_FOR_GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.ORDER_ITEM_DTO_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.ORDER_ITEM_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.ORDER_ITEM_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.ORDER_ITEM_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.TAG_ENTITY_LIST_MUST_NOT_BE_NULL;
import static org.springframework.util.Assert.notNull;

public class OrderItemUtil {
    public static List<OrderItemDto> orderItemEntityListToDtoConverting(List<OrderItemEntity> entities) {
        notNull(entities, ORDER_ITEM_ENTITY_LIST_MUST_NOT_BE_NULL);

        List<OrderItemDto> dtoList = new ArrayList<>();
        for (OrderItemEntity entity : entities) {
            dtoList.add(orderItemEntityToDtoConverting(entity));
        }
        return dtoList;
    }

    public static List<OrderItemEntity> orderItemDtoListToEntityConverting(List<OrderItemDto> dtoList,
                                                                           OrderForGiftCertificateEntity order) {
        notNull(dtoList, ORDER_ITEM_DTO_LIST_MUST_NOT_BE_NULL);
        notNull(order, ORDER_FOR_GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL);

        List<OrderItemEntity> entities = new ArrayList<>();
        for (OrderItemDto dto : dtoList) {
            entities.add(orderItemDtoToEntityConverting(dto, order));
        }
        return entities;
    }

    public static OrderItemEntity orderItemDtoToEntityConverting(OrderItemDto dto,
                                                                 OrderForGiftCertificateEntity order) {
        notNull(dto, ORDER_ITEM_DTO_MUST_NOT_BE_NULL);
        notNull(order, ORDER_FOR_GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL);

        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(dto.getId());
        entity.setOrder(order);
        entity.setGiftCertificate(GiftCertificateUtil.giftCertificateDtoToEntityConverting(dto.getGiftCertificate()));
        entity.setQuantity(dto.getQuantity());
        return entity;
    }

    public static OrderItemDto orderItemEntityToDtoConverting(OrderItemEntity entity) {
        OrderItemDto dto = new OrderItemDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    public static void updateFieldsInDtoFromEntity(OrderItemEntity entity, OrderItemDto dto) {
        notNull(entity, ORDER_ITEM_ENTITY_MUST_NOT_BE_NULL);
        notNull(dto, ORDER_ITEM_DTO_MUST_NOT_BE_NULL);

        dto.setId(entity.getId());
        entity.getGiftCertificate().setTags(new ArrayList<>());
        dto.setGiftCertificate(GiftCertificateUtil.
                giftCertificateEntityToDtoConverting(entity.getGiftCertificate()));
        dto.setQuantity(entity.getQuantity());
    }
}
