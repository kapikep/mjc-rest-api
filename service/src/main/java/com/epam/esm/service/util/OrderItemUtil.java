package com.epam.esm.service.util;

import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.entity.OrderItemEntity;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.constant.ExceptionMes.ORDER_FOR_GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.ORDER_ITEM_DTO_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.ORDER_ITEM_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.ORDER_ITEM_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.ORDER_ITEM_ENTITY_MUST_NOT_BE_NULL;
import static org.springframework.util.Assert.notNull;

/**
 * Utils for order item
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class OrderItemUtil {
    /**
     * Converting OrderItemEntity list to OrderItemDto list
     *
     * @param entities OrderItemEntity list
     * @return OrderItemDto list
     */
    public static List<OrderItemDto> orderItemEntityListToDtoConverting(List<OrderItemEntity> entities) {
        notNull(entities, ORDER_ITEM_ENTITY_LIST_MUST_NOT_BE_NULL);

        List<OrderItemDto> dtoList = new ArrayList<>();
        for (OrderItemEntity entity : entities) {
            dtoList.add(orderItemEntityToDtoConverting(entity));
        }
        return dtoList;
    }

    /**
     * Converting OrderItemDto list to OrderItemEntity list
     *
     * @param dtoList OrderItemDto list
     * @param order   OrderForGiftCertificateEntity to attach for OrderItemEntity
     * @return OrderItemEntity list
     */
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

    /**
     * Converting OrderItemDto to OrderItemEntity
     *
     * @param dto   OrderItemDto
     * @param order OrderForGiftCertificateEntity to attach for OrderItemEntity
     * @return new OrderItemEntity with fields values from OrderItemDto
     */
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

    /**
     * Converting OrderItemEntity to OrderItemDto
     *
     * @param entity OrderItemEntity
     * @return new OrderItemDto with fields values from OrderItemEntity
     */
    public static OrderItemDto orderItemEntityToDtoConverting(OrderItemEntity entity) {
        OrderItemDto dto = new OrderItemDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    /**
     * Update fields in OrderItemDto from OrderItemEntity
     *
     * @param entity OrderItemEntity
     * @param dto    OrderItemDto
     */
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
