package com.epam.esm.service.util;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.service.exception.ValidateException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.epam.esm.service.constant.ExceptionMes.GIFT_CERTIFICATE_DTO_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.GIFT_CERTIFICATE_DTO_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.GIFT_CERTIFICATE_ENTITY_LIST_MUST_NOT_BE_NULL;
import static com.epam.esm.service.constant.ExceptionMes.GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getGiftCertificateDtoId1;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getGiftCertificateDtoId2;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getGiftCertificateDtoId3;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getGiftCertificateDtoId4;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getGiftCertificateDtoList;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getNewGiftCertificateDtoId1;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getNewGiftCertificateDtoId2;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getNewGiftCertificateDtoId3;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getNewGiftCertificateDtoId4;
import static com.epam.esm.service.entityFactory.GiftCertificateEntityFactory.getGiftCertificateEntityId2;
import static com.epam.esm.service.entityFactory.GiftCertificateEntityFactory.getGiftCertificateEntityId3;
import static com.epam.esm.service.entityFactory.GiftCertificateEntityFactory.getGiftCertificateEntityId4;
import static com.epam.esm.service.entityFactory.GiftCertificateEntityFactory.getGiftCertificateEntityList;
import static com.epam.esm.service.entityFactory.GiftCertificateEntityFactory.getNewGiftCertificateEntityId1;
import static com.epam.esm.service.entityFactory.GiftCertificateEntityFactory.getNewGiftCertificateEntityId2;
import static com.epam.esm.service.entityFactory.GiftCertificateEntityFactory.getNewGiftCertificateEntityId3;
import static com.epam.esm.service.entityFactory.GiftCertificateEntityFactory.getNewGiftCertificateEntityId4;
import static com.epam.esm.service.util.GiftCertificateUtil.giftCertificateDtoListToEntityConverting;
import static com.epam.esm.service.util.GiftCertificateUtil.giftCertificateDtoToEntityConverting;
import static com.epam.esm.service.util.GiftCertificateUtil.giftCertificateEntityListToDtoConverting;
import static com.epam.esm.service.util.GiftCertificateUtil.giftCertificateEntityToDtoConverting;
import static com.epam.esm.service.util.GiftCertificateUtil.isNullFieldValidation;
import static com.epam.esm.service.util.GiftCertificateUtil.sortingValidation;
import static com.epam.esm.service.util.GiftCertificateUtil.updateFieldsInDto;
import static com.epam.esm.service.util.GiftCertificateUtil.updateNonNullFieldsFromDtoToEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GiftCertificateUtilTest {

    @Test
    void giftCertificateEntityListToDtoConvertingTest() {
        List<GiftCertificateDto> actualList = giftCertificateEntityListToDtoConverting(getGiftCertificateEntityList());

        assertEquals(getGiftCertificateDtoList(), actualList);
    }

    @Test
    void giftCertificateEntityListToDtoConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> giftCertificateEntityListToDtoConverting(null));

        assertEquals(GIFT_CERTIFICATE_ENTITY_LIST_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void giftCertificateDtoListToEntityConvertingTest() {
        List<GiftCertificateEntity> actualList = giftCertificateDtoListToEntityConverting(getGiftCertificateDtoList());

        assertEquals(getGiftCertificateEntityList(), actualList);
    }

    @Test
    void giftCertificateDtoListToEntityConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> giftCertificateDtoListToEntityConverting(null));

        assertEquals(GIFT_CERTIFICATE_DTO_LIST_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void giftCertificateDtoToEntityConvertingTest() {
        GiftCertificateEntity actualEntity = giftCertificateDtoToEntityConverting(getGiftCertificateDtoId3());

        assertEquals(getGiftCertificateEntityId3(), actualEntity);
    }

    @Test
    void giftCertificateDtoToEntityConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> giftCertificateDtoToEntityConverting(null));

        assertEquals(GIFT_CERTIFICATE_DTO_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void giftCertificateEntityToDtoConvertingTest() {
        GiftCertificateDto actualDto = giftCertificateEntityToDtoConverting(getGiftCertificateEntityId4());
        assertEquals(getGiftCertificateDtoId4(), actualDto);
    }

    @Test
    void giftCertificateEntityToDtoConvertingNullTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> giftCertificateEntityToDtoConverting(null));

        assertEquals(GIFT_CERTIFICATE_ENTITY_MUST_NOT_BE_NULL, e.getMessage());
    }

    @Test
    void updateFieldsWithNullTagTest() {
        GiftCertificateDto oldGift = getNewGiftCertificateDtoId4();
        GiftCertificateDto newGift = getNewGiftCertificateDtoId4();
        newGift.setTags(null);

        updateFieldsInDto(newGift, oldGift);
        assertEquals(newGift, oldGift);
        assertEquals(oldGift, getGiftCertificateDtoId4());
    }

    @Test
    void updateFieldsWithAllNotNullFieldsTest() {
        GiftCertificateDto oldGift = getNewGiftCertificateDtoId4();
        GiftCertificateDto newGift = getNewGiftCertificateDtoId1();

        updateFieldsInDto(newGift, oldGift);
        assertNotEquals(newGift, oldGift);
        assertEquals(newGift, getGiftCertificateDtoId1());
        assertEquals(oldGift, getGiftCertificateDtoId4());
    }

    @Test
    void updateFieldsWithNullFieldsTest() {
        GiftCertificateDto oldGift = getNewGiftCertificateDtoId2();
        GiftCertificateDto newGift = getNewGiftCertificateDtoId2();
        GiftCertificateDto expectedGift = getNewGiftCertificateDtoId2();
        newGift.setName("New name");
        newGift.setDescription(null);
        newGift.setCreateDate(null);
        newGift.setDuration(null);
        expectedGift.setName("New name");

        updateFieldsInDto(newGift, oldGift);
        assertEquals(expectedGift, newGift);
        assertNotEquals(oldGift, newGift);
    }

    @Test
    void updateNonNullFieldsFromDtoToEntityWithNullTagTest() {
        GiftCertificateEntity oldGift = getNewGiftCertificateEntityId4();
        oldGift.setId(3);
        GiftCertificateDto newGift = getNewGiftCertificateDtoId3();
        newGift.setTags(null);
        GiftCertificateEntity expectedGift = getNewGiftCertificateEntityId3();
        expectedGift.setTags(oldGift.getTags());

        updateNonNullFieldsFromDtoToEntity(newGift, oldGift);
        assertEquals(expectedGift, oldGift);
    }

    @Test
    void updateNonNullFieldsFromDtoToEntityAllNotNullFieldsTest() {
        GiftCertificateEntity oldGift = getNewGiftCertificateEntityId4();
        oldGift.setId(1);
        GiftCertificateDto newGift = getNewGiftCertificateDtoId1();
        GiftCertificateEntity expectedGift = getNewGiftCertificateEntityId1();

        updateNonNullFieldsFromDtoToEntity(newGift, oldGift);

        assertEquals(expectedGift, oldGift);
        assertEquals(getGiftCertificateDtoId1(), newGift);
    }

    @Test
    void updateNonNullFieldsFromDtoToEntityWithNullFieldsTest() {
        GiftCertificateEntity oldGift = getNewGiftCertificateEntityId2();
        GiftCertificateDto newGift = getNewGiftCertificateDtoId2();
        GiftCertificateEntity expectedGift = getNewGiftCertificateEntityId2();
        newGift.setName("New name");
        newGift.setDescription(null);
        newGift.setCreateDate(null);
        newGift.setDuration(null);
        expectedGift.setName("New name");

        updateNonNullFieldsFromDtoToEntity(newGift, oldGift);

        assertEquals(expectedGift, oldGift);
    }

    @Test
    void sortingValidationTest() throws ValidateException {
        CriteriaDto cr = new CriteriaDto();
        cr.setSorting("id");
        sortingValidation(cr);

        cr.setSorting("-id");
        sortingValidation(cr);

        cr.setSorting("criteria");
        assertThrows(ValidateException.class,
                () -> sortingValidation(cr));
    }

    @Test
    void allNotNullFieldValidationTest() {
        GiftCertificateDto dto = new GiftCertificateDto();

        assertTrue(isNullFieldValidation(dto));

        dto = getNewGiftCertificateDtoId1();
        assertFalse(isNullFieldValidation(dto));

        dto.setName(null);
        assertTrue(isNullFieldValidation(dto));

        dto.setName("Name");
        dto.setTags(null);
        assertTrue(isNullFieldValidation(dto));
    }

//    @Test
//    void giftCertificateCriteriaValidationAllOk() throws ValidateException {
//        Map<String, String> criteriaMap = new HashMap<>();
//        criteriaMap.put(GIFT_SEARCH_BY_TAG_NAME, "tag1");
//        criteriaMap.put(GIFT_SEARCH_NAME, "name");
//        criteriaMap.put(GIFT_SEARCH_DESCRIPTION, "description");
//        String sorting = "-name";
//
//        GiftCertificateUtil.giftCertificateCriteriaValidation(criteriaMap, sorting);
//    }

//    @Test
//    void giftCertificateCriteriaWrongTagName(){
//        Map<String, String> criteriaMap = new HashMap<>();
//        criteriaMap.put(GIFT_SEARCH_BY_TAG_NAME, "tagsaaaaaaaaaaaaaaaaaaddddddddddddddddde1daaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//        criteriaMap.put(GIFT_SEARCH_NAME, "namenioooooooooooooooovbyiiiiiiiiiiiadsssssiiiiii");
//        criteriaMap.put(GIFT_SEARCH_DESCRIPTION, "description");
//        String sorting = "+name";
//
//        ValidateException e = assertThrows(ValidateException.class, () ->  GiftCertificateUtil.giftCertificateCriteriaValidation(criteriaMap, sorting));
//        assertEquals("incorrect.param.tag", e.getResourceBundleCodeList().get(1));
//        assertEquals("incorrect.param.name", e.getResourceBundleCodeList().get(0));
//        assertEquals(2, e.getResourceBundleCodeList().size());
//    }

//    @Test
//    void giftCertificateCriteriaWrongSortingName(){
//        Map<String, String> criteriaMap = new HashMap<>();
//        criteriaMap.put(GIFT_SEARCH_BY_TAG_NAME, "tag");
//        criteriaMap.put(GIFT_SEARCH_NAME, "name");
//        criteriaMap.put(GIFT_SEARCH_DESCRIPTION, "description");
//        String sorting = "++name";
//
//        ValidateException e = assertThrows(ValidateException.class, () ->  GiftCertificateUtil.giftCertificateCriteriaValidation(criteriaMap, sorting));
//        assertEquals("incorrect.param.sorting", e.getResourceBundleCodeList().get(0));
//        assertEquals(1, e.getResourceBundleCodeList().size());
//    }
//
//    @Test
//    void giftCertificateCriteriaWrongParam(){
//        Map<String, String> criteriaMap = new HashMap<>();
//        criteriaMap.put(GIFT_SEARCH_BY_TAG_NAME, "tag");
//        criteriaMap.put(GIFT_SEARCH_NAME, "name");
//        criteriaMap.put(GIFT_SEARCH_DESCRIPTION, "description");
//        criteriaMap.put("incorrect", "description");
//
//        ValidateException e = assertThrows(ValidateException.class, () ->  GiftCertificateUtil.giftCertificateCriteriaValidation(criteriaMap, null));
//        assertEquals("incorrect.param", e.getResourceBundleCodeList().get(0));
//        assertEquals(1, e.getResourceBundleCodeList().size());
//    }
}