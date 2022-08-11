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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.impl.util.EntityUtil.getCriteriaWithDefaultVal;
import static com.epam.esm.repository.impl.util.EntityUtil.getEmptyTag;
import static com.epam.esm.repository.impl.util.EntityUtil.getGiftCertificateId1;
import static com.epam.esm.repository.impl.util.EntityUtil.getGiftCertificateId2;
import static com.epam.esm.repository.impl.util.EntityUtil.getGiftCertificateId4;
import static com.epam.esm.repository.impl.util.EntityUtil.getGiftCertificateId5;
import static com.epam.esm.repository.impl.util.EntityUtil.getNewGiftCertificate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest
//@Transactional

@ActiveProfiles("test")
@DataJpaTest
@EnableJpaAuditing
class GiftCertificateMySQLRepositoryTest {
    @Autowired
    private GiftCertificateRepository repository;

    @Autowired
    private TagMySQLRepository tagMySQLRepository;

    @Test
    void readAll() throws RepositoryException {
        List<GiftCertificateEntity> gifts = repository.readAll();

        assertEquals(5, gifts.size());
        gifts.forEach(Assertions::assertNotNull);
    }

    @Test
    void readGiftExistCertificate() throws RepositoryException {
        GiftCertificateEntity expectedGift = getGiftCertificateId1();
        GiftCertificateEntity actualGift = repository.readById(1);

        assertEquals(expectedGift, actualGift);
    }

    @Test
    void readGiftNotExistCertificate() {
        assertThrows(RepositoryException.class, () -> repository.readById(111));
    }

//    @Test
//    void search1000Gifts() throws RepositoryException {
//        CriteriaEntity cr = getCriteriaWithDefaultVal();
//        cr.setSorting("id");
//        System.out.println(repository.findByCriteria(cr));
//        long a = System.currentTimeMillis();
//        for (int i = 0; i < 100; i++) {
//            repository.findByCriteria(cr);
//        }
//        long b = System.currentTimeMillis();
//
//        System.out.println("total time -> " + (b - a) + " ms");
//        System.out.println("total size -> " + cr.getTotalSize());
//    }

    @Test
    void findGiftCertificateMatchChecking() throws RepositoryException {
        GiftCertificateEntity expectedGift = getGiftCertificateId2();
        CriteriaEntity cr = getCriteriaWithDefaultVal();
        cr.addSearchParam(SearchParam.GIFT_SEARCH_NAME, "Car");
        cr.addSearchParam(SearchParam.GIFT_SEARCH_DESCRIPTION, "washing");
        List<GiftCertificateEntity> actualGifts = repository.findByCriteria(cr);

        assertEquals(1, actualGifts.size());
        assertTrue(actualGifts.contains(expectedGift));

        cr.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Sport");
        actualGifts = repository.findByCriteria(cr);

        assertEquals(0, actualGifts.size());
        assertFalse(actualGifts.contains(expectedGift));
    }

    @Test
    void findGiftCertificateByTags() throws RepositoryException {
        CriteriaEntity cr = getCriteriaWithDefaultVal();
        cr.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Auto");
        List<GiftCertificateEntity> actualGifts = repository.findByCriteria(cr);

        assertEquals(2, actualGifts.size());
        assertEquals(2, cr.getTotalSize());
        assertTrue(actualGifts.contains(getGiftCertificateId2()));
        assertTrue(actualGifts.contains(getGiftCertificateId4()));

        cr.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Auto,Health");
        actualGifts = repository.findByCriteria(cr);
        assertEquals(1, actualGifts.size());
        assertEquals(1, cr.getTotalSize());
        assertTrue(actualGifts.contains(getGiftCertificateId4()));

        cr.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Auto,Health,Water");
        actualGifts = repository.findByCriteria(cr);
        assertEquals(0, actualGifts.size());
        assertEquals(0, cr.getTotalSize());

        cr.addSearchParam(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "Sport,Water,Health");
        actualGifts = repository.findByCriteria(cr);
        assertEquals(1, actualGifts.size());
        assertEquals(1, cr.getTotalSize());
        assertTrue(actualGifts.contains(getGiftCertificateId1()));
    }

    @Test
    void findGiftCertificateSortingChecking() throws RepositoryException {
        CriteriaEntity cr = getCriteriaWithDefaultVal();
        cr.setSorting("id");
        GiftCertificateEntity expectedFirstGift = getGiftCertificateId1();
        GiftCertificateEntity expectedLastGift = getGiftCertificateId5();
        List<GiftCertificateEntity> actualGifts = repository.findByCriteria(cr);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(0));
        assertEquals(expectedLastGift, actualGifts.get(4));

        cr.setSorting("+id");
        actualGifts = repository.findByCriteria(cr);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(0));
        assertEquals(expectedLastGift, actualGifts.get(4));

        cr.setSorting("-id");
        actualGifts = repository.findByCriteria(cr);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(4));
        assertEquals(expectedLastGift, actualGifts.get(0));
    }

    @Test
    void giftCertificatePageSortingChecking() throws RepositoryException {
        CriteriaEntity cr = getCriteriaWithDefaultVal();
        cr.setSorting("name");
        GiftCertificateEntity expectedLastGift = getGiftCertificateId1();
        GiftCertificateEntity expectedFirstGift = getGiftCertificateId4();
        List<GiftCertificateEntity> actualGifts = repository.readAllPaginated(cr);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(0));
        assertEquals(expectedLastGift, actualGifts.get(4));

        cr.setSorting("+name");
        actualGifts = repository.readAllPaginated(cr);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(0));
        assertEquals(expectedLastGift, actualGifts.get(4));

        cr.setSorting("-name");
        actualGifts = repository.readAllPaginated(cr);

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(4));
        assertEquals(expectedLastGift, actualGifts.get(0));
    }

    @Test
    void createGiftCertificate1() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificate();

        repository.create(expectedGift);
        long id = expectedGift.getId();

        GiftCertificateEntity actualGift = repository.readById(id);

        expectedGift.setId(actualGift.getId());
        assertEquals(expectedGift, actualGift);
    }

//    @Test
//    void createGiftCertificateCheckTransactional() throws RepositoryException {
//        final GiftCertificateEntity expectedGift = getNewGiftCertificate();
//        expectedGift.addTag(getEmptyTag());
//
////        final int id = repository.readAll().size() + 1;
//
//        List<TagEntity> tags = expectedGift.getTags();
//
//        repository.create(expectedGift);
//        final long id = expectedGift.getId();
//        final GiftCertificateEntity gc = repository.readById(id);
//        gc.getTags();
//
//        assertThrows(RepositoryException.class, () -> repository.create(expectedGift));
////        assertThrows(RepositoryException.class, () -> repository.readOne(id));
//    }

//    @Test
//    void updateGiftCertificateCheckTransactional1() throws RepositoryException {
//        GiftCertificateEntity expectedGift = getGiftCertificateId1();
//        expectedGift.setName("Transactional");
//        expectedGift.addTag(getEmptyTag());
//
//        assertThrows(RepositoryException.class, () -> repository.merge(expectedGift));
//
//        GiftCertificateEntity actual = repository.readById(1);
//
//        assertEquals(getGiftCertificateId1(), actual);
//    }

//    @Test
//    @Transactional
//    void createGiftCertificateCheckTransactional1() throws RepositoryException {
//        GiftCertificateEntity expectedGift = getNewGiftCertificate();
//        expectedGift.setName("Transactional1");
//        repository.create(expectedGift);
//        expectedGift.setName("Transactional2");
//        repository.create(expectedGift);
//    }

    @Test
    void updateGiftCertificate1() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificate();
        expectedGift.setId(5);

        repository.merge(expectedGift);
        GiftCertificateEntity actualGifts = repository.readById(5);
        assertEquals(expectedGift, actualGifts);
    }

    @Test
    void updateGiftCertificate2() throws RepositoryException {
        GiftCertificateEntity expectedGift = getNewGiftCertificate();
        expectedGift.setId(4);
        expectedGift.setTags(null);

        repository.merge(expectedGift);
        GiftCertificateEntity actualGifts = repository.readById(4);
        expectedGift.setTags(new ArrayList<>());
        assertEquals(expectedGift, actualGifts);
    }

    @Test
    void deleteExistingGiftCertificate() throws RepositoryException {
        repository.deleteById(4);

        assertThrows(RepositoryException.class, () -> repository.readById(4));
    }

    @Test
    void deleteNotExistingGiftCertificate() {
        assertThrows(RepositoryException.class, () -> repository.deleteById(111));
    }

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