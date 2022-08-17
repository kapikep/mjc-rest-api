package com.epam.esm.repository.impl.EntityFactory;

import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.entity.OrderItemEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.esm.repository.impl.EntityFactory.GiftCertificateEntityFactory.getGiftCertificateEntityId1;
import static com.epam.esm.repository.impl.EntityFactory.GiftCertificateEntityFactory.getGiftCertificateEntityId2;
import static com.epam.esm.repository.impl.EntityFactory.GiftCertificateEntityFactory.getGiftCertificateEntityId3;
import static com.epam.esm.repository.impl.EntityFactory.GiftCertificateEntityFactory.getGiftCertificateEntityId4;
import static com.epam.esm.repository.impl.EntityFactory.GiftCertificateEntityFactory.getGiftCertificateEntityId5;
import static com.epam.esm.repository.impl.EntityFactory.UserEntityFactory.getUserEntityId1;
import static com.epam.esm.repository.impl.EntityFactory.UserEntityFactory.getUserEntityId2;
import static com.epam.esm.repository.impl.EntityFactory.UserEntityFactory.getUserEntityId3;
import static com.epam.esm.repository.impl.EntityFactory.UserEntityFactory.getUserEntityId4;

public class OrderAndItemEntityFactory {
    private static final OrderForGiftCertificateEntity orderForGiftCertificateId1 = getNewOrderForGiftCertificateEntityId1();
    private static final OrderForGiftCertificateEntity orderForGiftCertificateId2 = getNewOrderForGiftCertificateEntityId2();
    private static final OrderForGiftCertificateEntity orderForGiftCertificateId3 = getNewOrderForGiftCertificateEntityId3();
    private static final OrderForGiftCertificateEntity orderForGiftCertificateId4 = getNewOrderForGiftCertificateEntityId4();
    private static final OrderForGiftCertificateEntity orderForGiftCertificateId5 = getNewOrderForGiftCertificateEntityId5();
    private static final OrderForGiftCertificateEntity orderForGiftCertificateId6 = getNewOrderForGiftCertificateEntityId6();
    private static final OrderForGiftCertificateEntity orderForGiftCertificateId7 = getNewOrderForGiftCertificateEntityId7();

    private static final OrderItemEntity orderItemId1 = getNewOrderItemEntityId1();
    private static final OrderItemEntity orderItemId2 = getNewOrderItemEntityId2();
    private static final OrderItemEntity orderItemId3 = getNewOrderItemEntityId3();
    private static final OrderItemEntity orderItemId4 = getNewOrderItemEntityId4();
    private static final OrderItemEntity orderItemId5 = getNewOrderItemEntityId5();
    private static final OrderItemEntity orderItemId6 = getNewOrderItemEntityId6();
    private static final OrderItemEntity orderItemId7 = getNewOrderItemEntityId7();

    public static OrderForGiftCertificateEntity getNewOrderForGiftCertificateEntityId1(){
        return new OrderForGiftCertificateEntity(1, LocalDateTime.parse("2022-05-27T04:43:55.000"),
                new BigDecimal("50.0"), getUserEntityId3(), Stream.of(getNewOrderItemEntityId1()).collect(Collectors.toList()));
    }

    public static OrderForGiftCertificateEntity getNewOrderForGiftCertificateEntityId2(){
        return new OrderForGiftCertificateEntity(2, LocalDateTime.parse("2022-06-27T04:43:55.000"),
                new BigDecimal("150.0"), getUserEntityId3(), Stream.of(getNewOrderItemEntityId2()).collect(Collectors.toList()));
    }

    public static OrderForGiftCertificateEntity getNewOrderForGiftCertificateEntityId3(){
        return new OrderForGiftCertificateEntity(3, LocalDateTime.parse("2022-07-27T04:43:55.000"),
                new BigDecimal("150.0"), getUserEntityId3(), Stream.of(getNewOrderItemEntityId3()).collect(Collectors.toList()));
    }

    public static OrderForGiftCertificateEntity getNewOrderForGiftCertificateEntityId4(){
        return new OrderForGiftCertificateEntity(4, LocalDateTime.parse("2022-06-27T04:43:55.000"),
                new BigDecimal("150.0"), getUserEntityId4(),
                Stream.of(getNewOrderItemEntityId5(), getNewOrderItemEntityId6()).collect(Collectors.toList()));
    }

    public static OrderForGiftCertificateEntity getNewOrderForGiftCertificateEntityId5(){
        return new OrderForGiftCertificateEntity(5, LocalDateTime.parse("2022-06-27T04:43:55.000"),
                new BigDecimal("70.0"), getUserEntityId4(), Stream.of(getNewOrderItemEntityId7()).collect(Collectors.toList()));
    }

    public static OrderForGiftCertificateEntity getNewOrderForGiftCertificateEntityId6(){
        return new OrderForGiftCertificateEntity(6, LocalDateTime.parse("2022-06-27T04:43:55.000"),
                new BigDecimal("100.0"), getUserEntityId2(), new ArrayList<>());
    }

    public static OrderForGiftCertificateEntity getNewOrderForGiftCertificateEntityId7(){
        return new OrderForGiftCertificateEntity(7, LocalDateTime.parse("2022-06-27T04:43:55.000"),
                new BigDecimal("30.0"), getUserEntityId1(), Stream.of(getNewOrderItemEntityId4()).collect(Collectors.toList()));
    }

    public static OrderItemEntity getNewOrderItemEntityId1(){
        return new OrderItemEntity(1, getGiftCertificateEntityId1(), orderForGiftCertificateId1, 2);
    }

    public static OrderItemEntity getNewOrderItemEntityId2(){
        return new OrderItemEntity(2, getGiftCertificateEntityId4(), orderForGiftCertificateId2, 1);
    }

    public static OrderItemEntity getNewOrderItemEntityId3(){
        return new OrderItemEntity(3, getGiftCertificateEntityId5(), orderForGiftCertificateId3, 2);
    }

    public static OrderItemEntity getNewOrderItemEntityId4(){
        return new OrderItemEntity(4, getGiftCertificateEntityId1(), orderForGiftCertificateId7, 5);
    }

    public static OrderItemEntity getNewOrderItemEntityId5(){
        return new OrderItemEntity(5, getGiftCertificateEntityId1(), orderForGiftCertificateId4, 2);
    }

    public static OrderItemEntity getNewOrderItemEntityId6(){
        return new OrderItemEntity(6, getGiftCertificateEntityId2(), orderForGiftCertificateId4, 3);
    }

    public static OrderItemEntity getNewOrderItemEntityId7(){
        return new OrderItemEntity(7, getGiftCertificateEntityId3(), orderForGiftCertificateId5, 1);
    }

    public static OrderForGiftCertificateEntity getOrderForGiftCertificateEntityId1(){
        return orderForGiftCertificateId1;
    }

    public static OrderForGiftCertificateEntity getOrderForGiftCertificateEntityId2(){
        return orderForGiftCertificateId2;
    }

    public static OrderForGiftCertificateEntity getOrderForGiftCertificateEntityId3(){
        return orderForGiftCertificateId3;
    }

    public static OrderForGiftCertificateEntity getOrderForGiftCertificateEntityId4(){
        return orderForGiftCertificateId4;
    }

    public static OrderForGiftCertificateEntity getOrderForGiftCertificateEntityId5(){
        return orderForGiftCertificateId5;
    }

    public static OrderForGiftCertificateEntity getOrderForGiftCertificateEntityId6(){
        return orderForGiftCertificateId6;
    }

    public static OrderForGiftCertificateEntity getOrderForGiftCertificateEntityId7(){
        return orderForGiftCertificateId7;
    }

    public static OrderItemEntity getOrderItemEntityId1(){
        return orderItemId1;
    }

    public static OrderItemEntity getOrderItemEntityId2(){
        return orderItemId2;
    }

    public static OrderItemEntity getOrderItemEntityId3(){
        return orderItemId3;
    }

    public static OrderItemEntity getOrderItemEntityId4(){
        return orderItemId4;
    }

    public static OrderItemEntity getOrderItemEntityId5(){
        return orderItemId5;
    }

    public static OrderItemEntity getOrderItemEntityId6(){
        return orderItemId6;
    }

    public static OrderItemEntity getOrderItemEntityId7(){
        return orderItemId7;
    }
}
