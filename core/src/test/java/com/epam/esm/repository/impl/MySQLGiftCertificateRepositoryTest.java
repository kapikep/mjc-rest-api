package com.epam.esm.repository.impl;

import com.epam.esm.config.DevConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.constant.GiftCertificateSearchParam;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DevConfig.class)
class MySQLGiftCertificateRepositoryTest {
    @Autowired
    private GiftCertificateRepository repository;

    @Test
    void readAll() throws RepositoryException {
        List<GiftCertificate> gifts = repository.readAllGiftCertificates();

        assertEquals(5, gifts.size());
        gifts.forEach(Assertions::assertNotNull);
    }

    @Test
    void readGiftExistCertificate() throws RepositoryException {
        GiftCertificate expectedGift = getGiftCertificateId1();
        GiftCertificate actualGift = repository.readGiftCertificate(1);

        assertEquals(expectedGift, actualGift);
    }

    @Test
    void readGiftNotExistCertificate() {
        assertThrows(RepositoryException.class, () -> repository.readGiftCertificate(111));
    }

    @Test
    void findGiftCertificateMatchChecking() throws RepositoryException {
        GiftCertificate expectedGift = getGiftCertificateId2();
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put(GiftCertificateSearchParam.SEARCH_NAME, "Car");
        criteriaMap.put(GiftCertificateSearchParam.SEARCH_DESCRIPTION, "washing");
        List<GiftCertificate> actualGifts = repository.findGiftCertificate(criteriaMap, "name_desc");

        assertEquals(1, actualGifts.size());
        assertTrue(actualGifts.contains(expectedGift));

        criteriaMap.put(GiftCertificateSearchParam.SEARCH_TAG_NAME, "Спорт");
        actualGifts = repository.findGiftCertificate(criteriaMap, null);

        assertEquals(0, actualGifts.size());
        assertFalse(actualGifts.contains(expectedGift));
    }

    @Test
    void findGiftCertificateSortingChecking() throws RepositoryException {
        GiftCertificate expectedLastGift = getGiftCertificateId1();
        GiftCertificate expectedFirstGift = getGiftCertificateId4();
        List<GiftCertificate> actualGifts = repository.findGiftCertificate(null, "name");

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(0));
        assertEquals(expectedLastGift, actualGifts.get(4));

        actualGifts =  repository.findGiftCertificate(null, "+name");

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(0));
        assertEquals(expectedLastGift, actualGifts.get(4));

        actualGifts = repository.findGiftCertificate(null, "-name");

        assertEquals(5, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(4));
        assertEquals(expectedLastGift, actualGifts.get(0));
    }

    @Test
    void createGiftCertificate1() throws RepositoryException {
        GiftCertificate expectedGift = getNewGiftCertificateId6();
        expectedGift.setId(7);

        int id = repository.createGiftCertificate(expectedGift);

        GiftCertificate actualGift = repository.readGiftCertificate(id);
        repository.deleteGiftCertificate(id);

        assertEquals(7, id);
        assertEquals(expectedGift, actualGift);
    }


    @Test
    void updateGiftCertificate1() throws RepositoryException {
        GiftCertificate expectedGift = getNewGiftCertificateId6();
        expectedGift.setId(5);

        repository.updateGiftCertificate(expectedGift);
        GiftCertificate actualGifts = repository.readGiftCertificate(5);
        assertEquals(expectedGift, actualGifts);
    }

    @Test
    void updateGiftCertificate2() throws RepositoryException {
        GiftCertificate expectedGift = getNewGiftCertificateId6();
        expectedGift.setId(4);
        expectedGift.setTags(null);

        repository.updateGiftCertificate(expectedGift);
        GiftCertificate actualGifts = repository.readGiftCertificate(4);
        expectedGift.setTags(Arrays.asList(getEmptyTag()));
        assertEquals(expectedGift, actualGifts);
    }

    @Test
    void deleteExistingGiftCertificate() throws RepositoryException {
        GiftCertificate expectedGift = getNewGiftCertificateId6();

        int id = repository.createGiftCertificate(expectedGift);

        repository.deleteGiftCertificate(id);

        assertThrows(RepositoryException.class, () -> repository.readGiftCertificate(id));
    }

    @Test
    void deleteNotExistingGiftCertificate(){
        assertThrows(RepositoryException.class, () -> repository.deleteGiftCertificate(111));
    }

    private GiftCertificate getGiftCertificateId1() {
        return new GiftCertificate(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagId1(), getTagId2(), getTagId7()).collect(Collectors.toList()));
    }

    private GiftCertificate getGiftCertificateId2() {
        return new GiftCertificate(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagId5()).collect(Collectors.toList()));
    }

    private GiftCertificate getGiftCertificateId4() {
        return new GiftCertificate(4, "Bowling for the company",
                "Bowling will be an excellent option for outdoor activities for a large company", 45.0, 60, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagId5(), getTagId7()).collect(Collectors.toList()));
    }

    private GiftCertificate getNewGiftCertificateId6() {
        return new GiftCertificate(6, "Name", "Description", 95.0, 60, LocalDateTime.parse("2020-04-27T04:43:55.000"),
                LocalDateTime.parse("2021-04-27T04:43:55.000"), Stream.of(getTagId5(), getTagId7()).collect(Collectors.toList()));
    }

    private Tag getTagId1() {
        return new Tag(1, "Sport");
    }

    private Tag getTagId2() {
        return new Tag(2, "Water");
    }

    private Tag getTagId5() {
        return new Tag(5, "Auto");
    }

    private Tag getTagId7() {
        return new Tag(7, "Health");
    }

    private Tag getEmptyTag() {
        return new Tag(0, null);
    }
}