package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.service.exception.ValidateException;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.constant.SearchParam.ORDER_SORT_PARAM;
import static com.epam.esm.repository.constant.ExceptionMes.ORDER_FOR_GIFT_CERTIFICATE_DTO_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.ORDER_FOR_GIFT_CERTIFICATE_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.ORDER_FOR_GIFT_CERTIFICATE_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.ORDER_FOR_GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL;
import static org.springframework.util.Assert.notNull;

/**
 * Utils for order for giftCertificate
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class OrderForGiftCertificateUtil {
    /**
     * Converting OrderForGiftCertificateEntity list to OrderForGiftCertificateDto list
     */
    public static List<OrderForGiftCertificateDto> orderForGiftCertificateEntityListToDtoConverting(List<OrderForGiftCertificateEntity> entities) {
        notNull(entities, ORDER_FOR_GIFT_CERTIFICATE_ENTITY_LIST_MUST_NOT_BE_NULL);

        List<OrderForGiftCertificateDto> dtoList = new ArrayList<>();
        for (OrderForGiftCertificateEntity entity : entities) {
            dtoList.add(orderForGiftCertificateEntityToDtoConverting(entity));
        }
        return dtoList;
    }

    /**
     * Converting OrderForGiftCertificateDto list to OrderForGiftCertificateEntity list
     */
    public static List<OrderForGiftCertificateEntity> orderForGiftCertificateDtoListToEntityConverting(List<OrderForGiftCertificateDto> dtoList) {
        notNull(dtoList, ORDER_FOR_GIFT_CERTIFICATE_DTO_LIST_MUST_NOT_BE_NULL);

        List<OrderForGiftCertificateEntity> entities = new ArrayList<>();
        for (OrderForGiftCertificateDto dto : dtoList) {
            entities.add(orderForGiftCertificateDtoToEntityConverting(dto));
        }
        return entities;
    }

    /**
     * Converting OrderForGiftCertificateDto to OrderForGiftCertificateEntity
     */
    public static OrderForGiftCertificateEntity orderForGiftCertificateDtoToEntityConverting(OrderForGiftCertificateDto dto) {
        notNull(dto, ORDER_FOR_GIFT_CERTIFICATE_DTO_MUST_NOT_BE_NULL);

        OrderForGiftCertificateEntity entity = new OrderForGiftCertificateEntity();
        entity.setId(dto.getId());
        entity.setOrderTime(dto.getOrderTime());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setUser(UserUtil.userDtoToEntityConverting(dto.getUser()));
        entity.setOrderItems(OrderItemUtil.orderItemDtoListToEntityConverting(dto.getOrderItems(), entity));
        return entity;
    }

    /**
     * Converting OrderForGiftCertificateEntity to OrderForGiftCertificateDto
     */
    public static OrderForGiftCertificateDto orderForGiftCertificateEntityToDtoConverting(OrderForGiftCertificateEntity entity) {
        OrderForGiftCertificateDto dto = new OrderForGiftCertificateDto();
        updateFieldsInDtoFromEntity(entity, dto);
        return dto;
    }

    /**
     * Update fields in OrderForGiftCertificateDto from OrderForGiftCertificateEntity
     */
    public static void updateFieldsInDtoFromEntity(OrderForGiftCertificateEntity entity,
                                                   OrderForGiftCertificateDto dto) {
        notNull(dto, ORDER_FOR_GIFT_CERTIFICATE_DTO_MUST_NOT_BE_NULL);
        notNull(entity, ORDER_FOR_GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL);

        dto.setId(entity.getId());
        dto.setOrderTime(entity.getOrderTime());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setUser(UserUtil.userEntityToDtoConverting(entity.getUser()));
        dto.setOrderItems(OrderItemUtil.orderItemEntityListToDtoConverting(entity.getOrderItems()));
    }

    /**
     * Validate CriteriaDto sorting field for OrderForGiftCertificateEntity
     *
     * @param crDto CriteriaDto
     * @throws ValidateException if sorting field does not match ORDER_SORT_PARAM
     */
    public static void orderForGiftCertificateSortingValidation(CriteriaDto crDto) throws ValidateException {
        CriteriaUtil.sortingValidation(crDto, ORDER_SORT_PARAM);
    }
}
