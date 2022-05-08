package com.epam.esm.service.utils;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.validator.GiftCertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.service.utils.GiftCertificateUtil.*;
import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateUtilTest {

    GiftCertificate gift1;
    GiftCertificate gift2;
    List<GiftCertificate> entityList;
    List<Tag> tagListGift1;
    List<Tag> tagListGift2;
    List<GiftCertificateDto> dtoList;
    GiftCertificateDto oldGift;

    @BeforeEach
    void init() {
        gift1 = new GiftCertificate(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), null);
        gift2 = new GiftCertificate(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), null);

        tagListGift1 = new ArrayList<>();
        tagListGift1.add(new Tag(1, "Sport"));
        tagListGift1.add(new Tag(2, "Water"));
        tagListGift1.add(new Tag(7, "Health"));

        tagListGift2 = new ArrayList<>();
        tagListGift2.add(new Tag(5, "Auto"));

        entityList = new ArrayList<>();
        entityList.add(new GiftCertificate(1, "Water skiing", "Water skiing on Minsk sea", 20.0, 50,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift1.get(0)));
        entityList.add(new GiftCertificate(1, "Water skiing", "Water skiing on Minsk sea", 20.0, 50,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift1.get(1)));
        entityList.add(new GiftCertificate(1, "Water skiing", "Water skiing on Minsk sea", 20.0, 50,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift1.get(2)));
        entityList.add(new GiftCertificate(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift2.get(0)));

        dtoList = new ArrayList<>();
        dtoList.add(new GiftCertificateDto(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift1));
        dtoList.add(new GiftCertificateDto(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"),  tagListGift2));

        oldGift = new GiftCertificateDto(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift1);
    }

    @Test
    void giftCertificateEntityListToDtoConvertingTest() {
        List<GiftCertificateDto> actualList = giftCertificateEntityListToDtoConverting(entityList);
        assertEquals(dtoList, actualList);
    }

    @Test
    void giftCertificateDtoToEntityTransferTest() {
        GiftCertificate expEntity = new GiftCertificate(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), null);

        GiftCertificate actEntity = giftCertificateDtoToEntityTransfer(dtoList.get(0));
        assertEquals(expEntity, actEntity);
    }

    @Test
    void updateFieldsTest1(){
        GiftCertificateDto newGift = new GiftCertificateDto(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift1);

        updateFields(newGift, oldGift);
        assertEquals(newGift, oldGift);
        assertEquals(oldGift, dtoList.get(0));
    }

    @Test
    void updateFieldsTest2(){
        GiftCertificateDto newGift = new GiftCertificateDto();
        newGift.setId(1);
        newGift.setName("newName");
        newGift.setDescription("newDescription");
        GiftCertificateDto expectedGift = new GiftCertificateDto(1, "newName",
                "newDescription", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift1);

        updateFields(newGift, oldGift);
        assertNotEquals(newGift, oldGift);
        assertEquals(oldGift, dtoList.get(0));
        assertEquals(expectedGift, newGift);
    }

    @Test
    void updateFieldsTest3(){
        GiftCertificateDto newGift = new GiftCertificateDto();
        newGift.setId(1);

        updateFields(newGift, oldGift);
        assertEquals(oldGift, newGift);
    }

    @Test
    void giftCertificateCriteriaValidationAllOk() throws ValidateException {
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put("tag.name", "tag1");
        criteriaMap.put("name", "name");
        criteriaMap.put("description", "description");
        String sorting = "name_desc";

        GiftCertificateValidator.giftCertificateCriteriaValidation(criteriaMap, sorting);
    }

    @Test
    void giftCertificateCriteriaWrongTagName() throws ValidateException {
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put("tag.name", "tagsaaaaaaaaaaaaaaaaaaddddddddddddddddde1");
        criteriaMap.put("name", "namenioooooooooooooooovbyiiiiiiiiiiiadsssssiiiiii");
        criteriaMap.put("description", "description");
        String sorting = "name_asc";

        ValidateException e = assertThrows(ValidateException.class, () ->  GiftCertificateValidator.giftCertificateCriteriaValidation(criteriaMap, sorting));
        assertEquals("incorrect.param.tag", e.getResourceBundleCodeList().get(1));
        assertEquals("incorrect.param.name", e.getResourceBundleCodeList().get(0));
        assertEquals(2, e.getResourceBundleCodeList().size());
    }

    @Test
    void giftCertificateCriteriaWrongSortingName() throws ValidateException {
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put("tag.name", "tag");
        criteriaMap.put("name", "name");
        criteriaMap.put("description", "description");
        String sorting = "name_DssESC";

        ValidateException e = assertThrows(ValidateException.class, () ->  GiftCertificateValidator.giftCertificateCriteriaValidation(criteriaMap, sorting));
        assertEquals("incorrect.param.sorting", e.getResourceBundleCodeList().get(0));
        assertEquals(1, e.getResourceBundleCodeList().size());
    }

    @Test
    void giftCertificateCriteriaWrongParam() throws ValidateException {
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put("tag.name", "tag");
        criteriaMap.put("name", "name");
        criteriaMap.put("description", "description");
        criteriaMap.put("incorrect", "description");

        ValidateException e = assertThrows(ValidateException.class, () ->  GiftCertificateValidator.giftCertificateCriteriaValidation(criteriaMap, null));
        assertEquals("incorrect.param", e.getResourceBundleCodeList().get(0));
        assertEquals(1, e.getResourceBundleCodeList().size());
    }
}