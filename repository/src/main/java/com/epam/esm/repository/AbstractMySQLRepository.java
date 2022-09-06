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

import static com.epam.esm.repository.constant.Constant.MINUS;
import static com.epam.esm.repository.constant.Constant.PLUS;
import static com.epam.esm.repository.constant.Constant.SPACE;
import static com.epam.esm.repository.constant.ExceptionMes.CRITERIA_BUILDER_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.CRITERIA_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0;
import static com.epam.esm.repository.constant.ExceptionMes.PAGE_MUST_BE_1;
import static com.epam.esm.repository.constant.ExceptionMes.PAGE_MUST_BE_BETWEEN_1_AND;
import static com.epam.esm.repository.constant.ExceptionMes.PAGE_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.QUERY_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.ROOT_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.SIZE_MUST_NOT_BE_NULL;
import static com.epam.esm.repository.constant.ExceptionMes.TOTAL_SIZE_MUST_NOT_BE_NULL;
import static org.springframework.util.Assert.notNull;

/**
 * Abstract MySQL Repository.
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public abstract class AbstractMySQLRepository<T extends Serializable> {

    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<T> clazz;

    protected final void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    /**
     * Read all entities from db.
     *
     * @return list with all entities from db.
     */
    public List<T> readAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> entity = cq.from(clazz);
        TypedQuery<T> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    /**
     * Read entity by id from db.
     *
     * @param id unique identifier of the entity to search.
     * @return entity by id.
     * @throws RepositoryException if entity with id does not exist.
     */
    public T readById(final long id) throws RepositoryException {
        T entity;
        entity = entityManager.find(clazz, id);

        if (entity == null) {
            throw new RepositoryException(INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0);
        }
        return entity;
    }

    /**
     * Read entities paginated from db.
     *
     * @param cr CriteriaEntity with params for pagination.
     * @return list with entities.
     * @throws RepositoryException      if page or size null or less 1.
     *                                  If the page is larger than the total size of the pages.
     * @exception IllegalArgumentException if the CriteriaEntity or criteria totalSize field is null.
     */
    public List<T> readPaginated(CriteriaEntity cr) throws RepositoryException {
        notNull(cr, CRITERIA_ENTITY_MUST_NOT_BE_NULL);

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

    /**
     * Create new entity in db. Update id in entity.
     *
     * @param entity entity instance to create in db.
     * @throws IllegalArgumentException if instance is not an entity
     */
    @Transactional
    public void create(final T entity) {
        entityManager.persist(entity);
    }

    /**
     * Merge entity in db.
     *
     * @param entity entity instance to merge in db.
     * @return the managed instance that the state was merged to.
     * @throws IllegalArgumentException if instance is not an entity or is a removed entity.
     */
    @Transactional
    public T merge(final T entity) {
        return entityManager.merge(entity);
    }

    /**
     * Delete entity in db.
     *
     * @param entity entity instance to delete from db.
     * @throws IllegalArgumentException if the instance is not an
     *                                  entity or is a detached entity
     */
    @Transactional
    public void delete(final T entity) {
        entityManager.remove(entity);
    }

    /**
     * Delete entity by id in db
     *
     * @param id unique identifier to delete from db.
     * @throws IllegalArgumentException if the instance is not an
     *                                  entity or is a detached entity
     */
    @Transactional
    public void deleteById(final long id) throws RepositoryException {
        final T entity = readById(id);
        delete(entity);
    }

    /**
     * Add pagination for query from CriteriaEntity page and size fields.
     *
     * @param query to add pagination.
     * @param cr    CriteriaEntity with param for pagination.
     */
    public void setPagination(TypedQuery<T> query, CriteriaEntity cr) {
        notNull(query, QUERY_MUST_NOT_BE_NULL);
        notNull(cr, CRITERIA_ENTITY_MUST_NOT_BE_NULL);
        notNull(cr.getPage(), PAGE_MUST_NOT_BE_NULL);
        notNull(cr.getSize(), SIZE_MUST_NOT_BE_NULL);

        query.setFirstResult((cr.getPage() - 1) * cr.getSize());
        query.setMaxResults(cr.getSize());

    }

    /**
     * Add sorting for query from CriteriaEntity page and size fields.
     *
     * @param cq     CriteriaQuery to add sorting.
     * @param cb     CriteriaBuilder.
     * @param entity Root<T> to get params.
     * @param cr     CriteriaEntity to get the sorting param.
     */
    public void setSorting(CriteriaQuery<T> cq, CriteriaBuilder cb,
                           Root<T> entity, CriteriaEntity cr) {
        notNull(cq, QUERY_MUST_NOT_BE_NULL);
        notNull(cb, CRITERIA_BUILDER_MUST_NOT_BE_NULL);
        notNull(entity, ROOT_ENTITY_MUST_NOT_BE_NULL);
        notNull(cr, CRITERIA_ENTITY_MUST_NOT_BE_NULL);

        String sorting = cr.getSorting();

        if (sorting != null) {
            if (sorting.startsWith(MINUS)) {
                sorting = sorting.substring(1);
                cq.orderBy(cb.desc(entity.get(sorting)));
            } else {
                if (sorting.startsWith(PLUS) || sorting.startsWith(SPACE)) {
                    sorting = sorting.substring(1);
                }
                cq.orderBy(cb.asc(entity.get(sorting)));
            }
        }
    }

    /**
     * Validate criteria size, page, totalSize fields.
     *
     * @param cr CriteriaEntity for validation.
     * @throws RepositoryException      if page or size null or less 1.
     *                                  If the page is larger than the total size of the pages.
     * @exception IllegalArgumentException if the CriteriaEntity or criteria totalSize field is null.
     */
    public void pageValidation(CriteriaEntity cr) throws RepositoryException {
        notNull(cr, CRITERIA_ENTITY_MUST_NOT_BE_NULL);
        notNull(cr.getTotalSize(), TOTAL_SIZE_MUST_NOT_BE_NULL);

        if (cr.getSize() == null && cr.getSize() < 1) {
            throw new RepositoryException(SIZE_MUST_NOT_BE_NULL);
        }
        if (cr.getPage() == null && cr.getPage() < 1) {
            throw new RepositoryException(PAGE_MUST_NOT_BE_NULL);
        }

        long totalPages = (long) Math.ceil(cr.getTotalSize() / (double) cr.getSize());

        if ((totalPages == 0 || totalPages == 1) && cr.getPage() > 1) {
            throw new RepositoryException(PAGE_MUST_BE_1);
        }
        if (cr.getPage() > totalPages && totalPages > 1) {
            throw new RepositoryException(PAGE_MUST_BE_BETWEEN_1_AND + totalPages);
        }
    }
}
