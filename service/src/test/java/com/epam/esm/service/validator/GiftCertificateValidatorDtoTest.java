package com.epam.esm.service.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.validator.groups.OnCreate;
import com.epam.esm.service.validator.groups.OnUpdate;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftCertificateValidatorDtoTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private Set<ConstraintViolation<GiftCertificateDto>> constraintViolations;

    @Test
    void onCreateGiftCertificateValidatorTest() {
        GiftCertificateDto gift = new GiftCertificateDto();

        constraintViolations = validator.validate(gift, OnCreate.class);
        assertEquals(4, constraintViolations.size());

        gift.setPrice(-45.0);
        gift.setDuration(-1);
        constraintViolations = validator.validate(gift, OnCreate.class);
        assertEquals(4, constraintViolations.size());

        gift.setName("name");
        gift.setDescription("name");
        constraintViolations = validator.validate(gift, OnCreate.class);
        assertEquals(2, constraintViolations.size());

        gift.setPrice(45.0);
        gift.setDuration(0);
        constraintViolations = validator.validate(gift, OnCreate.class);
        assertEquals(1, constraintViolations.size());

        gift.setPrice(45.0);
        gift.setDuration(1);
        constraintViolations = validator.validate(gift, OnCreate.class);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    void onUpdateGiftCertificateValidatorTest() {
        GiftCertificateDto gift = new GiftCertificateDto();

        constraintViolations = validator.validate(gift, OnUpdate.class);
        assertEquals(1, constraintViolations.size());

        gift.setPrice(-45.0);
        gift.setDuration(-1);
        constraintViolations = validator.validate(gift, OnUpdate.class);
        assertEquals(3, constraintViolations.size());

        gift.setId(-5);
        gift.setName("name");
        gift.setDescription("name");
        constraintViolations = validator.validate(gift, OnUpdate.class);
        assertEquals(3, constraintViolations.size());

        gift.setId(5);
        gift.setPrice(45.0);
        gift.setDuration(0);
        constraintViolations = validator.validate(gift, OnUpdate.class);
        assertEquals(1, constraintViolations.size());

        gift.setPrice(45.0);
        gift.setDuration(1);
        constraintViolations = validator.validate(gift, OnUpdate.class);
        assertEquals(0, constraintViolations.size());
    }
}
