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
    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<T> clazz;

    protected final void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public List<T> readAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> entity = cq.from(clazz);
        TypedQuery<T> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    public T readById(final long id) throws RepositoryException {
        T entity;
        entity = entityManager.find(clazz, id);

        if (entity == null) {
            throw new RepositoryException("Incorrect result size: expected 1, actual 0");
        }
        return entity;
    }

    public List<T> readPaginated(CriteriaEntity cr) throws RepositoryException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> entity = cq.from(clazz);
        cq.select(entity);

        setSorting(cq, cb, entity, cr);
        TypedQuery<T> query = entityManager.createQuery(cq);
        setPagination(query, cr);

        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        countCq.select(cb.count(countCq.from(clazz)));
        cr.setTotalSize(entityManager.createQuery(countCq).getSingleResult());

        pageValidation(cr);

        return query.getResultList();
    }

    public void setPagination(TypedQuery<T> query, CriteriaEntity cr) throws RepositoryException {
        if(cr == null){
            throw new RepositoryException("Criteria must be not null");
        }
        if (cr.getSize() != null && cr.getPage() != null) {
            query.setFirstResult((cr.getPage() - 1) * cr.getSize());
            query.setMaxResults(cr.getSize());
        } else {
            throw new RepositoryException("Size and page must be not null");
        }
    }

    public void setSorting(CriteriaQuery<T> cq, CriteriaBuilder cb,
                           Root<T> entity, CriteriaEntity cr) throws RepositoryException {
        if(cr == null){
            throw new RepositoryException("Criteria must be not null");
        }
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
    }

    public void pageValidation(CriteriaEntity cr) throws RepositoryException {
        //TODO assert.notnull
        if(cr == null){
            throw new RepositoryException("Criteria must be not null");
        }
        if (cr.getSize() == null && cr.getSize() < 1) {
            throw new RepositoryException("Size must be not null");
        }
        if (cr.getPage() == null && cr.getPage() < 1) {
            throw new RepositoryException("Page must be not null");
        }
        if (cr.getTotalSize() == null) {
            throw new RepositoryException("Total size must be not null");
        }

        long totalPages = (long) Math.ceil(cr.getTotalSize() / (double) cr.getSize());

        if ((totalPages == 0 || totalPages == 1) && cr.getPage() > 1) {
            throw new RepositoryException("Page must be 1");
        }

        if (cr.getPage() > totalPages && totalPages > 1) {
            throw new RepositoryException("Page must be between 1 and " + totalPages);
        }
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
        final T entity = readById(entityId);
        delete(entity);
    }
}
