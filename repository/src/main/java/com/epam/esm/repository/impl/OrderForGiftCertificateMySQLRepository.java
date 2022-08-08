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

@Repository
public class OrderForGiftCertificateMySQLRepository extends AbstractMySQLRepository<OrderForGiftCertificateEntity>
        implements OrderForGiftCertificateRepository {

    public OrderForGiftCertificateMySQLRepository() {
        setClazz(OrderForGiftCertificateEntity.class);
    }

    @Override
    public List<OrderForGiftCertificateEntity> getUserOrders(long userId, CriteriaEntity cr) throws RepositoryException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderForGiftCertificateEntity> cq = cb.createQuery(clazz);
        Root<OrderForGiftCertificateEntity> entity = cq.from(clazz);
        cq.select(entity);
        cq.where(cb.equal(entity.get("user").get("id"), userId));

        String sorting = cr.getSorting();

        if (sorting != null) {
            if (sorting.startsWith("-")) {
                sorting = sorting.substring(1);
                cq.orderBy(cb.desc(entity.get(sorting)));
            } else {
                if (sorting.startsWith("+") || sorting.startsWith(" ")) {
                    sorting = sorting.substring(1);
                }
                cq.orderBy(cb.asc(entity.get(sorting)));
            }
        }

        TypedQuery<OrderForGiftCertificateEntity> query = entityManager.createQuery(cq);
        if(cr.getSize() != null && cr.getPage() != null) {
            query.setFirstResult((cr.getPage() - 1) * cr.getSize());
            query.setMaxResults(cr.getSize());
        }else {
            throw new RepositoryException("Size and page must be not null");
        }

        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        countCq.select(cb.count(countCq.from(clazz)));
        countCq.where(cb.equal(entity.get("user").get("id"), userId));
        cr.setTotalSize(entityManager.createQuery(countCq).getSingleResult());

        pageValidation(cr);

        return query.getResultList();
    }
}
