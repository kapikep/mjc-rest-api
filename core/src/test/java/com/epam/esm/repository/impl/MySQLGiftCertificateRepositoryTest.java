package com.epam.esm.repository.impl;

import com.epam.esm.config.DevConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DevConfig.class)
class MySQLGiftCertificateRepositoryTest {
    @Autowired
    private GiftCertificateRepository repository;

    @Test
    void readAll() throws RepositoryException {
        List<GiftCertificate> gifts = repository.readAllGiftCertificates();

        assertEquals(8, gifts.size());
        gifts.forEach(Assertions::assertNotNull);
    }

    @Test
    void readGiftCertificate() throws RepositoryException {
        GiftCertificate expectedGift1 = new GiftCertificate(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), new Tag(1, "Sport"));
        GiftCertificate expectedGift2 = new GiftCertificate(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), new Tag(2, "Water"));
        GiftCertificate expectedGift3 = new GiftCertificate(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), new Tag(7, "Health"));

        List<GiftCertificate> actualGifts = repository.readGiftCertificate(1);

        assertEquals(3, actualGifts.size());
        assertTrue(actualGifts.contains(expectedGift1));
        assertTrue(actualGifts.contains(expectedGift2));
        assertTrue(actualGifts.contains(expectedGift3));
    }

    @Test
    void findGiftCertificateMatchChecking() throws RepositoryException {
        GiftCertificate expectedGift = new GiftCertificate(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), new Tag(5, "Auto"));
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put("name", "Car");
        criteriaMap.put("description", "washing");
        List<GiftCertificate> actualGifts =
                repository.findGiftCertificate(criteriaMap, "name_desc");

        assertEquals(1, actualGifts.size());
        assertTrue(actualGifts.contains(expectedGift));

        criteriaMap.put("tag.name", "Спорт");
        actualGifts =
                repository.findGiftCertificate(criteriaMap, null);

        assertEquals(0, actualGifts.size());
        assertFalse(actualGifts.contains(expectedGift));
    }

    @Test
    void findGiftCertificateSortingChecking() throws RepositoryException {
        GiftCertificate expectedLastGift = new GiftCertificate(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), new Tag(7, "Health"));
        GiftCertificate expectedFirstGift = new GiftCertificate(4, "Bowling for the company",
                "Bowling will be an excellent option for outdoor activities for a large company", 45.0, 60, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), new Tag(5, "Auto"));
        List<GiftCertificate> actualGifts =
                repository.findGiftCertificate(null, null);
        actualGifts =
                repository.findGiftCertificate(null, "name");

        assertEquals(8, actualGifts.size());
        assertEquals(expectedFirstGift, actualGifts.get(0));
        assertEquals(expectedLastGift, actualGifts.get(7));

        actualGifts =
                repository.findGiftCertificate(null, "name_desc");

        expectedLastGift.setTag(new Tag(1, "Sport"));
        expectedFirstGift.setTag(new Tag(7, "Health"));

        assertEquals(8, actualGifts.size());
        assertEquals(expectedLastGift, actualGifts.get(0));
        assertEquals(expectedFirstGift, actualGifts.get(7));
    }

    @Test
    void createGiftCertificate() throws RepositoryException {
        GiftCertificate expectedLastGift = new GiftCertificate(6, "Name", "Description", 45.0, 60,
                LocalDateTime.parse("2020-04-27T04:43:55.000"), LocalDateTime.parse("2021-04-27T04:43:55.000"), new Tag(1, "Sport"));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1,"Sport"));

        repository.createGiftCertificate(expectedLastGift, tags);

        List<GiftCertificate> actualGifts = repository.readGiftCertificate(6);
        repository.deleteGiftCertificate(6);

        assertEquals(expectedLastGift, actualGifts.get(0));
    }

    @Test
    void updateGiftCertificate() throws RepositoryException {
        GiftCertificate expectedLastGift = new GiftCertificate(5, "New name",
                "New description", 20.0, 50, LocalDateTime.parse("2010-04-27T04:43:55.000"),
                LocalDateTime.parse("2010-04-27T04:43:55.000"), new Tag(4, "Cafe"));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(4,"Cafe"));
        repository.updateGiftCertificate(expectedLastGift, tags);
        List<GiftCertificate> actualGifts = repository.readGiftCertificate(5);
        //        tags.add(new Tag("New tag"));
        assertEquals(expectedLastGift, actualGifts.get(0));
    }
}