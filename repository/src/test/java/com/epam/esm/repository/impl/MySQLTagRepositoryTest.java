package com.epam.esm.repository.impl;

import com.epam.esm.entity.TagEntity;
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

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RepositoryTestConfig.class)
class MySQLTagRepositoryTest {

    @Autowired
    private TagRepository repository;

    @Test
    void readAllTags() throws RepositoryException {
        List<TagEntity>tags;
        tags = repository.readAllTags();
        //assertEquals(7, tags.size());
        tags.forEach(Assertions::assertNotNull);
        tags.forEach(System.out::println);
    }

    @Test
    void readTagById() throws RepositoryException {
        TagEntity expectedTag = new TagEntity(6, "Romantic");
        TagEntity actualTag = repository.readTag(6);
        RepositoryException e = assertThrows(RepositoryException.class, () -> {
            repository.readTag(-1);
        });

        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void readTagByName() throws RepositoryException {
        TagEntity actualTag;
        TagEntity expectedTag = new TagEntity(2, "Water");
        actualTag = repository.readTagByName("Water");
        RepositoryException e = assertThrows(RepositoryException.class, () -> {
            repository.readTagByName("abcd");
        });

        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void createTag() throws RepositoryException {
        TagEntity expectedTag = new TagEntity("Tag1");
        int id = repository.createTag(expectedTag);
        TagEntity actualTag = repository.readTagByName("Tag1");
        assertEquals(expectedTag.getName(), actualTag.getName());
        actualTag = repository.readTag(id);
        assertEquals(expectedTag.getName(), actualTag.getName());
    }

    @Test
    void updateTag() throws RepositoryException {
        TagEntity expectedTag = new TagEntity(7,"Tag2");
        repository.updateTag(expectedTag);
        TagEntity actualTag = repository.readTag(7);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void deleteNotLinkedTag() throws RepositoryException {
        TagEntity tag = new TagEntity("Tag3");
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

    @Test
    void deleteNotExistTag(){
        assertThrows(RepositoryException.class, () -> repository.deleteTag(111));
    }
}