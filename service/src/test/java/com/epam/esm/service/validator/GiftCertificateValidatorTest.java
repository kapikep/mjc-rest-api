package com.epam.esm.service.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.exception.ValidateException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.repository.constant.SearchParam.*;
import static com.epam.esm.service.validator.GiftCertificateValidator.*;
import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateValidatorTest {

    @Test
    void giftCertificateFieldValidationTest() throws ValidateException {
        List<String> messages = new ArrayList<>();
        GiftCertificateDto dto = new GiftCertificateDto();
        ValidateException e;
        e = assertThrows(ValidateException.class, () -> giftCertificateFieldValidation(dto));

        GiftCertificateDto dto1 = new GiftCertificateDto(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180,
                LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), null);

        giftCertificateFieldValidation(dto1);

        dto1.setId(-5);
        messages.add("incorrect.id");
        e = assertThrows(ValidateException.class, () -> giftCertificateFieldValidation(dto1));
        assertEquals(messages, e.getResourceBundleCodeList());

        dto1.setName("");
        messages.add("incorrect.name");
        e = assertThrows(ValidateException.class, () -> giftCertificateFieldValidation(dto1));
        assertEquals(messages, e.getResourceBundleCodeList());

        dto1.setDescription("");
        messages.add("incorrect.description");
        e = assertThrows(ValidateException.class, () -> giftCertificateFieldValidation(dto1));
        assertEquals(messages, e.getResourceBundleCodeList());

        dto1.setPrice(0.0);
        messages.add("incorrect.price");
        e = assertThrows(ValidateException.class, () -> giftCertificateFieldValidation(dto1));
        assertEquals(messages, e.getResourceBundleCodeList());

        dto1.setDuration(0);
        messages.add("incorrect.duration");
        e = assertThrows(ValidateException.class, () -> giftCertificateFieldValidation(dto1));
        assertEquals(messages, e.getResourceBundleCodeList());

        dto1.setCreateDate(null);
        messages.add("incorrect.create.date");
        e = assertThrows(ValidateException.class, () -> giftCertificateFieldValidation(dto1));
        assertEquals(messages, e.getResourceBundleCodeList());

        dto1.setLastUpdateDate(null);
        messages.add("incorrect.update.date");
        e = assertThrows(ValidateException.class, () -> giftCertificateFieldValidation(dto1));
        assertEquals(messages, e.getResourceBundleCodeList());
    }

    @Test
    void lastUpdateDateValidationTest() {
        LocalDateTime createDate = LocalDateTime.parse("2022-03-27T04:43:55.000");
        LocalDateTime updateDate = LocalDateTime.parse("2022-04-27T04:43:55.000");
        assertTrue(lastUpdateDateValidation(createDate, updateDate));

        updateDate = LocalDateTime.parse("2020-03-27T04:43:55.000");
        assertFalse(lastUpdateDateValidation(createDate, updateDate));

        updateDate = LocalDateTime.parse("9999-03-27T04:43:55.000");
        assertFalse(lastUpdateDateValidation(createDate, updateDate));

        lastUpdateDateValidation(null, updateDate);
        assertFalse(lastUpdateDateValidation(null, null));
    }

    @Test
    void createDateValidationTest() {
        LocalDateTime createDate = LocalDateTime.parse("2022-03-27T04:43:55.000");
        assertTrue(createDateValidation(createDate));

        createDate = LocalDateTime.parse("9999-03-27T04:43:55.000");
        assertFalse(createDateValidation(createDate));

        assertFalse(createDateValidation(null));
    }

    @Test
    void allNotNullFieldValidationTest() throws ValidateException {
        GiftCertificateDto dto = new GiftCertificateDto();
        assertThrows(ValidateException.class, () -> isNullFieldValidation(dto));
        dto.setId(1);
        assertFalse(isNullFieldValidation(dto));
    }


    @Test
    void giftCertificateCriteriaValidationAllOk() throws ValidateException {
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put(GIFT_SEARCH_BY_TAG_NAME, "tag1");
        criteriaMap.put(GIFT_SEARCH_NAME, "name");
        criteriaMap.put(GIFT_SEARCH_DESCRIPTION, "description");
        String sorting = "-name";

        GiftCertificateValidator.giftCertificateCriteriaValidation(criteriaMap, sorting);
    }

    @Test
    void giftCertificateCriteriaWrongTagName(){
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put(GIFT_SEARCH_BY_TAG_NAME, "tagsaaaaaaaaaaaaaaaaaaddddddddddddddddde1daaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        criteriaMap.put(GIFT_SEARCH_NAME, "namenioooooooooooooooovbyiiiiiiiiiiiadsssssiiiiii");
        criteriaMap.put(GIFT_SEARCH_DESCRIPTION, "description");
        String sorting = "+name";

        ValidateException e = assertThrows(ValidateException.class, () ->  GiftCertificateValidator.giftCertificateCriteriaValidation(criteriaMap, sorting));
        assertEquals("incorrect.param.tag", e.getResourceBundleCodeList().get(1));
        assertEquals("incorrect.param.name", e.getResourceBundleCodeList().get(0));
        assertEquals(2, e.getResourceBundleCodeList().size());
    }

    @Test
    void giftCertificateCriteriaWrongSortingName(){
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put(GIFT_SEARCH_BY_TAG_NAME, "tag");
        criteriaMap.put(GIFT_SEARCH_NAME, "name");
        criteriaMap.put(GIFT_SEARCH_DESCRIPTION, "description");
        String sorting = "++name";

        ValidateException e = assertThrows(ValidateException.class, () ->  GiftCertificateValidator.giftCertificateCriteriaValidation(criteriaMap, sorting));
        assertEquals("incorrect.param.sorting", e.getResourceBundleCodeList().get(0));
        assertEquals(1, e.getResourceBundleCodeList().size());
    }

    @Test
    void giftCertificateCriteriaWrongParam(){
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put(GIFT_SEARCH_BY_TAG_NAME, "tag");
        criteriaMap.put(GIFT_SEARCH_NAME, "name");
        criteriaMap.put(GIFT_SEARCH_DESCRIPTION, "description");
        criteriaMap.put("incorrect", "description");

        ValidateException e = assertThrows(ValidateException.class, () ->  GiftCertificateValidator.giftCertificateCriteriaValidation(criteriaMap, null));
        assertEquals("incorrect.param", e.getResourceBundleCodeList().get(0));
        assertEquals(1, e.getResourceBundleCodeList().size());
    }
}