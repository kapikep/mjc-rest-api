package com.epam.esm.repository.impl;

import com.epam.esm.config.DevConfig;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DevConfig.class)
class MySQLTagRepositoryTest {
    @Autowired
    private TagRepository repository;

    @Test
    void readAllTags() throws RepositoryException {
        List<Tag>tags;
        tags = repository.readAllTags();
        assertEquals(7, tags.size());
        tags.forEach(Assertions::assertNotNull);
    }

    @Test
    void readTagById() throws RepositoryException {
        Tag expectedTag = new Tag(6, "Romantic");
        Tag actualTag = repository.readTag(6);
        RepositoryException e = assertThrows(RepositoryException.class, () -> {
            repository.readTag(-1);
        });

        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void readTagByName() throws RepositoryException {
        Tag actualTag;
        Tag expectedTag = new Tag(2, "Water");
        actualTag = repository.readTagByName("Water");
        RepositoryException e = assertThrows(RepositoryException.class, () -> {
            repository.readTagByName("abcd");
        });

        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void createTag() throws RepositoryException {
        Tag expectedTag = new Tag("Tag1");
        int id = repository.createTag(expectedTag);
        Tag actualTag = repository.readTagByName("Tag1");
        assertEquals(expectedTag.getName(), actualTag.getName());
        actualTag = repository.readTag(id);
        assertEquals(expectedTag.getName(), actualTag.getName());
    }

    @Test
    void updateTag() throws RepositoryException {
        Tag expectedTag = new Tag(7,"Tag2");
        repository.updateTag(expectedTag);
        Tag actualTag = repository.readTag(7);
        assertEquals(expectedTag, actualTag);

    }

    @Test
    void deleteNotLinkedTag() throws RepositoryException {
        Tag tag = new Tag("Tag3");
        repository.createTag(tag);
        tag.setId(repository.readTagByName(tag.getName()).getId());
        repository.deleteTag(tag.getId());

        RepositoryException e = assertThrows(RepositoryException.class, () -> {
            repository.readTagByName("Tag3");
        });

        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
    }

    @Test
    void deleteLinkedTag(){
        assertDoesNotThrow(() -> repository.deleteTag(7));

        RepositoryException e = assertThrows(RepositoryException.class, () -> {
            repository.readTag(7);
        });

        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
    }
}