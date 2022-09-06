package com.epam.esm.repository.impl;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.repository.AbstractMySQLRepository;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.OrderForGiftCertificateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.epam.esm.repository.constant.Constant.ID;
import static com.epam.esm.repository.constant.Constant.USER;

/**
 * MySQL repository for OrderForGiftCertificateEntity
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Repository
public class OrderForGiftCertificateMySQLRepository extends AbstractMySQLRepository<OrderForGiftCertificateEntity>
        implements OrderForGiftCertificateRepository {

    public OrderForGiftCertificateMySQLRepository() {
        setClazz(OrderForGiftCertificateEntity.class);
    }

    /**
     * Read OrderForGiftCertificateEntities by userId paginated from db.
     *
     * @param userId user unique identifier for search.
     * @param cr     CriteriaEntity with params for pagination.
     * @return List with OrderForGiftCertificateEntities.
     * @throws RepositoryException      if page or size null or less 1.
     *                                  If the page is larger than the total size of the pages.
     * @exception IllegalArgumentException if the CriteriaEntity or criteria totalSize field is null.
     */
    @Override
    public List<OrderForGiftCertificateEntity> readUserOrdersPaginated(long userId, CriteriaEntity cr) throws RepositoryException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderForGiftCertificateEntity> cq = cb.createQuery(clazz);
        Root<OrderForGiftCertificateEntity> entity = cq.from(clazz);
        cq.select(entity);
        cq.where(cb.equal(entity.get(USER).get(ID), userId));

        setSorting(cq, cb, entity, cr);

        TypedQuery<OrderForGiftCertificateEntity> query = entityManager.createQuery(cq);

        setPagination(query, cr);

        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        countCq.select(cb.count(countCq.from(clazz)));
        countCq.where(cb.equal(entity.get(USER).get(ID), userId));
        cr.setTotalSize(entityManager.createQuery(countCq).getSingleResult());

        pageValidation(cr);

        return query.getResultList();
    }
}
