package com.epam.esm.repository.impl;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import com.epam.esm.repository.mapper.TagMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
/**
 * MySQL repository for tags
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Repository
public class MySQLTagRepository implements TagRepository {
    public static final String INSERT = "INSERT INTO tag (name) VALUES(?)";
    public static final String SELECT = "SELECT * FROM tag";
    public static final String SELECT_FROM_TAG_WHERE_ID = "SELECT * FROM tag WHERE id=?";
    public static final String SELECT_FROM_TAG_WHERE_NAME = "SELECT * FROM tag WHERE name=?";
    public static final String UPDATE = "UPDATE tag SET name=? WHERE id=?";
    public static final String DELETE = "DELETE FROM tag WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    public MySQLTagRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Reads all tags from database
     *
     * @return list with all tagsEntities from database
     */
    @Override
    public List<TagEntity> readAllTags() throws RepositoryException {
        List<TagEntity> tags;
        try {
            tags = jdbcTemplate.query(SELECT, new TagMapper());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
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
        try {
            tag = jdbcTemplate.queryForObject(SELECT_FROM_TAG_WHERE_ID, new TagMapper(), id);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
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
        try {
            tag = jdbcTemplate.queryForObject(SELECT_FROM_TAG_WHERE_NAME, new TagMapper(), name);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return tag;
    }

    /**
     * Creates tag in database
     *
     * @param tag tagEntity to create in db
     * @return id for created tag
     */
    @Override
    public int createTag(TagEntity tag) throws RepositoryException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(INSERT, new String[]{"id"});
                ps.setString(1, tag.getName());
                return ps;
            }, keyHolder);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return keyHolder.getKey().intValue();
    }

    /**
     * Updates tag in database
     *
     * @param tag tag to create in db
     */
    @Override
    public void updateTag(TagEntity tag) throws RepositoryException {
        try {
            jdbcTemplate.update(UPDATE, tag.getName(), tag.getId());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    /**
     * Deletes tag in database
     *
     * @param id  id to create in db
     */
    @Override
    public void deleteTag(int id) throws RepositoryException {
        int res;
        try {
            res = jdbcTemplate.update(DELETE, id);
            if (res == 0){
                throw new RepositoryException("Resource to delete not found");
            }
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
