package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
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

    @Override
    public List<Tag> readAllTags() throws RepositoryException {
        List<Tag> tags;
        try {
            tags = jdbcTemplate.query(SELECT, new TagMapper());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return tags;
    }

    @Override
    public Tag readTag(int id) throws RepositoryException {
        Tag tag;
        try {
            tag = jdbcTemplate.queryForObject(SELECT_FROM_TAG_WHERE_ID, new TagMapper(), id);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return tag;
    }

    @Override
    public Tag readTagByName(String name) throws RepositoryException {
        Tag tag;
        try {
            tag = jdbcTemplate.queryForObject(SELECT_FROM_TAG_WHERE_NAME, new TagMapper(), name);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return tag;
    }

    @Override
    public int createTag(Tag tag) throws RepositoryException {
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

    @Override
    public void updateTag(Tag tag) throws RepositoryException {
        try {
            jdbcTemplate.update(UPDATE, tag.getName(), tag.getId());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteTag(int id) throws RepositoryException {
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
