package com.epam.esm.repository.impl;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.AbstractMySQLRepository;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * MySQL repository for tags
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Repository
public class TagMySQLRepository extends AbstractMySQLRepository<TagEntity> implements TagRepository{

    public TagMySQLRepository() {
        super();
        setClazz(TagEntity.class);
    }

    @Override
    public TagEntity readByName(String name) throws RepositoryException {
        TagEntity tag;

        Query query = entityManager.createQuery("select object (t) from TagEntity t where t.name = :name");
        query.setParameter("name", name);
        tag = (TagEntity) query.getSingleResult();

//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

////        CriteriaQuery<TagEntity> query = cb.createQuery(TagEntity.class);
////        Root<TagEntity> root = query.from(TagEntity.class);
////        ParameterExpression<String> p = cb.parameter(String.class);
////        query.select(root).where(cb.)
////        tag = entityManager.find(TagEntity.class, name);
        return tag;
    }
}
