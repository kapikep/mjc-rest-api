package com.epam.esm.service.util;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.service.exception.ValidateException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.esm.service.util.GiftCertificateUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GiftCertificateUtilTest {

    @Test
    void giftCertificateEntityListToDtoConvertingTest() throws ValidateException {
        List<GiftCertificateDto> actualList = giftCertificateEntityListToDtoConverting(getEntityList());

        assertEquals(getDtoList(), actualList);
    }

    @Test
    void giftCertificateDtoToEntityTransferTest() throws ValidateException {
        GiftCertificateEntity actualEntity = giftCertificateDtoToEntityTransfer(getDtoId1());
        assertEquals(getEntityId1(), actualEntity);
    }

    @Test
    void giftCertificateEntityToDtoTransferTest() throws ValidateException {
        GiftCertificateDto actualDto = giftCertificateEntityToDtoTransfer(getEntityId2());
        assertEquals(getDtoId2(), actualDto);
    }


    @Test
    void updateFieldsWithNullTagTest(){
        GiftCertificateDto oldGift = getDtoId4();
        GiftCertificateDto newGift = getDtoId4();
        newGift.setTags(null);

        updateFields(newGift, oldGift);
        assertEquals(newGift, oldGift);
        assertEquals(oldGift, getDtoId4());
    }

    @Test
    void updateFieldsWithAllNotNullFieldsTest(){
        GiftCertificateDto oldGift = getDtoId4();
        GiftCertificateDto newGift = getDtoId1();

        updateFields(newGift, oldGift);
        assertNotEquals(newGift, oldGift);
        assertEquals(newGift, getDtoId1());
        assertEquals(oldGift, getDtoId4());
    }

    @Test
    void updateFieldsWithNullFieldsTest(){
        GiftCertificateDto oldGift = getDtoId2();
        GiftCertificateDto newGift = getDtoId2();
        newGift.setName("New name");
        newGift.setDescription(null);
        newGift.setCreateDate(null);
        newGift.setDuration(null);
        GiftCertificateDto updatedGift = getDtoId2();
        updatedGift.setName("New name");

        updateFields(newGift, oldGift);
        assertEquals(updatedGift, newGift);
    }

    private GiftCertificateEntity getEntityId1() {
        return new GiftCertificateEntity(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagEntityId1(), getTagEntityId2(), getTagEntityId7()).collect(Collectors.toList()));
    }

    private GiftCertificateEntity getEntityId2() {
        return new GiftCertificateEntity(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagEntityId5()).collect(Collectors.toList()));
    }

    private GiftCertificateEntity getEntityId4() {
        return new GiftCertificateEntity(4, "Bowling for the company",
                "Bowling will be an excellent option for outdoor activities for a large company", 45.0, 60, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagEntityId5(), getTagEntityId7()).collect(Collectors.toList()));
    }

    private GiftCertificateDto getDtoId1() {
        return new GiftCertificateDto(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagDtoId1(), getTagDtoId2(), getTagDtoId7()).collect(Collectors.toList()));
    }

    private GiftCertificateDto getDtoId2() {
        return new GiftCertificateDto(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagDtoId5()).collect(Collectors.toList()));
    }

    private GiftCertificateDto getDtoId4() {
        return new GiftCertificateDto(4, "Bowling for the company",
                "Bowling will be an excellent option for outdoor activities for a large company", 45.0, 60, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagDtoId5(), getTagDtoId7()).collect(Collectors.toList()));
    }

    private List<GiftCertificateEntity> getEntityList() {
        List<GiftCertificateEntity> entityList = new ArrayList<>();
        entityList.add(getEntityId1());
        entityList.add(getEntityId2());
        entityList.add(getEntityId4());
        return entityList;
    }

    private List<GiftCertificateDto> getDtoList(){
        List<GiftCertificateDto> dtoList = new ArrayList<>();
        dtoList.add(getDtoId1());
        dtoList.add(getDtoId2());
        dtoList.add(getDtoId4());
        return dtoList;
    }

    private TagEntity getTagEntityId1() {
        return new TagEntity(1, "Sport");
    }

    private TagEntity getTagEntityId2() {
        return new TagEntity(2, "Water");
    }

    private TagEntity getTagEntityId5() {
        return new TagEntity(5, "Auto");
    }

    private TagEntity getTagEntityId7() {
        return new TagEntity(7, "Health");
    }

    private TagDto getTagDtoId1() {
        return new TagDto(1, "Sport");
    }

    private TagDto getTagDtoId2() {
        return new TagDto(2, "Water");
    }

    private TagDto getTagDtoId5() {
        return new TagDto(5, "Auto");
    }

    private TagDto getTagDtoId7() {
        return new TagDto(7, "Health");
    }
}