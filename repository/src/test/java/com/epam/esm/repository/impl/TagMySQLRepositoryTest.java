package com.epam.esm.repository.impl;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.OrderForGiftCertificateEntity;
import com.epam.esm.entity.OrderItemEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.OrderForGiftCertificateRepository;
import com.epam.esm.repository.interf.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.esm.repository.impl.EntityFactory.GiftCertificateEntityFactory.getGiftCertificateEntityId3;
import static com.epam.esm.repository.impl.EntityFactory.GiftCertificateEntityFactory.getGiftCertificateEntityId5;
import static com.epam.esm.repository.impl.EntityFactory.TagEntityFactory.getNewTagEntity;
import static com.epam.esm.repository.impl.EntityFactory.TagEntityFactory.getNewTagEntityId2;
import static com.epam.esm.repository.impl.EntityFactory.TagEntityFactory.getNewTagEntityId6;
import static com.epam.esm.repository.impl.EntityFactory.TagEntityFactory.getNewTagEntityId7;
import static com.epam.esm.repository.impl.EntityFactory.TagEntityFactory.getTagEntityId1;
import static com.epam.esm.repository.impl.EntityFactory.TagEntityFactory.getTagEntityId2;
import static com.epam.esm.repository.impl.EntityFactory.TagEntityFactory.getTagEntityId3;
import static com.epam.esm.repository.impl.EntityFactory.TagEntityFactory.getTagEntityId4;
import static com.epam.esm.repository.impl.EntityFactory.TagEntityFactory.getTagEntityId5;
import static com.epam.esm.repository.impl.EntityFactory.TagEntityFactory.getTagEntityId6;
import static com.epam.esm.repository.impl.EntityFactory.TagEntityFactory.getTagEntityId7;
import static com.epam.esm.repository.impl.EntityFactory.UserEntityFactory.getUserEntityId4;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@EnableJpaAuditing
class TagMySQLRepositoryTest {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private OrderForGiftCertificateRepository orderRepository;
    @PersistenceContext
    EntityManager entityManager;
    List<TagEntity> tags;

    @Test
    void readAllTagsTest() throws RepositoryException {
        tags = tagRepository.readAll();
        assertFalse(tags.isEmpty());
        tags.forEach(Assertions::assertNotNull);
    }

    @Test
    void readAllTagsDataConsistenceTest() throws RepositoryException {
        tags = tagRepository.readAll();

        assertEquals(getTagEntityId1(), tags.get(0));
        assertEquals(getTagEntityId2(), tags.get(1));
        assertEquals(getTagEntityId3(), tags.get(2));
        assertEquals(getTagEntityId4(), tags.get(3));
        assertEquals(getTagEntityId5(), tags.get(4));
        assertEquals(getTagEntityId6(), tags.get(5));
        assertEquals(getTagEntityId7(), tags.get(6));
    }

    @Test
    void readAllTagsPaginatedTest1() throws RepositoryException {
        CriteriaEntity cr = new CriteriaEntity(1, 5, "-id");

        tags = tagRepository.readPaginated(cr);

        assertEquals(5, tags.size());
        assertEquals(getNewTagEntityId7(), tags.get(0));
        tags.forEach(Assertions::assertNotNull);
        assertEquals(7, cr.getTotalSize());

        cr.setPage(2);
        cr.setSorting("id");
        tags = tagRepository.readPaginated(cr);

        assertEquals(2, tags.size());
        assertEquals(getNewTagEntityId6(), tags.get(0));
        assertEquals(7, cr.getTotalSize());
    }

    @Test
    void readAllTagsNullCriteriaTest(){
        RepositoryException e = assertThrows(RepositoryException.class,
                () -> tagRepository.readPaginated(null));
        assertEquals("Criteria must be not null", e.getMessage());

        e = assertThrows(RepositoryException.class,
                () -> tagRepository.readPaginated(new CriteriaEntity(null , 5, null)));
        assertEquals("Size and page must be not null", e.getMessage());
    }

    @Test
    void readAllTagsPaginatedWithExceptionTest(){
        CriteriaEntity cr = new CriteriaEntity(3, 5, "id");

        RepositoryException e = assertThrows(RepositoryException.class, () -> tagRepository.readPaginated(cr));
        assertEquals("Page must be between 1 and 2", e.getMessage());

        cr.setPage(2);
        cr.setSize(20);

        e = assertThrows(RepositoryException.class, () -> tagRepository.readPaginated(cr));
        assertEquals("Page must be 1", e.getMessage());
    }

    @Test
    void readTagByIdTest() throws RepositoryException {
        TagEntity expectedTag = getNewTagEntityId6();
        TagEntity actualTag = tagRepository.readById(6);

        assertEquals(expectedTag, actualTag);
        RepositoryException e = assertThrows(RepositoryException.class, () -> tagRepository.readById(-1));
        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
    }

    @Test
    void readTagByNameTest() throws RepositoryException {
        TagEntity expectedTag = getNewTagEntityId2();
        TagEntity actualTag = tagRepository.readByName("Water");
        assertThrows(EmptyResultDataAccessException.class, () -> tagRepository.readByName("abcd"));

        assertEquals(expectedTag, actualTag);
    }

    @Test
    void createTagTest() throws RepositoryException {
        TagEntity expectedTag = getNewTagEntity();
        tagRepository.create(expectedTag);
        long id = expectedTag.getId();
        TagEntity actualTag = tagRepository.readByName("New tag");
        assertEquals(expectedTag.getName(), actualTag.getName());
        actualTag = tagRepository.readById(id);
        assertEquals(expectedTag.getName(), actualTag.getName());
    }

    @Test
    void createTagTest1() throws RepositoryException {
        TagEntity expectedTag = getNewTagEntity();
        expectedTag.setId(66);
        expectedTag = tagRepository.merge(expectedTag);
        long id = expectedTag.getId();
        System.out.println(id);
        TagEntity actualTag = tagRepository.readByName("New tag");
        assertEquals(expectedTag.getName(), actualTag.getName());
        actualTag = tagRepository.readById(id);
        assertEquals(expectedTag.getName(), actualTag.getName());
    }

    @Test
    void createTagAuditingTest() throws RepositoryException {
        TagEntity expectedTag = getNewTagEntity();

        tagRepository.create(expectedTag);
        LocalDateTime createDate = LocalDateTime.now();
        entityManager.flush();
        entityManager.clear();
        TagEntity actualTag = tagRepository.readById(expectedTag.getId());

        assertTrue((createDate.toEpochSecond(ZoneOffset.UTC) -
                actualTag.getCreateDate().toEpochSecond(ZoneOffset.UTC)) < 1);

        assertTrue((createDate.toEpochSecond(ZoneOffset.UTC) -
                actualTag.getLastUpdateDate().toEpochSecond(ZoneOffset.UTC)) < 1);
    }

    @Test
    void createExistsTagTest(){
        TagEntity expectedTag = new TagEntity(0, "Sport");
        assertThrows(DataIntegrityViolationException.class, () -> tagRepository.create(expectedTag));
    }

    @Test
    void updateTagTest() throws RepositoryException {
        TagEntity expectedTag = new TagEntity(7, "Tag2");
        tagRepository.merge(expectedTag);
        TagEntity actualTag = tagRepository.readById(7);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void updateTagAuditingTest() throws RepositoryException {
        TagEntity expectedTag = new TagEntity(7, "Tag2");
        tagRepository.merge(expectedTag);
        LocalDateTime updateDate = LocalDateTime.now();
        entityManager.flush();
        entityManager.clear();
        TagEntity actualTag = tagRepository.readById(7);

        assertEquals(LocalDateTime.parse("2022-04-27T04:43:55.000"), actualTag.getCreateDate());

        assertTrue((updateDate.toEpochSecond(ZoneOffset.UTC) -
                actualTag.getLastUpdateDate().toEpochSecond(ZoneOffset.UTC)) < 1);
    }

    @Test
    void deleteNotLinkedTagTest() throws RepositoryException {
        TagEntity tag = new TagEntity(0, "Tag3");
        tagRepository.create(tag);
        tag.setId(tagRepository.readByName(tag.getName()).getId());
        tagRepository.deleteById(tag.getId());

        EmptyResultDataAccessException e = assertThrows(EmptyResultDataAccessException.class,
                () -> tagRepository.readByName("Tag3"));

        assertTrue(Objects.requireNonNull(e.getMessage()).contains("No entity found for query"));
    }

    @Test
    void deleteLinkedTagTest() {
        assertDoesNotThrow(() -> tagRepository.deleteById(7));

        RepositoryException e = assertThrows(RepositoryException.class, () -> tagRepository.readById(7));

        assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
    }

    @Test
    void deleteNotExistTagTest() {
        assertThrows(RepositoryException.class, () -> tagRepository.deleteById(111));
    }

    @Test
    void findMostWidelyTagTest(){
        tags = tagRepository.findMostWidelyTag();

        assertEquals(2, tags.size());
        assertEquals(getTagEntityId5(), tags.get(0));
        assertEquals(getTagEntityId7(), tags.get(1));
    }

    @Test
    void findMostWidelyTagTwoUsersTest() throws RepositoryException {
        OrderItemEntity orderItem = new OrderItemEntity(0, getGiftCertificateEntityId5(), null, 1);
        OrderForGiftCertificateEntity expectedOrder = new OrderForGiftCertificateEntity(0, LocalDateTime.now(),
                new BigDecimal("130.0"), getUserEntityId4(), Stream.of(orderItem).collect(Collectors.toList()));
        orderItem.setOrder(expectedOrder);

        orderRepository.create(expectedOrder);
        tags = tagRepository.findMostWidelyTag();

        assertEquals(3, tags.size());
        assertEquals(getTagEntityId5(), tags.get(0));
        assertEquals(getTagEntityId7(), tags.get(1));
        assertEquals(getTagEntityId5(), tags.get(2));
    }

    @Test
    void findMostWidelyTagAddOrderToUserId4Test() throws RepositoryException {
        OrderItemEntity orderItem = new OrderItemEntity(0, getGiftCertificateEntityId3(), null, 3);
        OrderForGiftCertificateEntity expectedOrder = new OrderForGiftCertificateEntity(0, LocalDateTime.now(),
                new BigDecimal("230.0"), getUserEntityId4(), Stream.of(orderItem).collect(Collectors.toList()));
        orderItem.setOrder(expectedOrder);

        orderRepository.create(expectedOrder);
        tags = tagRepository.findMostWidelyTag();

        assertEquals(1, tags.size());
        assertEquals(getTagEntityId7(), tags.get(0));
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
}