package com.epam.esm.repository.impl;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.repository.exception.RepositoryException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@ActiveProfiles("test")
@SpringBootTest
class OrderForGiftCertificateMySQLRepositoryTest {

    @Autowired
    private OrderForGiftCertificateMySQLRepository repository;

    @Test
    @Transactional
    void readAllOrdersTest() {
        List<OrderForGiftCertificateEntity> users;
        System.out.println("---------------RESULT-------------------");
        System.out.println("---------------RESULT-------------------");

    }

    @Test
    @Transactional
    void getUserOrdersTest() throws RepositoryException {
        List<OrderForGiftCertificateEntity> orders = repository.getUserOrders(3, getCriteria());
        System.out.println("---------------RESULT-------------------");
        orders.forEach(System.out::println);
        System.out.println("---------------RESULT-------------------");

    }

    private CriteriaEntity getCriteria(){
        CriteriaEntity cr = new CriteriaEntity();
        cr.setSize(10);
        cr.setPage(1);
        return cr;
    }
}