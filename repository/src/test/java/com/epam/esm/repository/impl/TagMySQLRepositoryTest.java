package com.epam.esm.repository.impl;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;

import static com.epam.esm.repository.impl.util.EntityUtil.getNewTag;
import static com.epam.esm.repository.impl.util.EntityUtil.getTagId2;
import static com.epam.esm.repository.impl.util.EntityUtil.getTagId6;
import static com.epam.esm.repository.impl.util.EntityUtil.getTagId7;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = RepositoryTestConfig.class)

//@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaAuditing
//    @AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class TagMySQLRepositoryTest {

    @Autowired
    private TagRepository repository;

    @Test
    void readAllTags() throws RepositoryException {
        List<TagEntity> tags;
        tags = repository.readAll();
        assertFalse(tags.isEmpty());
        tags.forEach(Assertions::assertNotNull);
    }

    @Test
    void readAllTagsPaginated() throws RepositoryException {
        List<TagEntity> tags;
        CriteriaEntity cr = new CriteriaEntity();
        cr.setPage(1);
        cr.setSize(5);
        cr.setSorting("-id");

        tags = repository.readAllPaginated(cr);

        assertEquals(5, tags.size());
        assertEquals(getTagId7(), tags.get(0));
        assertEquals(7, cr.getTotalSize());

        cr.setPage(2);
        cr.setSorting("id");
        tags = repository.readAllPaginated(cr);

        assertEquals(2, tags.size());
        assertEquals(getTagId6(), tags.get(0));
        assertEquals(7, cr.getTotalSize());
    }

    @Test
    void readAllTagsPaginatedWithException(){
        CriteriaEntity cr = new CriteriaEntity();
        cr.setPage(3);
        cr.setSize(5);

        RepositoryException e = assertThrows(RepositoryException.class, () -> repository.readAllPaginated(cr));
        assertEquals("Page must be between 1 and 2", e.getMessage());

        cr.setPage(2);
        cr.setSize(20);

        e = assertThrows(RepositoryException.class, () -> repository.readAllPaginated(cr));
        assertEquals("Page must be 1", e.getMessage());
    }

    @Test
    void readTagById() throws RepositoryException {
        TagEntity expectedTag = getTagId6();
        TagEntity actualTag = repository.readById(6);
        RepositoryException e = assertThrows(RepositoryException.class, () -> repository.readById(-1));

        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void readTagByName() throws RepositoryException {
        TagEntity expectedTag = getTagId2();
        TagEntity actualTag = repository.readByName("Water");
        assertThrows(NoResultException.class, () -> repository.readByName("abcd"));

        assertEquals(expectedTag, actualTag);
    }

    @Test
    void createTag() throws RepositoryException {
        TagEntity expectedTag = getNewTag();
        repository.create(expectedTag);
        long id = expectedTag.getId();
        TagEntity actualTag = repository.readByName("New tag");
        assertEquals(expectedTag.getName(), actualTag.getName());
        actualTag = repository.readById(id);
        assertEquals(expectedTag.getName(), actualTag.getName());
    }

    @Test
    void createExistsTag(){
        TagEntity expectedTag = new TagEntity(0, "Sport");
        assertThrows(PersistenceException.class, () -> repository.create(expectedTag));
    }

    @Test
    void updateTag() throws RepositoryException {
        TagEntity expectedTag = new TagEntity(7, "Tag2");
        repository.merge(expectedTag);
        TagEntity actualTag = repository.readById(7);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void deleteNotLinkedTag() throws RepositoryException {
        TagEntity tag = new TagEntity(0, "Tag3");
        repository.create(tag);
        tag.setId(repository.readByName(tag.getName()).getId());
        repository.deleteById(tag.getId());

        NoResultException e = assertThrows(NoResultException.class, () -> repository.readByName("Tag3"));

        assertEquals("No entity found for query", e.getMessage());
    }

    @Test
    void deleteLinkedTag() {
        assertDoesNotThrow(() -> repository.deleteById(7));

        RepositoryException e = assertThrows(RepositoryException.class, () -> repository.readById(7));

        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
    }

    @Test
    void deleteNotExistTag() {
        assertThrows(RepositoryException.class, () -> repository.deleteById(111));
    }

//    @Test
//    void findMostWidelyTagTest(){
//        List<TagEntity> tags = repository.findMostWidelyTag();
//        System.out.println(tags.size());
//        System.out.println(tags);
//        assertTrue(tags.size() > 0);
//    }

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
}