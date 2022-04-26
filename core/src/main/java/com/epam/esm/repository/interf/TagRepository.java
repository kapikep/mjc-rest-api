package com.epam.esm.repository.interf;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;

public interface TagRepository {
    List<Tag> readAllTags() throws RepositoryException;

    Tag readTag(int id) throws RepositoryException;

    Tag readTag(String name) throws RepositoryException;

    void createTag(Tag tag) throws RepositoryException;

    void updateTag(Tag tag) throws RepositoryException;

    void deleteTag(Tag tag) throws RepositoryException;
}
