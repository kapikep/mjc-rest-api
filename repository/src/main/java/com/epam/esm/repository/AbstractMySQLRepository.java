package com.epam.esm.repository;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.repository.exception.RepositoryException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractMySQLRepository<T extends Serializable> {
    protected Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    public final void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public List<T> readAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> entity = cq.from(clazz);
        TypedQuery<T> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    public T readOne(final long id) throws RepositoryException {
        T entity;
        entity = entityManager.find(clazz, id);

        if (entity == null) {
            throw new RepositoryException("Incorrect result size: expected 1, actual 0");
        }
        return entity;
    }

    public List<T> readPage(CriteriaEntity cr) throws RepositoryException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> entity = cq.from(clazz);
        cq.select(entity);

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

        TypedQuery<T> query = entityManager.createQuery(cq);
        if(cr.getSize() != null && cr.getPage() != null) {
            query.setFirstResult((cr.getPage() - 1) * cr.getSize());
            query.setMaxResults(cr.getSize());
        }else {
            throw new RepositoryException("Size and page must be not null");
        }

        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        countCq.select(cb.count(countCq.from(clazz)));
        cr.setTotalSize(entityManager.createQuery(countCq).getSingleResult());

        return query.getResultList();
    }

    @Transactional
    public void create(final T entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public T merge(final T entity) {
        return entityManager.merge(entity);
    }

    @Transactional
    public void delete(final T entity) {
        entityManager.remove(entity);
    }

    @Transactional
    public void deleteById(final long entityId) throws RepositoryException {
        final T entity = readOne(entityId);
        delete(entity);
    }
}
