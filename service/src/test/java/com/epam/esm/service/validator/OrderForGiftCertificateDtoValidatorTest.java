package com.epam.esm.service.validator;

import com.epam.esm.dto.OrderForGiftCertificateDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class OrderForGiftCertificateDtoValidatorTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private Set<ConstraintViolation<OrderForGiftCertificateDto>> constraintViolations;
}
