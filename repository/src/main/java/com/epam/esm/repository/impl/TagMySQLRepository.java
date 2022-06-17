package com.epam.esm.repository.impl;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * MySQL repository for tags
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Repository
public class TagMySQLRepository implements TagRepository {
    public static final String INSERT = "INSERT INTO tag (name) VALUES(?)";
    public static final String SELECT = "SELECT * FROM tag";
    public static final String SELECT_FROM_TAG_WHERE_ID = "SELECT * FROM tag WHERE id=?";
    public static final String SELECT_FROM_TAG_WHERE_NAME = "SELECT * FROM tag WHERE name=?";
    public static final String UPDATE = "UPDATE tag SET name=? WHERE id=?";
    public static final String DELETE = "DELETE FROM tag WHERE id=?";

    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;

    public TagMySQLRepository(JdbcTemplate jdbcTemplate, EntityManager entityManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
    }

    /**
     * Reads all tags from database
     *
     * @return list with all tagsEntities from database
     */
    @Override
    @Transactional
    public List<TagEntity> readAllTags() throws RepositoryException {
        List<TagEntity> tags;
//        try {
//            tags = jdbcTemplate.query(SELECT, new TagMapper());
//        } catch (DataAccessException e) {
//            throw new RepositoryException(e.getMessage(), e);
//        }
        Query query = entityManager.createQuery("select t from TagEntity t");
        tags = query.getResultList();
        return tags;
    }

    /**
     * Reads tag by id from database
     *
     * @return tagEntity  from database
     */
    @Override
    public TagEntity readTag(int id) throws RepositoryException {
        TagEntity tag;
        tag = entityManager.find(TagEntity.class, id);

        if (tag == null) {
            throw new RepositoryException("Incorrect result size: expected 1, actual 0");
        }
//        try {
//            tag = jdbcTemplate.queryForObject(SELECT_FROM_TAG_WHERE_ID, new TagMapper(), id);
//        } catch (DataAccessException e) {
//            throw new RepositoryException(e.getMessage(), e);
//        }
        return tag;
    }

    /**
     * Reads tag by name from database
     *
     * @return tagEntity from database
     */
    @Override
    public TagEntity readTagByName(String name) throws RepositoryException {
        TagEntity tag;

        Query query = entityManager.createQuery("select object (t) from TagEntity t where t.name = :name");
        query.setParameter("name", name);
        tag = (TagEntity) query.getSingleResult();
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

//        CriteriaQuery<TagEntity> query = cb.createQuery(TagEntity.class);
//        Root<TagEntity> root = query.from(TagEntity.class);
//        ParameterExpression<String> p = cb.parameter(String.class);
//        query.select(root).where(cb.)
//        tag = entityManager.find(TagEntity.class, name);

//        try {
//            tag = jdbcTemplate.queryForObject(SELECT_FROM_TAG_WHERE_NAME, new TagMapper(), name);
//        } catch (DataAccessException e) {
//            throw new RepositoryException(e.getMessage(), e);
//        }
        return tag;
    }

    /**
     * Creates tag in database
     *
     * @param tag tagEntity to create in db
     * @return id for created tag
     */
    @Override
    @Transactional
    public int createTag(TagEntity tag) throws RepositoryException {
        int result;
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        try {
//            jdbcTemplate.update(con -> {
//                PreparedStatement ps = con.prepareStatement(INSERT, new String[]{"id"});
//                ps.setString(1, tag.getName());
//                return ps;
//            }, keyHolder);
//        } catch (DataAccessException e) {
//            throw new RepositoryException(e.getMessage(), e);
//        }
//        result = keyHolder.getKey().intValue();
        try {
//            result = entityManager.merge(tag).getId();
            entityManager.persist(tag);
            entityManager.flush();
            result = tag.getId();
        } catch (DataIntegrityViolationException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Updates tag in database
     *
     * @param tag tag to create in db
     */
    @Override
    @Transactional
    public void updateTag(TagEntity tag) throws RepositoryException {
        int updatedRows;
        try {
//            updatedRows = jdbcTemplate.update(UPDATE, tag.getName(), tag.getId());
//
//            if (updatedRows == 0) {
//                throw new RepositoryException("0 updated rows");
//            }
            entityManager.merge(tag);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    /**
     * Deletes tag in database
     *
     * @param id id to create in db
     */
    @Override
    @Transactional
    public void deleteTag(int id) throws RepositoryException {
        int res;
        Query query = entityManager.createQuery("delete from TagEntity where id =:id");
        query.setParameter("id", id);
        res = query.executeUpdate();
        if (res == 0) {
            throw new RepositoryException("Resource to delete not found");
        }
//        try {
//            res = jdbcTemplate.update(DELETE, id);
//            if (res == 0) {
//                throw new RepositoryException("Resource to delete not found");
//            }
//        } catch (DataAccessException e) {
//            throw new RepositoryException(e.getMessage(), e);
//        }
    }
}
