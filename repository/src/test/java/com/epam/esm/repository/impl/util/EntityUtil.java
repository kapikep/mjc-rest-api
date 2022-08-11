package com.epam.esm.repository.impl.util;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityUtil {
    public static GiftCertificateEntity getGiftCertificateId1() {
        return new GiftCertificateEntity(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagId1(), getTagId2(), getTagId7()).collect(Collectors.toList()));
    }

    public static GiftCertificateEntity getGiftCertificateId2() {
        return new GiftCertificateEntity(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagId5()).collect(Collectors.toList()));
    }

    public static GiftCertificateEntity getGiftCertificateId4() {
        return new GiftCertificateEntity(4, "Bowling for the company",
                "Bowling will be an excellent option for outdoor activities for a large company", 45.0, 60, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagId5(), getTagId7()).collect(Collectors.toList()));
    }

    public static GiftCertificateEntity getGiftCertificateId5() {
        return new GiftCertificateEntity(5, "Karting on a two-storey highway",
                "Gift certificate for skating in the renovated club of Minsk \"Kartland\"", 20.0, 10, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagId5(), getTagId7()).collect(Collectors.toList()));
    }

    public static GiftCertificateEntity getNewGiftCertificate() {
        return new GiftCertificateEntity(0, "Name", "Description", 95.0, 60, LocalDateTime.parse("2020-04-27T04:43:55.000"),
                LocalDateTime.parse("2021-04-27T04:43:55.000"), Stream.of(getTagId5(), getTagId7()).collect(Collectors.toList()));
    }

    public static TagEntity getTagId1() {
        return new TagEntity(1, "Sport");
    }

    public static TagEntity getTagId2() {
        return new TagEntity(2, "Water");
    }

    public static TagEntity getTagId5() {
        return new TagEntity(5, "Auto");
    }

    public static TagEntity getTagId6() {
        return new TagEntity(6, "Romantic");
    }

    public static TagEntity getTagId7() {
        return new TagEntity(7, "Health");
    }

    public static TagEntity getNewTag() {
        return new TagEntity(0, "New tag");
    }

    public static TagEntity getEmptyTag() {
        return new TagEntity(0, null);
    }

    public static CriteriaEntity getCriteriaWithDefaultVal() {
        CriteriaEntity cr = new CriteriaEntity();
        cr.setPage(1);
        cr.setSize(20);
        return cr;
    }
}
