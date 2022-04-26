package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import com.epam.esm.repository.mapper.TagMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MySQLTagRepository implements TagRepository {
    private final JdbcTemplate jdbcTemplate;

    public MySQLTagRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> readAllTags() throws RepositoryException {
        List<Tag> tags;
        try {
            tags = jdbcTemplate.query("SELECT * FROM tag", new TagMapper());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return tags;
    }

    @Override
    public Tag readTag(int id) throws RepositoryException {
        Tag tag;
        try {
            tag = jdbcTemplate.queryForObject("SELECT * FROM tag WHERE id=?", new TagMapper(), id);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return tag;
    }

    @Override
    public Tag readTag(String name) throws RepositoryException {
        Tag tag;
        try {
            tag = jdbcTemplate.queryForObject("SELECT * FROM tag WHERE name=?", new TagMapper(), name);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return tag;
    }

    @Override
    public void createTag(Tag tag) throws RepositoryException {
        try {
            jdbcTemplate.update("INSERT INTO tag (name) VALUES(?)", tag.getName());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void updateTag(Tag tag) throws RepositoryException {
        try {
            jdbcTemplate.update("UPDATE tag SET name=? WHERE id=?", tag.getName(), tag.getId());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteTag(Tag tag) throws RepositoryException {
        try {
            jdbcTemplate.update("DELETE FROM tag WHERE id=?", tag.getId());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
