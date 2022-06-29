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
    private Class<T> clazz;

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

    public List<T> readPage(CriteriaEntity cr) {
//        Query query = entityManager.createQuery("from " + clazz.getName());
//        query.setFirstResult((cr.getPage() - 1) * cr.getLimit());
//        query.setMaxResults(cr.getLimit());

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> entity = cq.from(clazz);
        CriteriaQuery<T> select = cq.select(entity);

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
        query.setFirstResult((cr.getPage() - 1) * cr.getSize()); //-1
        query.setMaxResults(cr.getSize());
        cr.setTotalSize(getTotalSize());

        return query.getResultList();
    }

    public long getTotalSize() {
        return (long) entityManager.createQuery("select count (*) from " + clazz.getName())
                .getSingleResult();
    }

    @Transactional
    public void create(final T entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public T update(final T entity) {
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
