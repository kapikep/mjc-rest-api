package com.epam.esm.repository.impl;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.constant.SearchParam;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class GiftCertificateMySQLRepositoryTest {
    @Autowired
    private GiftCertificateRepository repository;

    @Autowired
    private TagMySQLRepository tagMySQLRepository;

    @Test
    void readAll() throws RepositoryException {
        List<GiftCertificateEntity> gifts = repository.readAll();

        gifts.forEach(System.out::println);
        assertEquals(5, gifts.size());
        gifts.forEach(Assertions::assertNotNull);
    }

    @Test
    void readGiftExistCertificate() throws RepositoryException {
        GiftCertificateEntity expectedGift = getGiftCertificateId1();
        GiftCertificateEntity actualGift = repository.readOne(1);

        assertEquals(expectedGift, actualGift);
    }

//    @Test
//    void put10000Gifts() throws RepositoryException {
//        int maxLinkedTags = 7;
//        Random tagSizeRand = new Random();
//        Random r = new Random();
//        Random randomTag = new Random();
//
//        for (int i = 0; i < 10000; i++) {
//            try {
//                List<TagEntity> tagEntities = linkGiftsAndTags(tagSizeRand.nextInt(maxLinkedTags) + 1, randomTag);
//                repository.create(new GiftCertificateEntity(0, "name" + i, "description" + i, (double)r.nextInt(600),
//                        r.nextInt(300), LocalDateTime.now(), LocalDateTime.now(), tagEntities));
//            } catch (Exception ignored) {
//            }
//        }
//    }
//
//    private List<TagEntity> linkGiftsAndTags(int size, Random randomTag) throws RepositoryException {
//        int totalSize = 1075;
//        List<TagEntity> tagEntities = new ArrayList<>(size);
////        System.out.println();
////        System.out.println("Size " + size);
//
//        for (int i = 0; i < size; i++) {
//            try {
//                long id = randomTag.nextInt(totalSize);
////                System.out.print(id + " ");
//                tagEntities.add(tagMySQLRepository.readOne(id));
//            }catch (Exception ignored){
//            }
//        }
//        return tagEntities;
//    }

    @Test
    void search1000Gifts() throws RepositoryException {
        CriteriaEntity cr = new CriteriaEntity();
        cr.setSorting("name");
        cr.setSize(30);
        cr.setPage(1);
        System.out.println(repository.findByParams(cr));
        long a = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            repository.findByParams(cr);
        }
        long b = System.currentTimeMillis();

        System.out.println("total time -> " + (b-a) + " ms");
        System.out.println("total size -> " + cr.getTotalSize());
    }

    @Test
    void readGiftNotExistCertificate() {
        assertThrows(RepositoryException.class, () -> repository.readOne(111));
    }

    @Test
    void findGiftCertificateMatchChecking() throws RepositoryException {
        GiftCertificateEntity expectedGift = getGiftCertificateId2();
        CriteriaEntity criteria = new CriteriaEntity();
        criteria.addSearchParam(SearchParam.GIFT_SEARCH_NAME, "Car");
        criteria.addSearchParam(SearchParam.GIFT_SEARCH_DESCRIPTION, "washing");
        List<GiftCertificateEntity> actualGifts = repository.findByParams(criteria);

        assertEquals(1, actualGifts.size());
        assertTrue(actualGifts.contains(expectedGift));

        criteria.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Sport");
        actualGifts = repository.findByParams(criteria);

        assertEquals(0, actualGifts.size());
        assertFalse(actualGifts.contains(expectedGift));
    }

    @Test
    void findGiftCertificateByTags() throws RepositoryException {
        CriteriaEntity criteria = new CriteriaEntity();

        criteria.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Auto");
        List<GiftCertificateEntity> actualGifts = repository.findByParams(criteria);

        assertEquals(2, actualGifts.size());
        assertTrue(actualGifts.contains(getGiftCertificateId2()));
        assertTrue(actualGifts.contains(getGiftCertificateId4()));
        criteria.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Auto,Health");
        actualGifts = repository.findByParams(criteria);
        assertEquals(1, actualGifts.size());
        assertTrue(actualGifts.contains(getGiftCertificateId4()));

        criteria.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Auto,Health,Water");
        actualGifts = repository.findByParams(criteria);
        assertEquals(0, actualGifts.size());

        criteria.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Sport,Water,Health");
        actualGifts = repository.findByParams(criteria);
        assertEquals(1, actualGifts.size());
        assertTrue(actualGifts.contains(getGiftCertificateId1()));
    }

    @Test
    void sranyiHibernate() throws RepositoryException {
        List<GiftCertificateEntity> actualGifts = repository.findByParams(null);
        System.out.println("-------------------result------------------");
        if (actualGifts != null) {
            actualGifts.forEach(System.out::println);
        }
    }

    @Test
    void findGiftCertificateSortingChecking() throws RepositoryException {
        GiftCertificateEntity expectedLastGift = getGiftCertificateId1();
        GiftCertificateEntity expectedFirstGift = getGiftCertificateId4();
        List<GiftCertificateEntity> actualGifts = repository.findByParams(null);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(0));
        assertEquals(expectedLastGift, actualGifts.get(4));

        actualGifts = repository.findByParams(null);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(0));
        assertEquals(expectedLastGift, actualGifts.get(4));

        actualGifts = repository.findByParams(null);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(4));
        assertEquals(expectedLastGift, actualGifts.get(0));
    }

    @Test
    @Transactional
    void createGiftCertificate1() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificateId6();

        repository.create(expectedGift);
        long id = expectedGift.getId();

        GiftCertificateEntity actualGift = repository.readOne(id);

        expectedGift.setId(actualGift.getId());
        assertEquals(expectedGift, actualGift);
    }

    @Test
    void createGiftCertificateCheckTransactional() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificateId6();
        expectedGift.setId(6);
        expectedGift.setName("Transactional");
        expectedGift.addTag(getEmptyTag());

        int id = repository.readAll().size() + 1;

        assertThrows(RepositoryException.class, () -> repository.create(expectedGift));
        assertThrows(RepositoryException.class, () -> repository.readOne(id));
    }

    @Test
    void updateGiftCertificateCheckTransactional1() throws RepositoryException {
        GiftCertificateEntity expectedGift = getGiftCertificateId1();
        expectedGift.setName("Transactional");
        expectedGift.addTag(getEmptyTag());

        assertThrows(RepositoryException.class, () -> repository.merge(expectedGift));

        GiftCertificateEntity actual = repository.readOne(1);

        assertEquals(getGiftCertificateId1(), actual);
    }

    @Test
    @Transactional
    void createGiftCertificateCheckTransactional1() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificateId6();
        expectedGift.setName("Transactional1");
        repository.create(expectedGift);
        expectedGift.setName("Transactional2");
        repository.create(expectedGift);
    }

    @Test
    @Transactional
    void updateGiftCertificate1() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificateId6();
        expectedGift.setId(5);

        repository.merge(expectedGift);
        GiftCertificateEntity actualGifts = repository.readOne(5);
        assertEquals(expectedGift, actualGifts);
    }

    @Test
    @Transactional
    void updateGiftCertificate2() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificateId6();
        expectedGift.setId(4);
        expectedGift.setTags(null);

        repository.merge(expectedGift);
        GiftCertificateEntity actualGifts = repository.readOne(4);
        expectedGift.setTags(new ArrayList<>());
        assertEquals(expectedGift, actualGifts);
    }

    @Test
    @Transactional
    void deleteExistingGiftCertificate() throws RepositoryException {
        repository.deleteById(4);

        assertThrows(RepositoryException.class, () -> repository.readOne(4));
    }

    @Test
    void deleteNotExistingGiftCertificate() {
        assertThrows(RepositoryException.class, () -> repository.deleteById(111));
    }

    private GiftCertificateEntity getGiftCertificateId1() {
        return new GiftCertificateEntity(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagId1(), getTagId2(), getTagId7()).collect(Collectors.toList()));
    }

    private GiftCertificateEntity getGiftCertificateId2() {
        return new GiftCertificateEntity(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagId5()).collect(Collectors.toList()));
    }

    private GiftCertificateEntity getGiftCertificateId4() {
        return new GiftCertificateEntity(4, "Bowling for the company",
                "Bowling will be an excellent option for outdoor activities for a large company", 45.0, 60, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagId5(), getTagId7()).collect(Collectors.toList()));
    }

    private GiftCertificateEntity getNewGiftCertificateId6() {
        return new GiftCertificateEntity(6, "Name", "Description", 95.0, 60, LocalDateTime.parse("2020-04-27T04:43:55.000"),
                LocalDateTime.parse("2021-04-27T04:43:55.000"), Stream.of(getTagId5(), getTagId7()).collect(Collectors.toList()));
    }

    private TagEntity getTagId1() {
        return new TagEntity(1, "Sport");
    }

    private TagEntity getTagId2() {
        return new TagEntity(2, "Water");
    }

    private TagEntity getTagId5() {
        return new TagEntity(5, "Auto");
    }

    private TagEntity getTagId7() {
        return new TagEntity(7, "Health");
    }

    private TagEntity getEmptyTag() {
        return new TagEntity(0, null);
    }
}