package com.epam.esm.repository.impl;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.entity.OrderItemEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.OrderForGiftCertificateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.esm.repository.constant.ExceptionMes.INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0;
import static com.epam.esm.repository.constant.ExceptionMes.PAGE_MUST_BE_1;
import static com.epam.esm.repository.constant.ExceptionMes.PAGE_MUST_BE_BETWEEN_1_AND;
import static com.epam.esm.repository.impl.EntityFactory.EntityFactory.getNewCriteriaWithDefaultVal;
import static com.epam.esm.repository.impl.EntityFactory.GiftCertificateEntityFactory.getGiftCertificateEntityId1;
import static com.epam.esm.repository.impl.EntityFactory.OrderAndItemEntityFactory.getOrderForGiftCertificateEntityId1;
import static com.epam.esm.repository.impl.EntityFactory.OrderAndItemEntityFactory.getOrderForGiftCertificateEntityId2;
import static com.epam.esm.repository.impl.EntityFactory.OrderAndItemEntityFactory.getOrderForGiftCertificateEntityId3;
import static com.epam.esm.repository.impl.EntityFactory.OrderAndItemEntityFactory.getOrderForGiftCertificateEntityId4;
import static com.epam.esm.repository.impl.EntityFactory.OrderAndItemEntityFactory.getOrderForGiftCertificateEntityId5;
import static com.epam.esm.repository.impl.EntityFactory.OrderAndItemEntityFactory.getOrderForGiftCertificateEntityId6;
import static com.epam.esm.repository.impl.EntityFactory.OrderAndItemEntityFactory.getOrderForGiftCertificateEntityId7;
import static com.epam.esm.repository.impl.EntityFactory.UserEntityFactory.getUserEntityId1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@EnableJpaAuditing
class OrderForGiftCertificateMySQLRepositoryTest {
    @Autowired
    private OrderForGiftCertificateRepository orderRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    void readAllOrdersPaginatedTest() throws RepositoryException {
        List<OrderForGiftCertificateEntity> orders;
        CriteriaEntity cr = getNewCriteriaWithDefaultVal();
        orders = orderRepository.readPaginated(cr);

        orders.forEach(Assertions::assertNotNull);
        assertEquals(7, orders.size());
        assertEquals(getOrderForGiftCertificateEntityId1(), orders.get(0));
        assertEquals(getOrderForGiftCertificateEntityId2(), orders.get(1));
        assertEquals(getOrderForGiftCertificateEntityId3(), orders.get(2));
        assertEquals(getOrderForGiftCertificateEntityId4(), orders.get(3));
        assertEquals(getOrderForGiftCertificateEntityId5(), orders.get(4));
        assertEquals(getOrderForGiftCertificateEntityId6(), orders.get(5));
        assertEquals(getOrderForGiftCertificateEntityId7(), orders.get(6));

        cr.setSorting("-id");
        orders = orderRepository.readPaginated(cr);

        orders.forEach(Assertions::assertNotNull);
        assertEquals(7, orders.size());
        assertEquals(getOrderForGiftCertificateEntityId7(), orders.get(0));
        assertEquals(getOrderForGiftCertificateEntityId1(), orders.get(6));
    }

    @Test
    void readAllOrdersPaginatedWithExceptionTest() {
        CriteriaEntity cr = new CriteriaEntity(3, 5, "id");

        RepositoryException e = assertThrows(RepositoryException.class, () -> orderRepository.readPaginated(cr));
        assertEquals(PAGE_MUST_BE_BETWEEN_1_AND + 2, e.getMessage());

        cr.setPage(2);
        cr.setSize(20);

        e = assertThrows(RepositoryException.class, () -> orderRepository.readPaginated(cr));
        assertEquals(PAGE_MUST_BE_1, e.getMessage());
    }

    @Test
    void readOrderByIdTest() throws RepositoryException {
        OrderForGiftCertificateEntity expectedOrder = getOrderForGiftCertificateEntityId7();
        OrderForGiftCertificateEntity actualOrder = orderRepository.readById(7);

        assertEquals(expectedOrder, actualOrder);
        RepositoryException e = assertThrows(RepositoryException.class, () -> orderRepository.readById(11));
        assertEquals(INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0, e.getMessage());
    }

    @Test
    void createOrderTest() throws RepositoryException {
        OrderItemEntity orderItem = new OrderItemEntity(0, getGiftCertificateEntityId1(), null, 1);
        OrderForGiftCertificateEntity expectedOrder = new OrderForGiftCertificateEntity(0, LocalDateTime.now(),
                new BigDecimal("50.0"), getUserEntityId1(), Stream.of(orderItem).collect(Collectors.toList()));
        orderItem.setOrder(expectedOrder);

        orderRepository.create(expectedOrder);

        OrderForGiftCertificateEntity actualOrder = orderRepository.readById(expectedOrder.getId());

        expectedOrder.setId(actualOrder.getId());
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void createOrderAuditingTest() throws RepositoryException {
        OrderItemEntity orderItem = new OrderItemEntity(0, getGiftCertificateEntityId1(), null, 1);
        OrderForGiftCertificateEntity expectedOrder = new OrderForGiftCertificateEntity(0, LocalDateTime.now(),
                new BigDecimal("50.0"), getUserEntityId1(), Stream.of(orderItem).collect(Collectors.toList()));
        orderItem.setOrder(expectedOrder);

        orderRepository.create(expectedOrder);
        LocalDateTime createDate = LocalDateTime.now();
        entityManager.flush();
        entityManager.clear();

        OrderForGiftCertificateEntity actualOrder = orderRepository.readById(expectedOrder.getId());

        assertTrue((createDate.toEpochSecond(ZoneOffset.UTC) -
                actualOrder.getCreateDate().toEpochSecond(ZoneOffset.UTC)) < 1);

        assertTrue((createDate.toEpochSecond(ZoneOffset.UTC) -
                actualOrder.getLastUpdateDate().toEpochSecond(ZoneOffset.UTC)) < 1);
    }

    @Test
    void updateOrderAuditingTest() throws RepositoryException {
        OrderForGiftCertificateEntity order = orderRepository.readById(3);
        OrderItemEntity orderItem = new OrderItemEntity(0, getGiftCertificateEntityId1(), order, 1);
        order.setOrderItems(Stream.of(orderItem).collect(Collectors.toList()));

        orderRepository.merge(order);
        LocalDateTime updateDate = LocalDateTime.now();
        entityManager.flush();
        entityManager.clear();
        OrderForGiftCertificateEntity actualOrder = orderRepository.readById(3);

        assertEquals(LocalDateTime.parse("2022-07-27T04:43:55"), actualOrder.getCreateDate());

        assertTrue((updateDate.toEpochSecond(ZoneOffset.UTC) -
                actualOrder.getLastUpdateDate().toEpochSecond(ZoneOffset.UTC)) < 1);
    }

    @Test
    void updateOrderTest() throws RepositoryException {
        OrderForGiftCertificateEntity order = orderRepository.readById(3);
        OrderItemEntity orderItem = new OrderItemEntity(0, getGiftCertificateEntityId1(), order, 1);
        order.setOrderItems(Stream.of(orderItem).collect(Collectors.toList()));

        orderRepository.merge(order);

        OrderForGiftCertificateEntity actualOrder = orderRepository.readById(3);

        assertEquals(order, actualOrder);
    }

    @Test
    void deleteExistingOrderTest() throws RepositoryException {
        orderRepository.deleteById(4);

        assertThrows(RepositoryException.class, () -> orderRepository.readById(4));
    }

    @Test
    void getUserOrdersTest() throws RepositoryException {
        CriteriaEntity cr = getNewCriteriaWithDefaultVal();
        List<OrderForGiftCertificateEntity> actualOrders = orderRepository.readUserOrdersPaginated(3, cr);

        List<OrderForGiftCertificateEntity> expectedOrders = Arrays.asList(getOrderForGiftCertificateEntityId1(),
                getOrderForGiftCertificateEntityId2(), getOrderForGiftCertificateEntityId3());

        assertEquals(expectedOrders, actualOrders);
    }
}