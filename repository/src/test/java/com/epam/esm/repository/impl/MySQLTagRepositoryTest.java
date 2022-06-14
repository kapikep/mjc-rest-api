package com.epam.esm.repository.impl;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = RepositoryTestConfig.class)
//@SpringBootTest(classes = RepositoryTestConfig.class)
//@RunWith(SpringRunner.class)
//    @DataJpaTest
//    @AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class MySQLTagRepositoryTest {

    @Autowired
    private TagRepository repository;

    @Test
    void readAllTags() throws RepositoryException {
        List<TagEntity> tags;
        tags = repository.readAllTags();
        assertTrue(tags.size() > 0);
        tags.forEach(Assertions::assertNotNull);
        System.out.println("---------------RESULT-------------------");
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
        EmptyResultDataAccessException e = assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.readTagByName("abcd");
        });

        assertEquals(expectedTag, actualTag);
    }

    @Test
    void createTag() throws RepositoryException {
        TagEntity expectedTag = new TagEntity(0,"Tag1");
        int id = repository.createTag(expectedTag);
        System.out.println("tagId " + id);
        TagEntity actualTag = repository.readTagByName("Tag1");
        assertEquals(expectedTag.getName(), actualTag.getName());
        actualTag = repository.readTag(id);
        System.out.println(actualTag);
        assertEquals(expectedTag.getName(), actualTag.getName());
    }

    @Test
    void createExistsTag() throws RepositoryException {
        TagEntity expectedTag = new TagEntity(0,"Sport");
        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class, () -> repository.createTag(expectedTag));
    }

    @Test
    void updateTag() throws RepositoryException {
        TagEntity expectedTag = new TagEntity(7, "Tag2");
        repository.updateTag(expectedTag);
        TagEntity actualTag = repository.readTag(7);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void deleteNotLinkedTag() throws RepositoryException {
        TagEntity tag = new TagEntity(0, "Tag3");
        repository.createTag(tag);
        tag.setId(repository.readTagByName(tag.getName()).getId());
        repository.deleteTag(tag.getId());

        EmptyResultDataAccessException e = assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.readTagByName("Tag3");
        });

        assertEquals("No entity found for query; nested exception is javax.persistence.NoResultException: " +
                "No entity found for query", e.getMessage());
    }

    @Test
    void deleteLinkedTag() {
        assertDoesNotThrow(() -> repository.deleteTag(7));

        RepositoryException e = assertThrows(RepositoryException.class, () -> {
            repository.readTag(7);
        });

        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
    }

    @Test
    void deleteNotExistTag() {
        assertThrows(RepositoryException.class, () -> repository.deleteTag(111));
    }
}