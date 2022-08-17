package com.epam.esm.service.entityFactory;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificateEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.esm.service.entityFactory.TagEntityFactory.getNewTagEntityId1;
import static com.epam.esm.service.entityFactory.TagEntityFactory.getNewTagEntityId2;
import static com.epam.esm.service.entityFactory.TagEntityFactory.getNewTagEntityId5;
import static com.epam.esm.service.entityFactory.TagEntityFactory.getNewTagEntityId7;

public class GiftCertificateEntityFactory {
    private static final GiftCertificateEntity giftCertificateId1 = getNewGiftCertificateEntityId1();
    private static final GiftCertificateEntity giftCertificateId2 = getNewGiftCertificateEntityId2();
    private static final GiftCertificateEntity giftCertificateId3 = getNewGiftCertificateEntityId3();
    private static final GiftCertificateEntity giftCertificateId4 = getNewGiftCertificateEntityId4();
    private static final GiftCertificateEntity giftCertificateId5 = getNewGiftCertificateEntityId5();

    public static GiftCertificateEntity getNewGiftCertificateEntityId1() {
        return new GiftCertificateEntity(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"),
                Stream.of(getNewTagEntityId1(), getNewTagEntityId2(), getNewTagEntityId7()).collect(Collectors.toList()));
    }

    public static GiftCertificateEntity getNewGiftCertificateEntityId2() {
        return new GiftCertificateEntity(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180,
                LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"),
                Stream.of(getNewTagEntityId5()).collect(Collectors.toList()));
    }

    public static GiftCertificateEntity getNewGiftCertificateEntityId3() {
        return new GiftCertificateEntity(3, "Relaxing massage \"Antistress\"",
                "Relaxing massage in Minsk will help to get rid of both nervous and muscle tension", 50.0, 60,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"),
                Stream.of(getNewTagEntityId5()).collect(Collectors.toList()));
    }

    public static GiftCertificateEntity getNewGiftCertificateEntityId4() {
        return new GiftCertificateEntity(4, "Bowling for the company",
                "Bowling will be an excellent option for outdoor activities for a large company", 45.0, 60,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"),
                Stream.of(getNewTagEntityId5(), getNewTagEntityId7()).collect(Collectors.toList()));
    }

    public static GiftCertificateEntity getNewGiftCertificateEntityId5() {
        return new GiftCertificateEntity(5, "Karting on a two-storey highway",
                "Gift certificate for skating in the renovated club of Minsk \"Kartland\"", 20.0, 10,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"),
                Stream.of(getNewTagEntityId5()).collect(Collectors.toList()));
    }

    public static GiftCertificateEntity getNewGiftCertificateEntity() {
        return new GiftCertificateEntity(0, "Name", "Description", 95.0, 60,
                LocalDateTime.parse("2020-04-27T04:43:55.000"), LocalDateTime.parse("2021-04-27T04:43:55.000"),
                Stream.of(getNewTagEntityId5(), getNewTagEntityId7()).collect(Collectors.toList()));
    }

    public static GiftCertificateEntity getGiftCertificateEntityId1() {
        return giftCertificateId1;
    }

    public static GiftCertificateEntity getGiftCertificateEntityId2() {
        return giftCertificateId2;
    }

    public static GiftCertificateEntity getGiftCertificateEntityId3() {
        return giftCertificateId3;
    }

    public static GiftCertificateEntity getGiftCertificateEntityId4() {
        return giftCertificateId4;
    }

    public static GiftCertificateEntity getGiftCertificateEntityId5() {
        return giftCertificateId5;
    }

    public static List<GiftCertificateEntity> getGiftCertificateEntityList() {
        return Arrays.asList(getGiftCertificateEntityId1(), getGiftCertificateEntityId2(), getGiftCertificateEntityId3(),
                getGiftCertificateEntityId4(), getGiftCertificateEntityId5());
    }

    public static List<GiftCertificateEntity> getNewGiftCertificateEntityList() {
        return Stream.of(getNewGiftCertificateEntityId1(), getNewGiftCertificateEntityId2(), getNewGiftCertificateEntityId3(),
                getNewGiftCertificateEntityId4(), getNewGiftCertificateEntityId5()).collect(Collectors.toList());
    }
}
