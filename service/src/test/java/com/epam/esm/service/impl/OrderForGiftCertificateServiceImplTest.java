package com.epam.esm.service.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.OrderForGiftCertificateDto;
import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.OrderForGiftCertificateRepository;
import com.epam.esm.service.dtoFactory.DtoFactory;
import com.epam.esm.service.entityFactory.EntityFactory;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.UserService;
import com.epam.esm.service.util.CriteriaUtil;
import com.epam.esm.service.util.OrderForGiftCertificateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getNewGiftCertificateDtoId1;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getNewGiftCertificateDtoId2;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getNewGiftCertificateDtoId3;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getNewGiftCertificateDtoId5;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getNewOrderForGiftCertificateDtoId4;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getNewOrderForGiftCertificateDtoList;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getNewOrderItemDtoId1;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getNewOrderItemDtoId3;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getNewOrderItemDtoId5;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getNewOrderItemDtoId6;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getNewOrderItemDtoId7;
import static com.epam.esm.service.dtoFactory.OrderAndItemDtoFactory.getOrderItemDtoId1;
import static com.epam.esm.service.dtoFactory.UserDtoFactory.getNewUserDtoId4;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getNewOrderForGiftCertificateEntityId1;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getNewOrderForGiftCertificateEntityId4;
import static com.epam.esm.service.entityFactory.OrderAndItemEntityFactory.getNewOrderForGiftCertificateEntityList;
import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.CriteriaUtil.setDefaultPageValIfEmpty;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.orderForGiftCertificateDtoToEntityConverting;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.orderForGiftCertificateEntityListToDtoConverting;
import static com.epam.esm.service.util.OrderForGiftCertificateUtil.updateFieldsInDtoFromEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class OrderForGiftCertificateServiceImplTest {
    private static final String ERROR_USER_NOT_FOUND = "error.user.not.found";
    private static final String ERROR_GIFT_NOT_FOUND = "error.gift.not.found";
    private static final String MESSAGE = "message";

    @Mock
    OrderForGiftCertificateRepository orderRepository;

    @Mock
    UserService userService;

    @Mock
    GiftCertificateServiceImpl giftService;

    @InjectMocks
    OrderForGiftCertificateServiceImpl orderService;

    @Test
    void getUserOrdersForGiftCertificatePaginatedTest() throws RepositoryException, ValidateException, ServiceException {
        CriteriaEntity crEntity = EntityFactory.getNewCriteriaEntityWithDefaultVal();
        CriteriaDto crDto = DtoFactory.getNewCriteriaDtoWithDefaultVal();
        List<OrderForGiftCertificateEntity> orderEntityList = getNewOrderForGiftCertificateEntityList();
        List<OrderForGiftCertificateDto> orderDtoList = getNewOrderForGiftCertificateDtoList();
        List<OrderForGiftCertificateDto> actualDtoList;
        long userId = 1;
        long totalSize = orderEntityList.size();

        try (MockedStatic<CriteriaUtil> crUtil = Mockito.mockStatic(CriteriaUtil.class);
             MockedStatic<OrderForGiftCertificateUtil> orderUtil = Mockito.mockStatic(OrderForGiftCertificateUtil.class)) {

            when(orderRepository.readUserOrdersPaginated(userId, crEntity)).thenReturn(orderEntityList);
            orderUtil.when(() -> orderForGiftCertificateEntityListToDtoConverting(orderEntityList))
                    .thenReturn(orderDtoList);
            crUtil.when(() -> criteriaDtoToEntityConverting(crDto)).thenReturn(crEntity);

            crEntity.setTotalSize(totalSize);
            actualDtoList = orderService.readUserOrdersForGiftCertificatePaginated(userId, crDto);

            verify(orderRepository).readUserOrdersPaginated(userId, crEntity);
            crUtil.verify(() -> criteriaDtoToEntityConverting(crDto));
            crUtil.verify(() -> setDefaultPageValIfEmpty(crDto));
            orderUtil.verify(() -> OrderForGiftCertificateUtil.orderForGiftCertificateSortingValidation(crDto));
            assertEquals(orderDtoList, actualDtoList);
            assertEquals(totalSize, crDto.getTotalSize());
        }
    }

    @Test
    void getUserOrdersForGiftCertificatePaginatedWithExceptionTest() throws RepositoryException, ServiceException {
        CriteriaEntity crEntity = EntityFactory.getNewCriteriaEntityWithDefaultVal();
        CriteriaDto crDto = DtoFactory.getNewCriteriaDtoWithDefaultVal();
        List<OrderForGiftCertificateEntity> orderEntityList = getNewOrderForGiftCertificateEntityList();
        List<OrderForGiftCertificateDto> orderDtoList = getNewOrderForGiftCertificateDtoList();
        long userId = 1;

        try (MockedStatic<CriteriaUtil> crUtil = Mockito.mockStatic(CriteriaUtil.class);
             MockedStatic<OrderForGiftCertificateUtil> orderUtil = Mockito.mockStatic(OrderForGiftCertificateUtil.class)) {

            when(orderRepository.readUserOrdersPaginated(userId, crEntity)).thenThrow(new RepositoryException(MESSAGE));
            orderUtil.when(() -> orderForGiftCertificateEntityListToDtoConverting(orderEntityList))
                    .thenReturn(orderDtoList);
            crUtil.when(() -> criteriaDtoToEntityConverting(crDto)).thenReturn(crEntity);

            ServiceException e = assertThrows(ServiceException.class,
                    () -> orderService.readUserOrdersForGiftCertificatePaginated(userId, crDto));

            assertEquals(MESSAGE, e.getMessage());

            when(userService.readUserById(userId)).thenThrow(new ServiceException());
            e = assertThrows(ServiceException.class,
                    () -> orderService.readUserOrdersForGiftCertificatePaginated(userId, crDto));

            assertEquals(ERROR_USER_NOT_FOUND, e.getResourceBundleCode());

        }
    }

    @Test
    void createOrderForGiftCertificateTotalAmountTest() throws ServiceException {
        BigDecimal expectedTotalAmount = new BigDecimal("340.0");
        OrderItemDto orderItemDtoId5 = getNewOrderItemDtoId5();
        OrderItemDto orderItemDtoId6 = getNewOrderItemDtoId6();
        List<OrderItemDto> orderItems = Stream.of(orderItemDtoId5, orderItemDtoId6)
                .collect(Collectors.toList());

        when(giftService.readGiftCertificateById(orderItemDtoId5.getGiftCertificate().getId()))
                .thenReturn(getNewGiftCertificateDtoId1());
        when(giftService.readGiftCertificateById(orderItemDtoId6.getGiftCertificate().getId()))
                .thenReturn(getNewGiftCertificateDtoId2());

        try (MockedStatic<OrderForGiftCertificateUtil> orderUtil = Mockito.mockStatic(OrderForGiftCertificateUtil.class)) {
            orderUtil.when(() -> orderForGiftCertificateDtoToEntityConverting(any())).thenReturn(getNewOrderForGiftCertificateEntityId1());
            OrderForGiftCertificateDto actualOrderDto = orderService.createOrderForGiftCertificate(anyLong(), orderItems);
            assertEquals(expectedTotalAmount, actualOrderDto.getTotalAmount());
        }
    }

    @Test
    void createOrderForGiftCertificateTotalAmount1Test() throws ServiceException {
        BigDecimal expectedTotalAmount = new BigDecimal("430.0");
        OrderItemDto orderItemDtoId1 = getNewOrderItemDtoId1();
        OrderItemDto orderItemDtoId3 = getNewOrderItemDtoId3();
        OrderItemDto orderItemDtoId6 = getNewOrderItemDtoId6();
        OrderItemDto orderItemDtoId7 = getNewOrderItemDtoId7();
        List<OrderItemDto> orderItems = Stream.of(orderItemDtoId1, orderItemDtoId3, orderItemDtoId6, orderItemDtoId7)
                .collect(Collectors.toList());

        when(giftService.readGiftCertificateById(orderItemDtoId1.getGiftCertificate().getId()))
                .thenReturn(getNewGiftCertificateDtoId1());
        when(giftService.readGiftCertificateById(orderItemDtoId3.getGiftCertificate().getId()))
                .thenReturn(getNewGiftCertificateDtoId5());
        when(giftService.readGiftCertificateById(orderItemDtoId6.getGiftCertificate().getId()))
                .thenReturn(getNewGiftCertificateDtoId2());
        when(giftService.readGiftCertificateById(orderItemDtoId7.getGiftCertificate().getId()))
                .thenReturn(getNewGiftCertificateDtoId3());

        try (MockedStatic<OrderForGiftCertificateUtil> orderUtil = Mockito.mockStatic(OrderForGiftCertificateUtil.class)) {
            orderUtil.when(() -> orderForGiftCertificateDtoToEntityConverting(any())).thenReturn(getNewOrderForGiftCertificateEntityId1());
            OrderForGiftCertificateDto actualOrderDto = orderService.createOrderForGiftCertificate(anyLong(), orderItems);
            assertEquals(expectedTotalAmount, actualOrderDto.getTotalAmount());
        }
    }

    @Test
    void createOrderForGiftCertificateOrderTimeTest() throws ServiceException {
        LocalDateTime time = LocalDateTime.now();

        try (MockedStatic<OrderForGiftCertificateUtil> orderUtil = Mockito.mockStatic(OrderForGiftCertificateUtil.class)) {
            OrderForGiftCertificateDto actualOrderDto = orderService.createOrderForGiftCertificate(anyLong(), new ArrayList<>());

            assertTrue((time.toEpochSecond(ZoneOffset.UTC) -
                    actualOrderDto.getOrderTime().toEpochSecond(ZoneOffset.UTC)) < 1);
        }
    }

    @Test
    void createOrderForGiftCertificateNotExistUserTest() throws ServiceException {
        long customerId = 5;
        when(userService.readUserById(anyLong())).thenThrow(ServiceException.class);

        ServiceException e = assertThrows(ServiceException.class, () -> orderService
                .createOrderForGiftCertificate(customerId, new ArrayList<>()));

        assertEquals(ERROR_USER_NOT_FOUND, e.getResourceBundleCode());
        assertEquals(customerId, e.getArgs()[0]);
    }

    @Test
    void createOrderForGiftCertificateNotExistGiftCertificateTest() throws ServiceException {
        OrderItemDto orderItemDto = getOrderItemDtoId1();
        long giftId = orderItemDto.getGiftCertificate().getId();
        when(giftService.readGiftCertificateById(anyLong())).thenThrow(ServiceException.class);

        ServiceException e = assertThrows(ServiceException.class, () -> orderService
                .createOrderForGiftCertificate(anyLong(), Collections.singletonList(orderItemDto)));

        assertEquals(ERROR_GIFT_NOT_FOUND, e.getResourceBundleCode());
        assertEquals(giftId, e.getArgs()[0]);
    }

    @Test
    void createOrderForGiftCertificateWithExceptionTest() {
        doThrow(new IllegalArgumentException(MESSAGE)).when(orderRepository).create(any());

        try (MockedStatic<OrderForGiftCertificateUtil> orderUtil = Mockito.mockStatic(OrderForGiftCertificateUtil.class)) {
            ServiceException e = assertThrows(ServiceException.class, () -> orderService
                    .createOrderForGiftCertificate(anyLong(), new ArrayList<>()));

            assertEquals(MESSAGE, e.getMessage());
        }
    }

    @Test
    void createOrderForGiftCertificateTest() throws ServiceException {
        LocalDateTime time = LocalDateTime.now();
        BigDecimal expectedTotalAmount = new BigDecimal("340.0");
        UserDto userDto = getNewUserDtoId4();
        long customerId = userDto.getId();
        OrderItemDto orderItemDtoId5 = getNewOrderItemDtoId5();
        OrderItemDto orderItemDtoId6 = getNewOrderItemDtoId6();
        List<OrderItemDto> orderItems = Stream.of(orderItemDtoId5, orderItemDtoId6)
                .collect(Collectors.toList());
        OrderForGiftCertificateEntity orderEntity = getNewOrderForGiftCertificateEntityId4();
        OrderForGiftCertificateDto orderDto = getNewOrderForGiftCertificateDtoId4();
        orderDto.setId(0);
        OrderForGiftCertificateDto actualOrderDto;


        when(userService.readUserById(customerId)).thenReturn(userDto);
        when(giftService.readGiftCertificateById(orderItemDtoId5.getGiftCertificate().getId()))
                .thenReturn(getNewGiftCertificateDtoId1());
        when(giftService.readGiftCertificateById(orderItemDtoId6.getGiftCertificate().getId()))
                .thenReturn(getNewGiftCertificateDtoId2());

        try (MockedStatic<OrderForGiftCertificateUtil> orderUtil = Mockito.mockStatic(OrderForGiftCertificateUtil.class)) {
            orderUtil.when(() -> orderForGiftCertificateDtoToEntityConverting(any())).thenReturn(orderEntity);
            actualOrderDto = orderService.createOrderForGiftCertificate(customerId, orderItems);

            verify(userService).readUserById(customerId);
            verify(giftService, times(2)).readGiftCertificateById(anyLong());
            orderUtil.verify(() -> orderForGiftCertificateDtoToEntityConverting(any()));
            orderUtil.verify(() -> updateFieldsInDtoFromEntity(any(), any()));
            verify(orderRepository).create(orderEntity);

            assertTrue((time.toEpochSecond(ZoneOffset.UTC) -
                    actualOrderDto.getOrderTime().toEpochSecond(ZoneOffset.UTC)) < 1);
            orderDto.setOrderTime(time);
            actualOrderDto.setOrderTime(time);
            assertEquals(orderDto, actualOrderDto);
            assertEquals(expectedTotalAmount, actualOrderDto.getTotalAmount());
            assertEquals(userDto, orderDto.getUser());
        }
    }
}