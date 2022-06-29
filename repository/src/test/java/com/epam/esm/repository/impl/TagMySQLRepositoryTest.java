package com.epam.esm.repository.impl;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@ActiveProfiles("test")
@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = RepositoryTestConfig.class)

//@RunWith(SpringRunner.class)
//@DataJpaTest

//    @AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class TagMySQLRepositoryTest {

    @Autowired
    private TagRepository repository;

    @Test
//    @Transactional
    void readAllTags() throws RepositoryException {
        List<TagEntity> tags;
        tags = repository.readAll();
        assertTrue(tags.size() > 0);
        tags.forEach(Assertions::assertNotNull);
        System.out.println("---------------RESULT-------------------");
        tags.forEach(System.out::println);
        System.out.println(tags.size());
    }

    @Test
    void getCount() {
        long size;
        size = repository.getTotalSize();
        System.out.println(size);
    }

    @Test
    void readPage() throws RepositoryException {
        List<TagEntity> tags;

        CriteriaEntity cr = new CriteriaEntity();
        cr.setPage(1);
        cr.setSize(20);
        cr.setSorting("-id");

        tags = repository.readPage(cr);

        tags.forEach(System.out::println);
        System.out.println(tags.size());
    }

//    @Test
//    void put1000tags() throws RepositoryException {
//
//        for (int i = 0; i < 1000; i++) {
//            try {
//                repository.create(new TagEntity(0, "Tag" + i));
//            }catch (Exception e){
//                System.out.println(e);
//            }
//        }
//    }

    @Test
//    @Transactional
    void readTagById() throws RepositoryException {
        TagEntity expectedTag = new TagEntity(6, "Romantic");
        TagEntity actualTag = repository.readOne(6);
        RepositoryException e = assertThrows(RepositoryException.class, () -> {
            repository.readOne(-1);
        });

        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
        assertEquals(expectedTag, actualTag);
    }

    @Test
//    @Transactional
    void readTagByName() throws RepositoryException {
        TagEntity actualTag;
        TagEntity expectedTag = new TagEntity(2, "Water");
        actualTag = repository.readByName("Water");
        EmptyResultDataAccessException e = assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.readByName("abcd");
        });

        assertEquals(expectedTag, actualTag);
    }

    @Test
    @Transactional
    void createTag() throws RepositoryException {
        TagEntity expectedTag = new TagEntity(0, "Tag1");
        repository.create(expectedTag);
        long id = expectedTag.getId();
        System.out.println("tagId " + id);
        TagEntity actualTag = repository.readByName("Tag1");
        assertEquals(expectedTag.getName(), actualTag.getName());
        actualTag = repository.readOne(id);
        assertEquals(expectedTag.getName(), actualTag.getName());
    }

    @Test
    @Transactional
    void createExistsTag() throws RepositoryException {
        TagEntity expectedTag = new TagEntity(0, "Sport");
        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class, () -> repository.create(expectedTag));
    }

    @Test
    @Transactional
    void updateTag() throws RepositoryException {
        TagEntity expectedTag = new TagEntity(7, "Tag2");
        repository.update(expectedTag);
        TagEntity actualTag = repository.readOne(7);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    @Transactional
    void deleteNotLinkedTag() throws RepositoryException {
        TagEntity tag = new TagEntity(0, "Tag3");
        repository.create(tag);
        tag.setId(repository.readByName(tag.getName()).getId());
        repository.deleteById(tag.getId());

        EmptyResultDataAccessException e = assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.readByName("Tag3");
        });

        assertEquals("No entity found for query; nested exception is javax.persistence.NoResultException: " +
                "No entity found for query", e.getMessage());
    }

    @Test
    @Transactional
    void deleteLinkedTag() {
        assertDoesNotThrow(() -> repository.deleteById(7));

        RepositoryException e = assertThrows(RepositoryException.class, () -> {
            repository.readOne(7);
        });

        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
    }

    @Test
    @Transactional
    void deleteNotExistTag() {
        assertThrows(RepositoryException.class, () -> repository.deleteById(111));
    }
}