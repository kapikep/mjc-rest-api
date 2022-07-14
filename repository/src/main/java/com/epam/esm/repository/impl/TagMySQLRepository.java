package com.epam.esm.repository.impl;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.AbstractMySQLRepository;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * MySQL repository for tags
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Repository
public class TagMySQLRepository extends AbstractMySQLRepository<TagEntity> implements TagRepository{

    private static final String WHERE_T_NAME = "select object (t) from TagEntity t where t.name = :name";

    public TagMySQLRepository() {
        setClazz(TagEntity.class);
    }

    @Override
    public TagEntity readByName(String name){
        TypedQuery<TagEntity> query = entityManager.createQuery(WHERE_T_NAME, TagEntity.class);
        query.setParameter("name", name);

        return query.getSingleResult();
    }
}
