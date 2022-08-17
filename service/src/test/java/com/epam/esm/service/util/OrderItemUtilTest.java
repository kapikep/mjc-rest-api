package com.epam.esm.service.util;

import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.entity.OrderItemEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.esm.service.constant.ExceptionMes.ORDER_FOR_GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.ORDER_ITEM_DTO_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.ORDER_ITEM_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.ORDER_ITEM_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.ORDER_ITEM_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getNewOrderItemDtoId1;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getNewOrderItemDtoId3;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getNewOrderItemDtoId5;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getNewOrderItemDtoId6;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getNewOrderForGiftCertificateEntityId1;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getNewOrderForGiftCertificateEntityId4;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getNewOrderItemEntityId1;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getOrderItemEntityId5;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getOrderItemEntityId6;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getOrderItemEntityList;
import static com.epam.esm.service.util.OrderItemUtil.orderItemDtoListToEntityConverting;
import static com.epam.esm.service.util.OrderItemUtil.orderItemDtoToEntityConverting;
import static com.epam.esm.service.util.OrderItemUtil.orderItemEntityListToDtoConverting;
import static com.epam.esm.service.util.OrderItemUtil.orderItemEntityToDtoConverting;
import static com.epam.esm.service.util.OrderItemUtil.updateFieldsInDtoFromEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderItemUtilTest {

    @Test
    void orderItemEntityListToDtoConvertingTest() {
        List<OrderItemDto> actualDto = orderItemEntityListToDtoConverting(getOrderItemEntityList());

        assertEquals(7, actualDto.size());
        actualDto.forEach(Assertions::assertNotNull);
    }

    @Test
    void orderItemEntityListToDtoConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> orderItemEntityListToDtoConverting(null));

        assertEquals(ORDER_ITEM_ENTITY_LIST_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void orderItemDtoListToEntityConvertingTest() {
        List<OrderItemDto> dtoList = Stream.of(getNewOrderItemDtoId5(),
                getNewOrderItemDtoId6()).collect(Collectors.toList());
        List<OrderItemEntity> actualEntityList = orderItemDtoListToEntityConverting(dtoList,
                getNewOrderForGiftCertificateEntityId4());

        assertEquals(2, actualEntityList.size());
        assertEquals(getOrderItemEntityId5(), actualEntityList.get(0));
        assertEquals(getOrderItemEntityId6(), actualEntityList.get(1));
    }

    @Test
    void orderItemDtoListToEntityConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> orderItemDtoListToEntityConverting(null, null));

        assertEquals(ORDER_ITEM_DTO_LIST_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void orderItemDtoToEntityConvertingTest() {
        OrderItemDto actualDto = getNewOrderItemDtoId1();
        OrderItemEntity expectedEntity = getNewOrderItemEntityId1();
        OrderItemDto expectedDto = getNewOrderItemDtoId1();

        OrderItemEntity actualEntity = orderItemDtoToEntityConverting(actualDto,
                getNewOrderForGiftCertificateEntityId1());

        assertEquals(expectedDto, actualDto);
        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    void orderItemDtoToEntityConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> orderItemDtoToEntityConverting(null, getNewOrderForGiftCertificateEntityId1()));

        assertEquals(ORDER_ITEM_DTO_MUST_NOT_BE_NULL, e.getMessage());

        e = assertThrows(IllegalArgumentException.class,
                () -> orderItemDtoToEntityConverting(getNewOrderItemDtoId3(), null));

        assertEquals(ORDER_FOR_GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void orderItemEntityToDtoConvertingTest() {
        OrderItemEntity actualEntity = getNewOrderItemEntityId1();
        OrderItemDto expectedDto = getNewOrderItemDtoId1();
        OrderItemEntity expectedEntity = getNewOrderItemEntityId1();

        OrderItemDto actualDto = orderItemEntityToDtoConverting(actualEntity);

        assertEquals(expectedDto, actualDto);
        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    void orderItemEntityToDtoConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> orderItemEntityToDtoConverting(null));

        assertEquals(ORDER_ITEM_ENTITY_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void updateFieldsInDtoFromEntityTest() {
        OrderItemEntity actualEntity = getNewOrderItemEntityId1();
        OrderItemDto actualDto = new OrderItemDto();
        OrderItemDto expectedDto = getNewOrderItemDtoId1();
        OrderItemEntity expectedEntity = getNewOrderItemEntityId1();

        updateFieldsInDtoFromEntity(actualEntity, actualDto);

        assertEquals(expectedDto, actualDto);
        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    void updateFieldsInDtoFromEntityNullTest() {
        OrderItemEntity actualEntity = getNewOrderItemEntityId1();
        OrderItemDto actualDto = new OrderItemDto();

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> updateFieldsInDtoFromEntity(null, actualDto));

        assertEquals(ORDER_ITEM_ENTITY_MUST_NOT_BE_NULL, e.getMessage());

        e = assertThrows(IllegalArgumentException.class,
                () -> updateFieldsInDtoFromEntity(actualEntity, null));

        assertEquals(ORDER_ITEM_DTO_MUST_NOT_BE_NULL, e.getMessage());
    }
}