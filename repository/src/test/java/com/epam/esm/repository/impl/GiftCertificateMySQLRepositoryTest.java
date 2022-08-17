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
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.impl.EntityFactory.EntityFactory.getNewCriteriaWithDefaultVal;
import static com.epam.esm.repository.impl.EntityFactory.GiftCertificateEntityFactory.getNewGiftCertificateEntity;
import static com.epam.esm.repository.impl.EntityFactory.GiftCertificateEntityFactory.getNewGiftCertificateEntityId1;
import static com.epam.esm.repository.impl.EntityFactory.GiftCertificateEntityFactory.getNewGiftCertificateEntityId2;
import static com.epam.esm.repository.impl.EntityFactory.GiftCertificateEntityFactory.getNewGiftCertificateEntityId4;
import static com.epam.esm.repository.impl.EntityFactory.GiftCertificateEntityFactory.getNewGiftCertificateEntityId5;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@EnableJpaAuditing
class GiftCertificateMySQLRepositoryTest {
    @Autowired
    private GiftCertificateRepository giftCertificateRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    void readAllTest() throws RepositoryException {
        List<GiftCertificateEntity> gifts = giftCertificateRepository.readAll();

        assertEquals(5, gifts.size());
        gifts.forEach(Assertions::assertNotNull);
    }

    @Test
    void readGiftExistCertificateTest() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificateEntityId1();
        GiftCertificateEntity actualGift = giftCertificateRepository.readById(1);

        assertEquals(expectedGift, actualGift);
    }

    @Test
    void readGiftNotExistCertificateTest() {
        assertThrows(RepositoryException.class, () -> giftCertificateRepository.readById(111));
    }

    @Test
    void findGiftCertificateMatchCheckingTest() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificateEntityId2();
        CriteriaEntity cr = getNewCriteriaWithDefaultVal();
        cr.addSearchParam(SearchParam.GIFT_SEARCH_NAME, "Car");
        cr.addSearchParam(SearchParam.GIFT_SEARCH_DESCRIPTION, "washing");
        List<GiftCertificateEntity> actualGifts = giftCertificateRepository.findByCriteria(cr);

        assertEquals(1, actualGifts.size());
        assertTrue(actualGifts.contains(expectedGift));

        cr.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Sport");
        actualGifts = giftCertificateRepository.findByCriteria(cr);

        assertEquals(0, actualGifts.size());
        assertFalse(actualGifts.contains(expectedGift));
    }

    @Test
    void findGiftCertificateByTagsTest() throws RepositoryException {
        CriteriaEntity cr = getNewCriteriaWithDefaultVal();
        cr.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Auto");
        List<GiftCertificateEntity> actualGifts = giftCertificateRepository.findByCriteria(cr);

        assertEquals(3, actualGifts.size());
        assertEquals(3, cr.getTotalSize());
        assertTrue(actualGifts.contains(getNewGiftCertificateEntityId2()));
        assertTrue(actualGifts.contains(getNewGiftCertificateEntityId4()));

        cr.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Auto,Health");
        actualGifts = giftCertificateRepository.findByCriteria(cr);
        assertEquals(1, actualGifts.size());
        assertEquals(1, cr.getTotalSize());
        assertTrue(actualGifts.contains(getNewGiftCertificateEntityId4()));

        cr.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Auto,Health,Water");
        actualGifts = giftCertificateRepository.findByCriteria(cr);
        assertEquals(0, actualGifts.size());
        assertEquals(0, cr.getTotalSize());

        cr.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Sport,Water,Health");
        actualGifts = giftCertificateRepository.findByCriteria(cr);
        assertEquals(1, actualGifts.size());
        assertEquals(1, cr.getTotalSize());
        assertTrue(actualGifts.contains(getNewGiftCertificateEntityId1()));
    }

    @Test
    void findGiftCertificateSortingCheckingTest() throws RepositoryException {
        CriteriaEntity cr = getNewCriteriaWithDefaultVal();
        cr.setSorting("id");
        GiftCertificateEntity expectedFirstGift = getNewGiftCertificateEntityId1();
        GiftCertificateEntity expectedLastGift = getNewGiftCertificateEntityId5();
        List<GiftCertificateEntity> actualGifts = giftCertificateRepository.findByCriteria(cr);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(0));
        assertEquals(expectedLastGift, actualGifts.get(4));

        cr.setSorting("+id");
        actualGifts = giftCertificateRepository.findByCriteria(cr);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(0));
        assertEquals(expectedLastGift, actualGifts.get(4));

        cr.setSorting("-id");
        actualGifts = giftCertificateRepository.findByCriteria(cr);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(4));
        assertEquals(expectedLastGift, actualGifts.get(0));
    }

    @Test
    void giftCertificatePageSortingCheckingTest() throws RepositoryException {
        CriteriaEntity cr = getNewCriteriaWithDefaultVal();
        cr.setSorting("name");
        GiftCertificateEntity expectedLastGift = getNewGiftCertificateEntityId1();
        GiftCertificateEntity expectedFirstGift = getNewGiftCertificateEntityId4();
        List<GiftCertificateEntity> actualGifts = giftCertificateRepository.readPaginated(cr);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(0));
        assertEquals(expectedLastGift, actualGifts.get(4));

        cr.setSorting("+name");
        actualGifts = giftCertificateRepository.readPaginated(cr);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(0));
        assertEquals(expectedLastGift, actualGifts.get(4));

        cr.setSorting("-name");
        actualGifts = giftCertificateRepository.readPaginated(cr);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(4));
        assertEquals(expectedLastGift, actualGifts.get(0));
    }

    @Test
    void createGiftCertificateTest() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificateEntity();

        giftCertificateRepository.create(expectedGift);
        GiftCertificateEntity actualGift = giftCertificateRepository.readById(expectedGift.getId());

        expectedGift.setId(actualGift.getId());
        assertEquals(expectedGift, actualGift);
    }

    @Test
    void createGiftCertificateAuditingTest() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificateEntity();

        giftCertificateRepository.create(expectedGift);
        LocalDateTime createDate = LocalDateTime.now();
        entityManager.flush();
        entityManager.clear();
        GiftCertificateEntity actualGift = giftCertificateRepository.readById(expectedGift.getId());

        assertTrue((createDate.toEpochSecond(ZoneOffset.UTC) -
                actualGift.getCreateDate().toEpochSecond(ZoneOffset.UTC)) < 1);

        assertTrue((createDate.toEpochSecond(ZoneOffset.UTC) -
                actualGift.getLastUpdateDate().toEpochSecond(ZoneOffset.UTC)) < 1);
    }

    @Test
    void createGiftCertificateCheckTagsCascadeTypeTest() throws RepositoryException {
        TestTransaction.end();

        GiftCertificateEntity expectedGift = getNewGiftCertificateEntity();
        expectedGift.addTag(new TagEntity(0, null));

        long id = giftCertificateRepository.readAll().size() + 1;

        assertThrows(org.springframework.dao.InvalidDataAccessApiUsageException.class,
                () -> giftCertificateRepository.create(expectedGift));
        assertThrows(RepositoryException.class, () -> giftCertificateRepository.readById(id));
    }

    @Test
    void updateGiftCertificate1Test() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificateEntity();
        expectedGift.setId(5);

        giftCertificateRepository.merge(expectedGift);
        GiftCertificateEntity actualGifts = giftCertificateRepository.readById(5);
        assertEquals(expectedGift, actualGifts);
    }

    @Test
    void updateGiftCertificate2Test() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificateEntity();
        expectedGift.setId(4);
        expectedGift.setTags(null);

        giftCertificateRepository.merge(expectedGift);
        GiftCertificateEntity actualGifts = giftCertificateRepository.readById(4);
        expectedGift.setTags(new ArrayList<>());
        assertEquals(expectedGift, actualGifts);
    }

    @Test
    void updateGiftCertificateAuditingTest() throws RepositoryException {
        GiftCertificateEntity expectedGift = giftCertificateRepository.readById(3);
        expectedGift.setName("New name");
        expectedGift.setPrice(100.0);

        giftCertificateRepository.merge(expectedGift);
        LocalDateTime createDate = LocalDateTime.now();
        entityManager.flush();
        entityManager.clear();
        GiftCertificateEntity actualGift = giftCertificateRepository.readById(3);

        assertEquals(LocalDateTime.parse("2022-04-27T04:43:55.000"), actualGift.getCreateDate());

        assertTrue((createDate.toEpochSecond(ZoneOffset.UTC) -
                actualGift.getLastUpdateDate().toEpochSecond(ZoneOffset.UTC)) < 1);
    }

    @Test
    void deleteExistingGiftCertificateTest() throws RepositoryException {
        giftCertificateRepository.deleteById(4);

        assertThrows(RepositoryException.class, () -> giftCertificateRepository.readById(4));
    }

    @Test
    void deleteNotExistingGiftCertificateTest() {
        assertThrows(RepositoryException.class, () -> giftCertificateRepository.deleteById(111));
    }

//    @Test
//    void search1000Gifts() throws RepositoryException {
//        CriteriaEntity cr = getNewCriteriaWithDefaultVal();
//        long a = System.currentTimeMillis();
//        for (int i = 0; i < 1000; i++) {
//            giftCertificateRepository.findByCriteria(cr);
//        }
//        long b = System.currentTimeMillis();
//
//        System.out.println("total time -> " + (b - a) + " ms");
//        System.out.println("total size -> " + cr.getTotalSize());
//    }
//
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


//    private List<TagEntity> linkGiftsAndTags(int size, Random randomTag) throws RepositoryException {
//        int totalSize = 1075;
//        List<TagEntity> tagEntities = new ArrayList<>(size);
//
//        for (int i = 0; i < size; i++) {
//            try {
//                long id = randomTag.nextInt(totalSize);
//                tagEntities.add(tagMySQLRepository.readOne(id));
//            }catch (Exception ignored){
//            }
//        }
//        return tagEntities;
//    }
}