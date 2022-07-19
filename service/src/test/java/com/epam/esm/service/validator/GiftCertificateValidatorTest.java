package com.epam.esm.service.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.util.GiftCertificateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.repository.constant.SearchParam.*;
import static com.epam.esm.service.util.GiftCertificateUtil.isNullFieldValidation;
import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateValidatorTest {

    @Test
    void allNotNullFieldValidationTest() throws ValidateException {
        GiftCertificateDto dto = new GiftCertificateDto();
        assertThrows(ValidateException.class, () -> isNullFieldValidation(dto));
        dto.setId(1);
        assertFalse(isNullFieldValidation(dto));
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