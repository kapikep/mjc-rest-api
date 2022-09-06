package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.service.exception.ValidateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.epam.esm.service.constant.ExceptionMes.ORDER_FOR_GIFT_CERTIFICATE_DTO_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.ORDER_FOR_GIFT_CERTIFICATE_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.ORDER_FOR_GIFT_CERTIFICATE_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.ORDER_FOR_GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getNewOrderForGiftCertificateDtoId4;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getOrderForGiftCertificateDtoId4;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getOrderForGiftCertificateDtoId5;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getOrderForGiftCertificateDtoList;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getNewOrderForGiftCertificateEntityId4;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getNewOrderForGiftCertificateEntityId5;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getOrderForGiftCertificateEntityId4;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getOrderForGiftCertificateEntityId5;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getOrderForGiftCertificateEntityList;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.orderForGiftCertificateDtoListToEntityConverting;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.orderForGiftCertificateDtoToEntityConverting;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.orderForGiftCertificateEntityListToDtoConverting;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.orderForGiftCertificateEntityToDtoConverting;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.orderForGiftCertificateSortingValidation;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.updateFieldsInDtoFromEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderForGiftCertificateUtilTest {

    @Test
    void orderForGiftCertificateEntityListToDtoConvertingTest() {
        List<OrderForGiftCertificateDto> actualDtoList =
                orderForGiftCertificateEntityListToDtoConverting(getOrderForGiftCertificateEntityList());

        assertEquals(getOrderForGiftCertificateDtoList(), actualDtoList);
    }

    @Test
    void orderForGiftCertificateEntityListToDtoConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> orderForGiftCertificateEntityListToDtoConverting(null));

        assertEquals(ORDER_FOR_GIFT_CERTIFICATE_ENTITY_LIST_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void orderForGiftCertificateDtoListToEntityConvertingTest() {
        List<OrderForGiftCertificateEntity> actualEntityList =
                orderForGiftCertificateDtoListToEntityConverting(getOrderForGiftCertificateDtoList());

        assertEquals(7, actualEntityList.size());
        actualEntityList.forEach(Assertions::assertNotNull);
    }

    @Test
    void orderForGiftCertificateDtoListToEntityConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> orderForGiftCertificateDtoListToEntityConverting(null));

        assertEquals(ORDER_FOR_GIFT_CERTIFICATE_DTO_LIST_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void orderForGiftCertificateDtoToEntityConvertingTest() {
        OrderForGiftCertificateDto actualDto = getNewOrderForGiftCertificateDtoId4();
        OrderForGiftCertificateEntity actualEntity = orderForGiftCertificateDtoToEntityConverting(actualDto);
        OrderForGiftCertificateEntity expectedEntity = getNewOrderForGiftCertificateEntityId4();
        expectedEntity.getUser().setLogin(null);
        expectedEntity.getUser().setPassword(null);

        assertEquals(expectedEntity, actualEntity);
        assertEquals(getOrderForGiftCertificateDtoId4(), actualDto);
    }

    @Test
    void orderForGiftCertificateDtoToEntityConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> orderForGiftCertificateDtoToEntityConverting(null));

        assertEquals(ORDER_FOR_GIFT_CERTIFICATE_DTO_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void orderForGiftCertificateEntityToDtoConvertingTest() {
        OrderForGiftCertificateEntity actualEntity = getNewOrderForGiftCertificateEntityId4();
        OrderForGiftCertificateDto actualDto = orderForGiftCertificateEntityToDtoConverting(actualEntity);

        assertEquals(getOrderForGiftCertificateDtoId4(), actualDto);
        assertEquals(getOrderForGiftCertificateEntityId4(), actualEntity);
    }

    @Test
    void orderForGiftCertificateEntityToDtoConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> orderForGiftCertificateEntityToDtoConverting(null));

        assertEquals(ORDER_FOR_GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void updateFieldsInDtoFromEntityTest() {
        OrderForGiftCertificateEntity actualEntity = getNewOrderForGiftCertificateEntityId5();
        OrderForGiftCertificateDto actualDto = new OrderForGiftCertificateDto();
        updateFieldsInDtoFromEntity(actualEntity, actualDto);

        assertEquals(getOrderForGiftCertificateDtoId5(), actualDto);
        assertEquals(getOrderForGiftCertificateEntityId5(), actualEntity);
    }

    @Test
    void orderForGiftCertificateSortingValidationTest() throws ValidateException {
        CriteriaDto cr = new CriteriaDto();
        cr.setSorting("id");
        orderForGiftCertificateSortingValidation(cr);

        cr.setSorting("-id");
        orderForGiftCertificateSortingValidation(cr);

        cr.setSorting("criteria");
        assertThrows(ValidateException.class,
                () -> orderForGiftCertificateSortingValidation(cr));
    }
}