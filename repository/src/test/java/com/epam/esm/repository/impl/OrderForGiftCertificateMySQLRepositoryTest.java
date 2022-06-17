package com.epam.esm.repository.impl;

import com.epam.esm.entity.OrderForGiftCertificateEntity;
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
        users = repository.readAllUsers();
        System.out.println("---------------RESULT-------------------");
        users.forEach(System.out::println);
        System.out.println("---------------RESULT-------------------");

    }
}