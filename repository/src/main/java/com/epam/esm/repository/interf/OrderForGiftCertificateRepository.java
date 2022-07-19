package com.epam.esm.repository.interf;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;

public interface OrderForGiftCertificateRepository {
    List<OrderForGiftCertificateEntity> readPage(CriteriaEntity cr) throws RepositoryException;

    OrderForGiftCertificateEntity readOne(long id) throws RepositoryException;

    void create(OrderForGiftCertificateEntity tag) throws RepositoryException;

    OrderForGiftCertificateEntity merge(OrderForGiftCertificateEntity tag) throws RepositoryException;

    void deleteById(long id) throws RepositoryException;

    List<OrderForGiftCertificateEntity> getUserOrders(long userId, CriteriaEntity cr) throws RepositoryException;
}
