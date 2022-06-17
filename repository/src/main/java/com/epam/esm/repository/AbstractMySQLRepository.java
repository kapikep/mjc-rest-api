package com.epam.esm.repository;

import com.epam.esm.repository.exception.RepositoryException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractMySQLRepository<T extends Serializable> {

    private Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    public final void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    @SuppressWarnings("unchecked")
    public List<T> readAll() {
        return entityManager.createQuery("from " + clazz.getName()).getResultList();
    }

    public T readOne(final long id) throws RepositoryException {
        T entity;
        entity = entityManager.find(clazz, id);

        if (entity == null) {
            throw new RepositoryException("Incorrect result size: expected 1, actual 0");
        }
        return entity;
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
