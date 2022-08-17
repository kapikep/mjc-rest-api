package com.epam.esm.repository.interf;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;

public interface OrderForGiftCertificateRepository {
    List<OrderForGiftCertificateEntity> readPaginated(CriteriaEntity cr) throws RepositoryException;

    OrderForGiftCertificateEntity readById(long id) throws RepositoryException;

    void create(OrderForGiftCertificateEntity order) throws RepositoryException;

    OrderForGiftCertificateEntity merge(OrderForGiftCertificateEntity order) throws RepositoryException;

    void deleteById(long id) throws RepositoryException;

    List<OrderForGiftCertificateEntity> getUserOrdersPaginated(long userId, CriteriaEntity cr) throws RepositoryException;
}
