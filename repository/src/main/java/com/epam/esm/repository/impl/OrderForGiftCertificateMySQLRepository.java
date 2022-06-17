package com.epam.esm.repository.impl;

import com.epam.esm.entity.OrderForGiftCertificateEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class OrderForGiftCertificateMySQLRepository {

    private EntityManager entityManager;

    public OrderForGiftCertificateMySQLRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<OrderForGiftCertificateEntity> readAllUsers(){
        List<OrderForGiftCertificateEntity> orders;

//        users = entityManager.createQuery("from user").getResultList();
        orders = entityManager.createQuery("select u from OrderForGiftCertificateEntity u").getResultList();

        return orders;
    }
}
