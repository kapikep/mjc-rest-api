package com.epam.esm.repository.interf;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;

import java.util.List;

public interface TagRepository {
    List<Tag> readAllTags() throws RepositoryException;

    Tag readTag(int id) throws RepositoryException;

    Tag readTagByName(String name) throws RepositoryException;

    int createTag(Tag tag) throws RepositoryException;

    void updateTag(Tag tag) throws RepositoryException;

    void deleteTag(int id) throws RepositoryException;
}
