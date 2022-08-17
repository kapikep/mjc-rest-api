package com.epam.esm.service.dtoFactory;

import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.dto.OrderItemDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getGiftCertificateDtoId1;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getGiftCertificateDtoId2;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getGiftCertificateDtoId3;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getGiftCertificateDtoId4;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getGiftCertificateDtoId5;
import static com.epam.esm.service.dtoFactory.UserDtoFactory.getUserDtoId1;
import static com.epam.esm.service.dtoFactory.UserDtoFactory.getUserDtoId2;
import static com.epam.esm.service.dtoFactory.UserDtoFactory.getUserDtoId3;
import static com.epam.esm.service.dtoFactory.UserDtoFactory.getUserDtoId4;

public class OrderAndItemDtoFactory {
    private static final OrderForGiftCertificateDto orderForGiftCertificateId1 = getNewOrderForGiftCertificateDtoId1();
    private static final OrderForGiftCertificateDto orderForGiftCertificateId2 = getNewOrderForGiftCertificateDtoId2();
    private static final OrderForGiftCertificateDto orderForGiftCertificateId3 = getNewOrderForGiftCertificateDtoId3();
    private static final OrderForGiftCertificateDto orderForGiftCertificateId4 = getNewOrderForGiftCertificateDtoId4();
    private static final OrderForGiftCertificateDto orderForGiftCertificateId5 = getNewOrderForGiftCertificateDtoId5();
    private static final OrderForGiftCertificateDto orderForGiftCertificateId6 = getNewOrderForGiftCertificateDtoId6();
    private static final OrderForGiftCertificateDto orderForGiftCertificateId7 = getNewOrderForGiftCertificateDtoId7();

    private static final OrderItemDto orderItemId1 = getNewOrderItemDtoId1();
    private static final OrderItemDto orderItemId2 = getNewOrderItemDtoId2();
    private static final OrderItemDto orderItemId3 = getNewOrderItemDtoId3();
    private static final OrderItemDto orderItemId4 = getNewOrderItemDtoId4();
    private static final OrderItemDto orderItemId5 = getNewOrderItemDtoId5();
    private static final OrderItemDto orderItemId6 = getNewOrderItemDtoId6();
    private static final OrderItemDto orderItemId7 = getNewOrderItemDtoId7();

    public static OrderForGiftCertificateDto getNewOrderForGiftCertificateDtoId1() {
        return new OrderForGiftCertificateDto(1, LocalDateTime.parse("2022-05-27T04:43:55.000"),
                new BigDecimal("50.0"), getUserDtoId3(), Stream.of(getNewOrderItemDtoId1()).collect(Collectors.toList()));
    }

    public static OrderForGiftCertificateDto getNewOrderForGiftCertificateDtoId2() {
        return new OrderForGiftCertificateDto(2, LocalDateTime.parse("2022-06-27T04:43:55.000"),
                new BigDecimal("150.0"), getUserDtoId3(), Stream.of(getNewOrderItemDtoId2()).collect(Collectors.toList()));
    }

    public static OrderForGiftCertificateDto getNewOrderForGiftCertificateDtoId3() {
        return new OrderForGiftCertificateDto(3, LocalDateTime.parse("2022-07-27T04:43:55.000"),
                new BigDecimal("150.0"), getUserDtoId3(), Stream.of(getNewOrderItemDtoId3()).collect(Collectors.toList()));
    }

    public static OrderForGiftCertificateDto getNewOrderForGiftCertificateDtoId4() {
        return new OrderForGiftCertificateDto(4, LocalDateTime.parse("2022-06-27T04:43:55.000"),
                new BigDecimal("340.0"), getUserDtoId4(), Stream.of(getNewOrderItemDtoId5(), getNewOrderItemDtoId6()).collect(Collectors.toList()));
    }

    public static OrderForGiftCertificateDto getNewOrderForGiftCertificateDtoId5() {
        return new OrderForGiftCertificateDto(5, LocalDateTime.parse("2022-06-27T04:43:55.000"),
                new BigDecimal("70.0"), getUserDtoId4(), Stream.of(getNewOrderItemDtoId7()).collect(Collectors.toList()));
    }

    public static OrderForGiftCertificateDto getNewOrderForGiftCertificateDtoId6() {
        return new OrderForGiftCertificateDto(6, LocalDateTime.parse("2022-06-27T04:43:55.000"),
                new BigDecimal("100.0"), getUserDtoId2(), new ArrayList<>());
    }

    public static OrderForGiftCertificateDto getNewOrderForGiftCertificateDtoId7() {
        return new OrderForGiftCertificateDto(7, LocalDateTime.parse("2022-06-27T04:43:55.000"),
                new BigDecimal("30.0"), getUserDtoId1(), Stream.of(getNewOrderItemDtoId4()).collect(Collectors.toList()));
    }

    public static OrderItemDto getNewOrderItemDtoId1() {
        return new OrderItemDto(1, getGiftCertificateDtoId1(), 2);
    }

    public static OrderItemDto getNewOrderItemDtoId2() {
        return new OrderItemDto(2, getGiftCertificateDtoId4(), 1);
    }

    public static OrderItemDto getNewOrderItemDtoId3() {
        return new OrderItemDto(3, getGiftCertificateDtoId5(), 2);
    }

    public static OrderItemDto getNewOrderItemDtoId4() {
        return new OrderItemDto(4, getGiftCertificateDtoId1(), 5);
    }

    public static OrderItemDto getNewOrderItemDtoId5() {
        return new OrderItemDto(5, getGiftCertificateDtoId1(), 2);
    }

    public static OrderItemDto getNewOrderItemDtoId6() {
        return new OrderItemDto(6, getGiftCertificateDtoId2(), 3);
    }

    public static OrderItemDto getNewOrderItemDtoId7() {
        return new OrderItemDto(7, getGiftCertificateDtoId3(), 1);
    }

    public static OrderForGiftCertificateDto getOrderForGiftCertificateDtoId1() {
        return orderForGiftCertificateId1;
    }

    public static OrderForGiftCertificateDto getOrderForGiftCertificateDtoId2() {
        return orderForGiftCertificateId2;
    }

    public static OrderForGiftCertificateDto getOrderForGiftCertificateDtoId3() {
        return orderForGiftCertificateId3;
    }

    public static OrderForGiftCertificateDto getOrderForGiftCertificateDtoId4() {
        return orderForGiftCertificateId4;
    }

    public static OrderForGiftCertificateDto getOrderForGiftCertificateDtoId5() {
        return orderForGiftCertificateId5;
    }

    public static OrderForGiftCertificateDto getOrderForGiftCertificateDtoId6() {
        return orderForGiftCertificateId6;
    }

    public static OrderForGiftCertificateDto getOrderForGiftCertificateDtoId7() {
        return orderForGiftCertificateId7;
    }

    public static OrderItemDto getOrderItemDtoId1() {
        return orderItemId1;
    }

    public static OrderItemDto getOrderItemDtoId2() {
        return orderItemId2;
    }

    public static OrderItemDto getOrderItemDtoId3() {
        return orderItemId3;
    }

    public static OrderItemDto getOrderItemDtoId4() {
        return orderItemId4;
    }

    public static OrderItemDto getOrderItemDtoId5() {
        return orderItemId5;
    }

    public static OrderItemDto getOrderItemDtoId6() {
        return orderItemId6;
    }

    public static OrderItemDto getOrderItemDtoId7() {
        return orderItemId7;
    }

    public static List<OrderItemDto> getOrderItemDtoList() {
        return Arrays.asList(orderItemId1, orderItemId2, orderItemId3, orderItemId4,
                orderItemId5, orderItemId6, orderItemId7);
    }

    public static List<OrderForGiftCertificateDto> getOrderForGiftCertificateDtoList() {
        return Arrays.asList(orderForGiftCertificateId1, orderForGiftCertificateId2, orderForGiftCertificateId3, orderForGiftCertificateId4,
                orderForGiftCertificateId5, orderForGiftCertificateId6, orderForGiftCertificateId7);
    }

    public static List<OrderForGiftCertificateDto> getNewOrderForGiftCertificateDtoList(){
        return Stream.of(getNewOrderForGiftCertificateDtoId1(), getNewOrderForGiftCertificateDtoId2(),
                getNewOrderForGiftCertificateDtoId3(), getNewOrderForGiftCertificateDtoId4(),
                getNewOrderForGiftCertificateDtoId5(), getNewOrderForGiftCertificateDtoId6(),
                getNewOrderForGiftCertificateDtoId7()).collect(Collectors.toList());
    }
}
