package com.epam.esm.service.dtoFactory;

import com.epam.esm.dto.GiftCertificateDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.esm.service.dtoFactory.TagDtoFactory.getNewTagDtoId1;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getNewTagDtoId2;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getNewTagDtoId5;
import static com.epam.esm.service.dtoFactory.TagDtoFactory.getNewTagDtoId7;


public class GiftCertificateDtoFactory {
    private static final GiftCertificateDto giftCertificateId1 = getNewGiftCertificateDtoId1();
    private static final GiftCertificateDto giftCertificateId2 = getNewGiftCertificateDtoId2();
    private static final GiftCertificateDto giftCertificateId3 = getNewGiftCertificateDtoId3();
    private static final GiftCertificateDto giftCertificateId4 = getNewGiftCertificateDtoId4();
    private static final GiftCertificateDto giftCertificateId5 = getNewGiftCertificateDtoId5();

    public static GiftCertificateDto getNewGiftCertificateDtoId1() {
        return new GiftCertificateDto(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"),
                Stream.of(getNewTagDtoId1(), getNewTagDtoId2(), getNewTagDtoId7()).collect(Collectors.toList()));
    }

    public static GiftCertificateDto getNewGiftCertificateDtoId2() {
        return new GiftCertificateDto(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"),
                Stream.of(getNewTagDtoId5()).collect(Collectors.toList()));
    }

    public static GiftCertificateDto getNewGiftCertificateDtoId3() {
        return new GiftCertificateDto(3, "Relaxing massage \"Antistress\"",
                "Relaxing massage in Minsk will help to get rid of both nervous and muscle tension", 50.0, 60,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"),
                Stream.of(getNewTagDtoId5()).collect(Collectors.toList()));
    }

    public static GiftCertificateDto getNewGiftCertificateDtoId4() {
        return new GiftCertificateDto(4, "Bowling for the company",
                "Bowling will be an excellent option for outdoor activities for a large company", 45.0, 60,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"),
                Stream.of(getNewTagDtoId5(), getNewTagDtoId7()).collect(Collectors.toList()));
    }

    public static GiftCertificateDto getNewGiftCertificateDtoId5() {
        return new GiftCertificateDto(5, "Karting on a two-storey highway",
                "Gift certificate for skating in the renovated club of Minsk \"Kartland\"", 20.0, 10,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"),
                Stream.of(getNewTagDtoId5()).collect(Collectors.toList()));
    }

    public static GiftCertificateDto getNewGiftCertificateDto() {
        return new GiftCertificateDto(0, "Name", "Description", 95.0, 60,
                LocalDateTime.parse("2020-04-27T04:43:55.000"), LocalDateTime.parse("2021-04-27T04:43:55.000"),
                Stream.of(getNewTagDtoId5(), getNewTagDtoId7()).collect(Collectors.toList()));
    }

    public static GiftCertificateDto getGiftCertificateDtoId1() {
        return giftCertificateId1;
    }

    public static GiftCertificateDto getGiftCertificateDtoId2() {
        return giftCertificateId2;
    }

    public static GiftCertificateDto getGiftCertificateDtoId3() {
        return giftCertificateId3;
    }

    public static GiftCertificateDto getGiftCertificateDtoId4() {
        return giftCertificateId4;
    }

    public static GiftCertificateDto getGiftCertificateDtoId5() {
        return giftCertificateId5;
    }

    public static List<GiftCertificateDto> getGiftCertificateDtoList() {
        return Arrays.asList(getGiftCertificateDtoId1(), getGiftCertificateDtoId2(), getGiftCertificateDtoId3(),
                getGiftCertificateDtoId4(), getGiftCertificateDtoId5());
    }

    public static List<GiftCertificateDto> getNewGiftCertificateDtoList() {
        return Stream.of(getNewGiftCertificateDtoId1(), getNewGiftCertificateDtoId2(), getNewGiftCertificateDtoId3(),
                getNewGiftCertificateDtoId4(), getNewGiftCertificateDtoId5()).collect(Collectors.toList());
    }
}
